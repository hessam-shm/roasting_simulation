package com.cropster.roastingsimulation.machine.service;

import com.cropster.roastingsimulation.facility.entity.Facility;
import com.cropster.roastingsimulation.greencoffee.entity.GreenCoffee;
import com.cropster.roastingsimulation.machine.entity.Machine;

import java.util.Date;

public interface MachineService {

    Machine create(String name, int capacity, Facility facility);
    Machine createRandomForFacility(Facility facility);
    Machine getRandomFromFacility(Facility facility);
    void roast(double startWeight, double endWeight, Date startTime, Date endtime,
               String productName, GreenCoffee greenCoffee);
    Machine retrieve(String name, Facility facility);
    Machine upsert(Machine machine);
}
