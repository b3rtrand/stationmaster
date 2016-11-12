package ru.labbit.tutu.stationmaster.tasks;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class MakeStationsListTask extends AsyncTask<String, Void, List<String>> {

    @Override
    protected List<String> doInBackground(String... s) {
        // TODO: 11.11.2016 матчить как в ctrl+shift+n
        //TODO: матчить по нескольким полям а не только по getName
//        List stations = Arrays.asList(
//                new Station("moscow okt","moscow"),
//                new Station("something else","whatever"),
//                new Station("some more stuff whatever","fgdgdfg"),
//                new Station("moscow stuff","fgdgdfg"),
//                new Station("whatever","fgdgdfg")
//        );
        List<String> result = new ArrayList();
//        Pattern p = Pattern.compile(".*"+s[0]+".*", Pattern.CASE_INSENSITIVE);
//        //лямбды не поддерживаются =(
//        for (Object st2 : stations) {
//            Station st = (Station) st2;
//            if (p.matcher(st.getName()).matches()) {
//                result.add(st.getName());
//            }
//        }
        return result;
    }
}
