package com.cropster.roastingsimulation.machine.service;

import com.cropster.roastingsimulation.facility.entity.Facility;
import com.cropster.roastingsimulation.greencoffee.entity.GreenCoffee;
import com.cropster.roastingsimulation.machine.entity.Machine;
import com.cropster.roastingsimulation.roastingprocess.entity.RoastingProcess;

import java.util.Date;
import java.util.List;

public interface MachineService {

    Machine create(String name, int capacity, Facility facility);

    Machine createRandomForFacility(Facility facility);

    Machine getRandomFromFacility(Facility facility);

    RoastingProcess roast(double startWeight, double endWeight, Date startTime, Date endtime,
                          String productName, Machine machine, GreenCoffee greenCoffee);

    RoastingProcess roastRandom(Machine machine, GreenCoffee greenCoffee);

    Machine retrieve(String name, Facility facility);

    List<Machine> retrieveAllByFacility(Facility facility);

    Machine upsert(Machine machine);
}
