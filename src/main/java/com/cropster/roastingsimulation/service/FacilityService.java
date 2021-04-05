package com.cropster.roastingsimulation.service;

import com.cropster.roastingsimulation.entity.Facility;

public interface FacilityService {

    Facility retrieve(String name);
    Facility create(String name);
    Facility createRandom();
    Facility getRandom();
}
