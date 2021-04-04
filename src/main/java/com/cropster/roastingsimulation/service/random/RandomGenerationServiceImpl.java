package com.cropster.roastingsimulation.service.random;

import com.cropster.roastingsimulation.repository.random.RandomNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private String appendRandomLetter(String s) {
        return s + randomNameRepository.getLetterPool().charAt(
                ThreadLocalRandom.current().nextInt(26));
    }
}
