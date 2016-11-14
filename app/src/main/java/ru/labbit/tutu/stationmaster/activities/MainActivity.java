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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import javax.inject.Inject;

import ru.labbit.tutu.stationmaster.R;
import ru.labbit.tutu.stationmaster.application.App;
import ru.labbit.tutu.stationmaster.controller.Controller;

public class MainActivity extends AppCompatActivity {

    @Inject
    public Controller controller;

    private EditText departureTextView;
    private EditText arrivalTextView;
    private ListView mainListView;
    private CalendarView calendarView;

    /*
    *       активность только вызывает методы контроллера
    *       и обрабатывает его ответы для апдейта вью
    *       бизнес логики в ней нет
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("start", "main");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        App.getComponent().inject(this);

        departureTextView = (EditText) findViewById(R.id.departure_text);
        arrivalTextView = (EditText) findViewById(R.id.arrival_text);
        mainListView = (ListView) findViewById(R.id.stations_list_view);
        calendarView = (CalendarView) findViewById(R.id.calendarView);

        //календарь будем показывать только когда выбраны обе станции
        calendarView.setVisibility(View.GONE);
        //а список будем показывать только когда понадобится
        mainListView.setVisibility(View.GONE);

        addControllerListeners();
        addUIListeners();
        Log.e("end", "main");
    }

    private void handlePopulateMainList(List<String> l) {
        ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, l);
        mainListView.setAdapter(a);
    }

    private void addControllerListeners() {
        controller.setControllerListener(new Controller.ControllerListener() {
            @Override
            public void onDepatrureChange(String s) {
                departureTextView.setText(s);
            }

            @Override
            public void onArrivalChange(String s) {
                arrivalTextView.setText(s);
            }

            @Override
            public void onStationsListChange(List<String> l) {
                handlePopulateMainList(l);
            }
        });
    }

    private void addUIListeners() {
        departureTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mainListView.setVisibility(View.VISIBLE);
                    arrivalTextView.setVisibility(View.GONE);
                    calendarView.setVisibility(View.GONE);
                }
                controller.handleDepartureTextFocusChange(v, hasFocus);
            }
        });
        departureTextView.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                controller.handleTextChange(departureTextView.getText().toString());
                mainListView.setVisibility(View.VISIBLE);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        arrivalTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mainListView.setVisibility(View.VISIBLE);
                    calendarView.setVisibility(View.GONE);
                }
                controller.handleArrivalTextFocusChange(v, hasFocus);
            }
        });
        arrivalTextView.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                controller.handleTextChange(arrivalTextView.getText().toString());
                mainListView.setVisibility(View.VISIBLE);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //листенер на клик на объекте
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                handleListItemClick(position);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                thatsAllFolks();
            }
        });
    }

    private void thatsAllFolks() {
        Intent intent = new Intent(this, FinalActivity.class);
        startActivity(intent);
    }

    private void handleListItemClick(int position) {
        Log.e("handle", "item click"); //интересно а почему нет Log(String)?
        String itemText = controller.getListItemText(position);
        //служебные сообщения не надо кликать, уродливо но переделывать поздно третий час ночи воскресенья
        if (itemText.equals(getText(R.string.not_found)) || itemText.equals(getText(R.string.more_symbols)))
            return;

        if (departureTextView.hasFocus()) {
            departureTextView.setText(controller.getListItemText(position));
            arrivalTextView.setVisibility(View.VISIBLE);
            controller.departureStationSet = true;
            if (controller.arrivalStationSet == false) {
                arrivalTextView.requestFocus();
            }
        } else {
            controller.arrivalStationSet = true;
            arrivalTextView.setText(itemText);
        }
        mainListView.setVisibility(View.INVISIBLE);
        if (controller.arrivalStationSet && controller.departureStationSet) {
            timeToPickDate();
        }
    }

    private void timeToPickDate() {
        mainListView.setVisibility(View.INVISIBLE);
        calendarView.setVisibility(View.VISIBLE);
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
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