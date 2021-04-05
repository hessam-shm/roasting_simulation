package com.cropster.roastingsimulation.greencoffee.service;

import com.cropster.roastingsimulation.facility.entity.Facility;
import com.cropster.roastingsimulation.greencoffee.entity.GreenCoffee;

public interface GreenCoffeeService {

    GreenCoffee create(String name, int amount, Facility facility);

    GreenCoffee createRandomForFacility(Facility facility);

    GreenCoffee getRandomFromFacility(Facility facility);

    GreenCoffee reduceInStockAmount(GreenCoffee greenCoffee, int consumed);

    GreenCoffee retrieve(String name, Facility facility);

    void persist(GreenCoffee greenCoffee);
}