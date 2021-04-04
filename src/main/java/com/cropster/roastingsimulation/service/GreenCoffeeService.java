package com.cropster.roastingsimulation.service;

import com.cropster.roastingsimulation.entity.Facility;
import com.cropster.roastingsimulation.entity.GreenCoffee;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface GreenCoffeeService {

    GreenCoffee create(String name, int amount, Facility facility);

    GreenCoffee createRandomForFacility(Facility facility);

    GreenCoffee getRandomFromFacility(Facility facility);

}