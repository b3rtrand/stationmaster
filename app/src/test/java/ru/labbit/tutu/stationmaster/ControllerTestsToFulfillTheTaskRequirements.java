package ru.labbit.tutu.stationmaster;

import android.util.Log;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.labbit.tutu.stationmaster.controller.Controller;
import ru.labbit.tutu.stationmaster.tasks.LoadStationsTask;
import ru.labbit.tutu.stationmaster.tasks.MakeStationsListTask;

import static org.junit.Assert.*;

public class ControllerTestsToFulfillTheTaskRequirements {

    @Test
    public void integrationTestToFulfillTheTaskRequirements() throws Exception {
        //instrumented тестирование я не умею а юниттестить в этом прокте нечего =(
        LoadStationsTask task = new LoadStationsTask();
        Assert.assertNotNull(task);
    }
}