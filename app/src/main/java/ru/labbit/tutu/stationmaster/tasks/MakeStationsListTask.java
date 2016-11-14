package ru.labbit.tutu.stationmaster.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import ru.labbit.tutu.stationmaster.R;
import ru.labbit.tutu.stationmaster.application.App;
import ru.labbit.tutu.stationmaster.controller.Controller;
import ru.labbit.tutu.stationmaster.vos.City;
import ru.labbit.tutu.stationmaster.vos.Station;

public class MakeStationsListTask extends AsyncTask<String, Void, List<String>> {

    Controller controller;

    public MakeStationsListTask(Controller controller) {
        this.controller = controller;
    }

    @Override
    protected List<String> doInBackground(String... s) {
        try {
            //хотелось искать модно как в ctrl_shift+N но такая ерунда получается на этих данных
            //searchText =".*" + s[0].replaceAll("(.)", "$1.*");
            String searchText = ".*" + s[0] + ".*";
            List<City> cities = controller.getAllStations().getCitiesFrom();

            List<String> result = new ArrayList();
            //лямбды не поддерживаются =(
            Pattern p = Pattern.compile(searchText, Pattern.CASE_INSENSITIVE);

            for (Object cityObject : cities) {
                City city = (City) cityObject;
                List<Station> stations = city.getStations();
                for (Object station : stations) {
                    Station st = (Station) station;
                    String searchMe = st.getCityTitle() + " " + st.getStationTitle();
                    //не будем искать по бессмысленным словам
                    searchMe = searchMe.replaceAll(App.getContext().getResources().getString(R.string.meaningless), "");
                    if (p.matcher(searchMe).matches()) {
                        result.add(searchMe);
                    }
                }
            }
            return result;
        } catch (Exception e) {
            Log.e("OMG", "error", e);
            return null;
        }
    }
}
