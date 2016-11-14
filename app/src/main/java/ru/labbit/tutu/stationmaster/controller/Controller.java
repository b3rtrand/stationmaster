package ru.labbit.tutu.stationmaster.controller;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.labbit.tutu.stationmaster.R;
import ru.labbit.tutu.stationmaster.application.App;
import ru.labbit.tutu.stationmaster.tasks.LoadStationsTask;
import ru.labbit.tutu.stationmaster.tasks.MakeStationsListTask;
import ru.labbit.tutu.stationmaster.utils.json.JSONResourceReader;
import ru.labbit.tutu.stationmaster.vos.AllStations;

public class Controller {

    public static final int MIN_CHARS_TO_SEARCH = 3;
    public static final String TAG = "CUSTOM_MESSAGE: ";
    public static final int MAX_SYMBOLS_FOR_SUPER_SEARCH = 7;

    private ControllerListener listener;
    private List<String> stationsList = new ArrayList<>();

    //пока я просто хочу переходить к шагу выбора даты только когда оба эти значения установлены
    public boolean departureStationSet;
    public boolean arrivalStationSet;

    private AllStations allStations;

    public Controller() {
        loadStations(new JSONResourceReader(App.getContext().getResources(), R.raw.allstations));
    }

    public void setControllerListener(ControllerListener listener) {
        this.listener = listener;
    }

    public void handleTextChange(String s) {
        if (s.length() >= Controller.MIN_CHARS_TO_SEARCH) {
            stationsList = createFilteredList(s);
            if (stationsList.size() == 0) {
                //вообще-то это не лучшая идея класть туда системные сообщения но время уже поджимает
                stationsList.add(App.getContext().getResources().getString(R.string.not_found));
            }
        } else {
            stationsList = Arrays.asList(App.getContext().getResources().getString(R.string.more_symbols));
        }
        listener.onStationsListChange(stationsList);
    }

    public void handleDepartureTextFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            listener.onDepatrureChange("");
        }
    }

    public void handleArrivalTextFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            listener.onArrivalChange("");
        }
    }

    public void loadStations(JSONResourceReader reader) {
        LoadStationsTask task = new LoadStationsTask();
        task.execute(reader);
        System.out.println("loading stations");
        Log.e(TAG, "loading stations");
        try {
            allStations = task.get();
            Log.e(TAG, "stations loaded");

        } catch (InterruptedException e) {
            Log.e(TAG, "InterruptedException", e);
        } catch (ExecutionException e) {
            Log.e(TAG, "ExecutionException", e);
        }
    }

    private List<String> createFilteredList(String s) {
        MakeStationsListTask task = new MakeStationsListTask(this);
        task.execute(s);
        try {
            return task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getListItemText(int position) {
        return stationsList.get(position);
    }

    public interface ControllerListener {
        void onDepatrureChange(String s);

        void onArrivalChange(String s);

        void onStationsListChange(List<String> l);
    }

    public AllStations getAllStations() {
        return allStations;
    }

    public void setAllStations(AllStations allStations) {
        this.allStations = allStations;
    }
}
