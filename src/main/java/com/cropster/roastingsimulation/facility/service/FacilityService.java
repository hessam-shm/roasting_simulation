package com.cropster.roastingsimulation.facility.service;

import com.cropster.roastingsimulation.facility.entity.Facility;

public interface FacilityService {

    Facility retrieve(String name);
    Facility create(String name);
    Facility createRandom();
    Facility getRandom();
}
