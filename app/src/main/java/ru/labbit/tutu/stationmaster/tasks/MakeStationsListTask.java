package ru.labbit.tutu.stationmaster.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;

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
        // TODO: 11.11.2016 матчить как в ctrl+shift+n
        try {
            List<City> cities = controller.getAllStations().getCitiesFrom();

            List<String> result = new ArrayList();
            Pattern p = Pattern.compile(".*"+s[0]+".*", Pattern.CASE_INSENSITIVE);
            //лямбды не поддерживаются =(

            for (Object cityObject : cities) {
                City city = (City) cityObject;
                List<Station> stations = city.getStations();
                for (Object station : stations) {
                    Station st = (Station) station;
                    if (p.matcher(st.getStationTitle()).matches() || p.matcher(st.getCityTitle()).matches()) {
                        result.add(st.getCityTitle() + " " + st.getStationTitle());
                    }
                }
            }
            return result;
        }
        catch (Exception e) {
            Log.e("OMG", "error", e);
            return null;
        }
    }
}
