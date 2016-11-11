package ru.labbit.tutu.stationmaster.tasks;

import android.os.AsyncTask;

import ru.labbit.tutu.stationmaster.entities.AllStations;
import ru.labbit.tutu.stationmaster.utils.json.JSONResourceReader;

public class LoadStationsTask extends AsyncTask<JSONResourceReader, Void, AllStations> {

    @Override
    protected AllStations doInBackground(JSONResourceReader... readers) {

        return readers[0].constructUsingGson(AllStations.class);
    }
}
