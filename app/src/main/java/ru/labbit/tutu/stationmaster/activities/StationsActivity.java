package ru.labbit.tutu.stationmaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import ru.labbit.tutu.stationmaster.R;
import ru.labbit.tutu.stationmaster.application.App;
import ru.labbit.tutu.stationmaster.controller.Controller;
import ru.labbit.tutu.stationmaster.entities.AllStations;
import ru.labbit.tutu.stationmaster.entities.Station;
import ru.labbit.tutu.stationmaster.tasks.LoadStationsTask;
import ru.labbit.tutu.stationmaster.tasks.MakeStationsListTask;
import ru.labbit.tutu.stationmaster.utils.json.JSONResourceReader;

public class StationsActivity extends AppCompatActivity {

    private static final int MIN_CHARS_TO_SEARCH = 2;
    private static final String TAG = "CUSTOM MESSAGE";

    @Inject
    public Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stations_activity);
        addTextListeners((EditText) findViewById(R.id.departure_text));
//        loadStations();
        App.getComponent().inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_stations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    private void addTextListeners(final EditText textField) {
        textField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    textField.setText("");
                } else {
                    textField.setText(R.string.departure);
                }
            }
        });
        textField.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if (textField.getText().length() > MIN_CHARS_TO_SEARCH) {
                    populateList(textField.getText().toString());

                }
                else {
                    // TODO: 11.11.2016 как-то прятать его чтоли подумать
                    // TODO: 11.11.2016  если мало символов стало то что-то надо делать
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private void loadStations() {
        JSONResourceReader reader = new JSONResourceReader(getResources(), R.raw.allstations);
        LoadStationsTask task = new LoadStationsTask();
        task.execute(reader);
        Log.e(TAG,"lets do stuff");
        try {
            AllStations ast = task.get();
            //следующая строчка ломает. видимо я накосячил с форматом но где же блять ошибка обернем щас в трай и пизда всему
            Station st = ast.getCitiesTo().get(0).getStations().get(0);
            Log.e(TAG,ast.getCitiesTo().get(0).getStations().get(0).getStationTitle());
            Log.e(TAG,"that was city");
            List result = Arrays.asList(st.getStationTitle());
            ListView listView = (ListView) findViewById(R.id.stations_list_view);
            listView.setAdapter(new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, result));

        } catch (InterruptedException e) {
            Log.e(TAG,"InterruptedException",e);
        } catch (ExecutionException e) {
            Log.e(TAG,"ExecutionException",e);
        }
    }

    private void populateList(String s) {
        MakeStationsListTask task = new MakeStationsListTask();
        task.execute(s);
        try {
            List result = task.get();
            ListView listView = (ListView) findViewById(R.id.stations_list_view);
            listView.setAdapter(new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, result));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}