package ru.labbit.tutu.stationmaster.controller;

import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.labbit.tutu.stationmaster.tasks.LoadStationsTask;
import ru.labbit.tutu.stationmaster.tasks.MakeStationsListTask;
import ru.labbit.tutu.stationmaster.utils.json.JSONResourceReader;
import ru.labbit.tutu.stationmaster.vos.AllStations;

public class Controller {

    public static final int MIN_CHARS_TO_SEARCH = 2;
    public static final String TAG = "CUSTOM_MESSAGE: ";

    private ControllerListener listener;

    private AllStations allStations;

    public Controller() {
        //intial stuff goes here
//        listener = null; //todo убрать и посмотреть что будет
    }

    public void setControllerListener(ControllerListener listener) {
        this.listener = listener;
    }

    public void handleTextChange(String s) {
        if (s.length() > Controller.MIN_CHARS_TO_SEARCH) {
            List<String> l = populateList(s);
            //TODO: отдельно обрабатывать пустой результат
            l.add("пустой результат");
            listener.onStationsListChange(l);
        } else {
            // TODO: 11.11.2016 как-то прятать его чтоли подумать
            // TODO: 11.11.2016  если мало символов стало то что-то надо делать
        }
    }

    public void handleDepartureTextFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            listener.onDepatrureChange("");
        } else {
            //TODO возможно при потере фокуса мы захотим сбрасывать текст в дефолт если не выбрана осмысленная станция
        }
    }

    public void loadStations(JSONResourceReader reader) {
        LoadStationsTask task = new LoadStationsTask();
        task.execute(reader);
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

    private List<String> populateList(String s) {
        MakeStationsListTask task = new MakeStationsListTask(this);
        //TODO FIND OUT if must rework to use listeners
        task.execute(s);
        try {
            Log.e(TAG,"building list of stations that qualify");
            return task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface ControllerListener {
        void onDepatrureChange(String s);

        void onStationsListChange(List<String> l);
    }

    public AllStations getAllStations() {
        return allStations;
    }

    public void setAllStations(AllStations allStations) {
        this.allStations = allStations;
    }
}
