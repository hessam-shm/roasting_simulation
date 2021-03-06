package com.cropster.roastingsimulation.common.operations;

import com.cropster.roastingsimulation.common.log.Logger;
import com.cropster.roastingsimulation.facility.entity.Facility;
import com.cropster.roastingsimulation.facility.service.FacilityService;
import com.cropster.roastingsimulation.greencoffee.service.GreenCoffeeService;
import com.cropster.roastingsimulation.machine.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Profile("!test")
@Component
public class DataGenerator implements ApplicationRunner {

    @Autowired
    FacilityService facilityService;
    @Autowired
    MachineService machineService;
    @Autowired
    GreenCoffeeService greenCoffeeService;
    @Autowired
    Simulator simulator;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Logger.info(this.getClass(),"Generating initial data...");
        //generate 2 facilities and keep a reference to them instead of refetch
        Facility facility1 = facilityService.createRandom();
        Facility facility2 = facilityService.createRandom();

        //create 3 machines for each facility
        IntStream.range(0,3).forEach(i ->
                machineService.createRandomForFacility(facility1));
        IntStream.range(0,3).forEach(i ->
                machineService.createRandomForFacility(facility2));

        //create 5 greencoffee for each facility
        IntStream.range(0,5).forEach(i ->
                greenCoffeeService.createRandomForFacility(facility1));
        IntStream.range(0,5).forEach(i ->
                greenCoffeeService.createRandomForFacility(facility2));

        Logger.info(this.getClass(),"Data generation finished.");

        Thread.sleep(3000);
        Logger.info(this.getClass(),"starting simulation...");
        long start = System.currentTimeMillis();

        simulator.simulate();
        long end = System.currentTimeMillis();
        Logger.info(this.getClass(),"Simulation finished in " +
                TimeUnit.MILLISECONDS.toMinutes(end-start) + " minute(s) and " +
                TimeUnit.MILLISECONDS.toSeconds((end-start)%60000) + " second(s)");
    }
}
