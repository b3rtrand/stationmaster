package ru.labbit.tutu.stationmaster.entities;

import java.util.List;

public class AllStations {

    private List<City> citiesFrom;
    private List<City> citiesTo;

    public List<City> getCitiesFrom() {
        return citiesFrom;
    }

    public void setCitiesFrom(List<City> citiesFrom) {
        this.citiesFrom = citiesFrom;
    }

    public List<City> getCitiesTo() {
        return citiesTo;
    }

    public void setCitiesTo(List<City> citiesTo) {
        this.citiesTo = citiesTo;
    }
}
