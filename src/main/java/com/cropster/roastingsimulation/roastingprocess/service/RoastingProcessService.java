package com.cropster.roastingsimulation.roastingprocess.service;

import com.cropster.roastingsimulation.greencoffee.entity.GreenCoffee;
import com.cropster.roastingsimulation.machine.entity.Machine;
import com.cropster.roastingsimulation.roastingprocess.entity.RoastingProcess;

import java.util.Date;

public interface RoastingProcessService {

    RoastingProcess create(double startWeight, double endWeight, Date startTime, Date endTime,
                           String productName, Machine machine, GreenCoffee greenCoffee);

    RoastingProcess createRandomWithGreenCoffee(Machine machine, GreenCoffee greenCoffee);
}
