package ru.labbit.tutu.stationmaster.dagger;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import ru.labbit.tutu.stationmaster.activities.StationsActivity;
import ru.labbit.tutu.stationmaster.controller.Controller;

@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(StationsActivity stationsActivity);

    Context context();
    Controller controller();
}