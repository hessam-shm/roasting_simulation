package com.cropster.roastingsimulation.machine.service;

import com.cropster.roastingsimulation.common.log.Logger;
import com.cropster.roastingsimulation.facility.entity.Facility;
import com.cropster.roastingsimulation.greencoffee.entity.GreenCoffee;
import com.cropster.roastingsimulation.greencoffee.service.GreenCoffeeService;
import com.cropster.roastingsimulation.machine.entity.Machine;
import com.cropster.roastingsimulation.machine.repository.MachineRepository;
import com.cropster.roastingsimulation.roastingprocess.entity.RoastingProcess;
import com.cropster.roastingsimulation.roastingprocess.service.RoastingProcessService;
import com.cropster.roastingsimulation.common.random.RandomGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class MachineServiceImpl implements MachineService{

    @Autowired
    MachineRepository machineRepository;
    @Autowired
    RandomGenerationService randomGenerationService;
    @Autowired
    RoastingProcessService roastingProcessService;
    @Autowired
    GreenCoffeeService greenCoffeeService;

    @Override
    public Machine create(String name, int capacity, Facility facility){
        Machine machine = new Machine();
        machine.setName(name);
        machine.setCapacity(capacity);
        facility.addMachine(machine);

        Machine savedMachine = null;
        try{
            savedMachine = machineRepository.save(machine);
        } catch (ConstraintViolationException e){
            Logger.logException(this.getClass(),e);
            Logger.info(this.getClass(),e.getMessage());
            throw(e);
        }
        return savedMachine;
    }

    @Override
    public Machine createRandomForFacility(Facility facility){
        boolean repeated = true;
        String name;
        do{
            name = randomGenerationService.getRandomMachineName();
            if(machineRepository.findByNameAndFacility_Id(name, facility.getId()) == null)
                repeated = false;
        } while (repeated);
        return create(name,randomGenerationService.getRandomMachineCapacity(),facility);
    }

    @Override
    public Machine getRandomFromFacility(Facility facility){
        Machine machine = null;
        long count = machineRepository.countAllByFacility_Id(facility.getId());
        if(count <= 0)
            return machine;
        Page<Machine> machinePage = machineRepository.findAllByFacility_Id(facility.getId(),
                PageRequest.of(ThreadLocalRandom.current().nextInt((int)count),1));
        if(machinePage.hasContent())
            machine = machinePage.getContent().get(0);
        return machine;
    }

    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.SERIALIZABLE)
    @Override
    public RoastingProcess roast(double startWeight, double endWeight, Date startTime, Date endtime,
                                 String productName, Machine machine, GreenCoffee greenCoffee) {
        RoastingProcess roastingProcess = null;
        try{
            roastingProcess = roastingProcessService.create(
                    startWeight,endWeight,startTime,endtime,productName,machine,greenCoffee);
        } catch (ConstraintViolationException e){
            Logger.error(this.getClass(),"roasing process for machine " + machine.getId() + " and " +
                    "green coffee " + greenCoffee.getName() + " faild");
        }
        if(roastingProcess != null)
            greenCoffeeService.reduceInStockAmount(
                    greenCoffee,calculateConsumedWeight(startWeight,endWeight));
        return roastingProcess;
    }

    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.SERIALIZABLE)
    @Override
    public RoastingProcess roastRandom(Machine machine, GreenCoffee greenCoffee){
        RoastingProcess roastingProcess = null;
        try{
            roastingProcess = roastingProcessService.createRandomWithGreenCoffee(machine,greenCoffee);
        } catch (ConstraintViolationException e){
            Logger.error(this.getClass(),"roasing process for machine " + machine.getId() + " and " +
                    "green coffee " + greenCoffee.getName() + " faild");
        }
        if(roastingProcess != null)
            greenCoffeeService.reduceInStockAmount(greenCoffee,
                    calculateConsumedWeight(roastingProcess.getStartWeight(),roastingProcess.getEndWeight()));
        return roastingProcess;
    }

    @Override
    public Machine retrieve(String name, Facility facility) {
        return machineRepository.findByNameAndFacility_Id(name,facility.getId());
    }

    @Override
    public List<Machine> retrieveAllByFacility(Facility facility) {
        List<Machine> machines = new ArrayList<>();
        machineRepository.findAllByFacility_Id(facility.getId()).forEach(machines::add);
        return machines;
    }


    @Override
    public Machine upsert(Machine machine){
        return machineRepository.save(machine);
    }

    private int calculateConsumedWeight(double startWeight, double endWeight){
        return Math.toIntExact(Math.round(startWeight - endWeight));
    }
}
