package ru.labbit.tutu.stationmaster.controller;

import java.util.List;

public class Controller {

    //TODO убрать их куда-то в статику
    public static final int MIN_CHARS_TO_SEARCH = 2;
    public static final String TAG = "CUSTOM MESSAGE";

    private ControllerListener listener;

    public Controller () {
        //intial stuff goes here
        listener = null; //todo убрать и посмотреть что будет
    }

    public void setControllerListener(ControllerListener listener) {
        this.listener = listener;
    }

    public String whatever() {
        return "nice";
    }

    public void loadStationsData() {
        listener.onDepatrureChange("it works");
    }

    public void handleTextChange(String s) {
        if (s.length() > Controller.MIN_CHARS_TO_SEARCH) {
//                    populateList(textField.getText().toString());

        }
        else {
            // TODO: 11.11.2016 как-то прятать его чтоли подумать
            // TODO: 11.11.2016  если мало символов стало то что-то надо делать
        }
    }
//    private void loadStations() {
//        JSONResourceReader reader = new JSONResourceReader(getResources(), R.raw.allstations);
//        LoadStationsTask task = new LoadStationsTask();
//        task.execute(reader);
//        Log.e("","lets do stuff");
//        try {
//            AllStations ast = task.get();
//            Station st = ast.getCitiesTo().get(0).getStations().get(0);
//            Log.e("",ast.getCitiesTo().get(0).getStations().get(0).getStationTitle());
//            Log.e("","that was city");
//            List result = Arrays.asList(st.getStationTitle());
//
//        } catch (InterruptedException e) {
//            Log.e(TAG,"InterruptedException",e);
//        } catch (ExecutionException e) {
//            Log.e(TAG,"ExecutionException",e);
//        }
//    }
//
//    private void populateList(String s) {
//        MakeStationsListTask task = new MakeStationsListTask();
//        //TODO rework to use listeners
//        task.execute(s);
//        try {
//            List result = task.get();
//            ListView listView = (ListView) findViewById(R.id.stations_list_view);
//            listView.setAdapter(new ArrayAdapter<>(this,
//                    android.R.layout.simple_list_item_1, result));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }

    public interface ControllerListener {
        public void onDepatrureChange(String s);
        public void onStationsListChange(List<String> l);
    }
}
