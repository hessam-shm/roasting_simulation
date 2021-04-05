package com.cropster.roastingsimulation.common.random;

import java.util.Date;

public interface RandomGenerationService {

    String getRandomGreenCoffeeName();
    String getRandomFacilityName();
    String getRandomMachineName();
    int getRandomMachineCapacity();
    int getRandomInitialGreenCoffeeAmount();
    int getRandomRoastingStartWeight(int machineCapacity);
    int getRandomDuraion();
    Date getRandomStartTime();
    int getRandomWeightLoss(int startWeight);
    String getRandomProductName();
}
