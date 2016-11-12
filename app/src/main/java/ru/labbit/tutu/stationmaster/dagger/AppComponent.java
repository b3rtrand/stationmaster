package ru.labbit.tutu.stationmaster.dagger;

import javax.inject.Singleton;

import dagger.Component;
import ru.labbit.tutu.stationmaster.activities.MainActivity;
import ru.labbit.tutu.stationmaster.controller.Controller;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(MainActivity mainActivity);

    Controller controller();
}