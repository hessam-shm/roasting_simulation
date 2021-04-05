package com.cropster.roastingsimulation.service;

import com.cropster.roastingsimulation.entity.Facility;
import com.cropster.roastingsimulation.entity.GreenCoffee;
import com.cropster.roastingsimulation.entity.Machine;
import com.cropster.roastingsimulation.repository.MachineRepository;
import com.cropster.roastingsimulation.service.random.RandomGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.Date;
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
            System.out.println(e.getMessage());
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

    @Override
    public void roast(double startWeight, double endWeight, Date startTime, Date endtime,
                      String productName, GreenCoffee greenCoffee) {
        roastingProcessService.create(startWeight,endWeight,startTime,endtime,productName,greenCoffee);
        greenCoffeeService.reduceInStockAmount(greenCoffee,calculateConsumedWeight(startWeight,endWeight));
    }

    @Override
    public Machine retrieve(String name, Facility facility) {
        return machineRepository.findByNameAndFacility_Id(name,facility.getId());
    }


    @Override
    public Machine upsert(Machine machine){
        return machineRepository.save(machine);
    }

    private int calculateConsumedWeight(double startWeight, double endWeight){
        return Math.toIntExact(Math.round(startWeight - endWeight));
    }
}
