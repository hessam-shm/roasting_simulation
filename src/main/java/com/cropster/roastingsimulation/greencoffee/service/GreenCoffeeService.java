package com.cropster.roastingsimulation.greencoffee.service;

import com.cropster.roastingsimulation.facility.entity.Facility;
import com.cropster.roastingsimulation.greencoffee.entity.GreenCoffee;

import java.util.List;

public interface GreenCoffeeService {

    GreenCoffee create(String name, int amount, Facility facility);

    GreenCoffee createRandomForFacility(Facility facility);

    GreenCoffee getRandomFromFacility(Facility facility);

    GreenCoffee reduceInStockAmount(GreenCoffee greenCoffee, double consumed);

    GreenCoffee retrieve(String name, Facility facility);

    List<GreenCoffee> retrieveAllByFacility(Facility facility);

    void persist(GreenCoffee greenCoffee);
}