package com.cropster.roastingsimulation.common.random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

//range numbers can be externalized
@Service
public class RandomGenerationServiceImpl implements RandomGenerationService {

    @Autowired
    RandomNameRepository randomNameRepository;

    @Override
    public String getRandomGreenCoffeeName() {
        return randomNameRepository.getGreenCoffeeNames().get(
                ThreadLocalRandom.current().nextInt(randomNameRepository.getGreenCoffeeNames().size())
        );
    }

    @Override
    public String getRandomFacilityName() {
        return appendRandomLetter("Facility-");
    }

    @Override
    public String getRandomMachineName() {
        return appendRandomLetter("Machine-");
    }

    @Override
    public int getRandomMachineCapacity() {
        return ThreadLocalRandom.current().nextInt(15,91);
    }

    @Override
    public int getRandomInitialGreenCoffeeAmount() {
        return ThreadLocalRandom.current().nextInt(500,10001);
    }

    @Override
    public int getRandomRoastingStartWeight(int machineCapacity) {
        int lowerBound = (int)(65*(machineCapacity/100.0f));
        return ThreadLocalRandom.current().nextInt(lowerBound,machineCapacity+1);
    }

    @Override
    public int getRandomDuraion() {
        return ThreadLocalRandom.current().nextInt(5,16);
    }

    @Override
    public Date getRandomStartTime() {
        long now = Instant.now().toEpochMilli();
        long yesterday = Instant.now().minus(Duration.ofDays(1)).toEpochMilli();
        return new Date(ThreadLocalRandom.current().nextLong(now,yesterday+1));
    }

    @Override
    public int getRandomWeightLoss(int startWeight) {
        int upperBound =  (int)(15*(startWeight/100.0f));
        int lowerBound =  (int)(8*(startWeight/100.0f));
        return ThreadLocalRandom.current().nextInt(lowerBound,upperBound+1);
    }

    @Override
    public String getRandomProductName() {
        return appendRandomLetter("Product-");
    }



    private String appendRandomLetter(String s) {
        return s + randomNameRepository.getLetterPool().charAt(
                ThreadLocalRandom.current().nextInt(26));
    }
}
