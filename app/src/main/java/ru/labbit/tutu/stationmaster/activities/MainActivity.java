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

import java.util.List;

import javax.inject.Inject;

import ru.labbit.tutu.stationmaster.R;
import ru.labbit.tutu.stationmaster.application.App;
import ru.labbit.tutu.stationmaster.controller.Controller;
import ru.labbit.tutu.stationmaster.utils.json.JSONResourceReader;

public class MainActivity extends AppCompatActivity {

    @Inject
    public Controller controller;

    private EditText departureTextView;
    private ListView mainListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stations_activity);

        App.getComponent().inject(this);

        //TODO убрать в контроллер
        controller.loadStations(new JSONResourceReader(getResources(), R.raw.allstations));

        departureTextView = (EditText) findViewById(R.id.departure_text);
        mainListView = (ListView) findViewById(R.id.stations_list_view);

        addTextListeners(departureTextView);

        controller.setControllerListener(new Controller.ControllerListener() {
            @Override
            public void onDepatrureChange(String s) {
                departureTextView.setText(s);
            }

            @Override
            public void onStationsListChange(List<String> l) {
                populateMainList(l);
            }
        });
    }

    private void populateMainList(List<String> l) {
        ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, l);
        mainListView.setAdapter(a);
    }

    private void addTextListeners(final EditText textField) {
        textField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d("view is: ", v.toString());
                controller.handleDepartureTextFocusChange(v, hasFocus);
            }
        });
        textField.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                controller.handleTextChange(textField.getText().toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
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
}