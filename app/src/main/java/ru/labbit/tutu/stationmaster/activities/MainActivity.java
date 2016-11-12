package ru.labbit.tutu.stationmaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import ru.labbit.tutu.stationmaster.R;
import ru.labbit.tutu.stationmaster.application.App;
import ru.labbit.tutu.stationmaster.controller.Controller;

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

        departureTextView = (EditText) findViewById(R.id.departure_text);
        mainListView = (ListView) findViewById(R.id.stations_list_view);

        addTextListeners(departureTextView);

        controller.setControllerListener(new Controller.ControllerListener() {
            @Override
            public void onDepatrureChange(String s) {
                //TODO: impl
                departureTextView.setText(s);
            }
            @Override
            public void onStationsListChange(List<String> l) {
//                mainListView.setAdapter(new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1, l));
            }
        });
        //TODO в конструкторе контроллера это делать
        controller.loadStationsData();

//        //TODO лист не надо заполнять его надо прятать пока он не понадобится
//        List result = Arrays.asList(controller.whatever());
//        mainListView.setAdapter(new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1, result));
    }

    public void updateDepartureText(String s) {
        departureTextView.setText(s);
    }

    private void addTextListeners(final EditText textField) {
        textField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //TODO тоже вынести обработку в контроллер но туда надо отдать текст
                if (hasFocus) {
                    textField.setText("");
                } else {
                    textField.setText(R.string.departure);
                }
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