package com.cropster.roastingsimulation.service;

import com.cropster.roastingsimulation.entity.GreenCoffee;
import com.cropster.roastingsimulation.entity.Machine;
import com.cropster.roastingsimulation.entity.RoastingProcess;
import com.cropster.roastingsimulation.repository.RoastingProcessRepository;
import com.cropster.roastingsimulation.service.random.RandomGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Service
public class RoastingProcessServiceImpl implements RoastingProcessService{

    @Autowired
    RoastingProcessRepository roastingProcessRepository;
    @Autowired
    GreenCoffeeService greenCoffeeService;
    @Autowired
    RandomGenerationService randomGenerationService;

    public RoastingProcess create(double startWeight, double endWeight, Date startTime, Date endTime,
                                  String productName, GreenCoffee greenCoffee) {
        RoastingProcess roastingProcess = new RoastingProcess();
        roastingProcess.setStartWeight(startWeight);
        roastingProcess.setEndWeight(endWeight);
        roastingProcess.setStartTime(startTime);
        roastingProcess.setEndTime(endTime);
        roastingProcess.setProductName(productName);
        roastingProcess.setGreenCoffee(greenCoffee);

        return roastingProcessRepository.save(roastingProcess);
    }

    @Override
    public RoastingProcess createRandomWithGreenCoffee(Machine machine, GreenCoffee greenCoffee) {
        int startWeight = randomGenerationService.getRandomRoastingStartWeight(machine.getCapacity());
        Date startTime = randomGenerationService.getRandomStartTime();
        return create(startWeight,
                startWeight - randomGenerationService.getRandomWeightLoss(startWeight),
                startTime,calculateEndTime(startTime,randomGenerationService.getRandomDuraion()),
                randomGenerationService.getRandomProductName(),greenCoffee);
    }

    private Date calculateEndTime(Date startTime,int duration){
        return new Date(startTime.toInstant().plus(Duration.ofMinutes(duration)).toEpochMilli());
    }
}
