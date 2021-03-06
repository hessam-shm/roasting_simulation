package com.cropster.roastingsimulation.roastingprocess.service;

import com.cropster.roastingsimulation.common.log.Logger;
import com.cropster.roastingsimulation.greencoffee.entity.GreenCoffee;
import com.cropster.roastingsimulation.greencoffee.service.GreenCoffeeService;
import com.cropster.roastingsimulation.machine.entity.Machine;
import com.cropster.roastingsimulation.common.random.RandomGenerationService;
import com.cropster.roastingsimulation.roastingprocess.entity.RoastingProcess;
import com.cropster.roastingsimulation.roastingprocess.repository.RoastingProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.time.Duration;
import java.util.Date;

@Transactional
@Service
public class RoastingProcessServiceImpl implements RoastingProcessService{

    @Autowired
    RoastingProcessRepository roastingProcessRepository;
    @Autowired
    GreenCoffeeService greenCoffeeService;
    @Autowired
    RandomGenerationService randomGenerationService;

    @Override
    public RoastingProcess create(double startWeight, double endWeight, Date startTime, Date endTime,
                                  String productName, Machine machine, GreenCoffee greenCoffee) {
        if(machineIsBusy(machine,startTime,endTime) || startWeight > greenCoffee.getAmount())
            return null;

        RoastingProcess roastingProcess = new RoastingProcess();
        roastingProcess.setStartWeight(startWeight);
        roastingProcess.setEndWeight(endWeight);
        roastingProcess.setStartTime(startTime);
        roastingProcess.setEndTime(endTime);
        roastingProcess.setProductName(productName);
        roastingProcess.setMachine(machine);
        roastingProcess.setGreenCoffee(greenCoffee);

        RoastingProcess savedRoastingProcess = null;
        try{
            savedRoastingProcess = roastingProcessRepository.save(roastingProcess);

        } catch (ConstraintViolationException e){
            Logger.logException(this.getClass(),e);
            Logger.info(this.getClass(),e.getMessage());
            throw(e);
        }
        return savedRoastingProcess;

    }

    @Override
    public RoastingProcess createRandomWithGreenCoffee(Machine machine, GreenCoffee greenCoffee) {
        double startWeight = randomGenerationService.getRandomRoastingStartWeight(machine.getCapacity());
        Date startTime = randomGenerationService.getRandomStartTime();
        return create(startWeight,
                startWeight - randomGenerationService.getRandomWeightLoss(startWeight),
                startTime,calculateEndTime(startTime,randomGenerationService.getRandomDuraion()),
                randomGenerationService.getRandomProductName(),machine,greenCoffee);
    }

    private Date calculateEndTime(Date startTime,int duration){
        return new Date(startTime.toInstant().plus(Duration.ofMinutes(duration)).toEpochMilli());
    }

    private boolean machineIsBusy(Machine machine, Date startTime, Date endTime){
        return !roastingProcessRepository.findCollidingTime(machine.getId(),startTime,endTime).isEmpty();
    }
}
