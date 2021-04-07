package com.cropster.roastingsimulation.common.random;

import java.util.Date;

public interface RandomGenerationService {

    String getRandomGreenCoffeeName();

    String getRandomFacilityName();

    String getRandomMachineName();

    int getRandomMachineCapacity();

    int getRandomInitialGreenCoffeeAmount();

    double getRandomRoastingStartWeight(int machineCapacity);

    int getRandomDuraion();

    Date getRandomStartTime();

    double getRandomWeightLoss(double startWeight);

    String getRandomProductName();
}
