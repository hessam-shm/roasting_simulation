package com.cropster.roastingsimulation.facility.service;

import com.cropster.roastingsimulation.facility.entity.Facility;

import java.util.List;

public interface FacilityService {

    Facility retrieve(String name);

    Facility create(String name);

    Facility createRandom();

    Facility retrieveRandom();

    List<Facility> retrieveAll();
}
