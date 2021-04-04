package com.cropster.roastingsimulation.service;

import com.cropster.roastingsimulation.entity.Facility;
import com.cropster.roastingsimulation.entity.Machine;

public interface MachineService {

    Machine create(String name, int capacity, Facility facility);
    Machine createRandomForFacility(Facility facility);
    Machine getRandomFromFacility(Facility facility);
    Machine upsert(Machine machine);
}
