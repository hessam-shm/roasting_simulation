package com.cropster.roastingsimulation.service;

import com.cropster.roastingsimulation.entity.GreenCoffee;
import com.cropster.roastingsimulation.entity.Machine;
import com.cropster.roastingsimulation.entity.RoastingProcess;

import java.util.Date;

public interface RoastingProcessService {

    RoastingProcess create(double startWeight, double endWeight, Date startTime, Date endTime,
                           String productName, GreenCoffee greenCoffee);

    RoastingProcess createRandomWithGreenCoffee(Machine machine, GreenCoffee greenCoffee);
}
