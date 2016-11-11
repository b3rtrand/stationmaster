package ru.labbit.tutu.stationmaster.application;

import android.app.Application;

import ru.labbit.tutu.stationmaster.dagger.AppComponent;
import ru.labbit.tutu.stationmaster.dagger.AppModule;
import ru.labbit.tutu.stationmaster.dagger.DaggerAppComponent;

public class App extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getComponent() {
        return component;
    }

}
