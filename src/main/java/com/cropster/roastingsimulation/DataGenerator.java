package com.cropster.roastingsimulation;

import com.cropster.roastingsimulation.entity.Facility;
import com.cropster.roastingsimulation.service.FacilityService;
import com.cropster.roastingsimulation.service.GreenCoffeeService;
import com.cropster.roastingsimulation.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

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

    @Override
    public void run(ApplicationArguments args) throws Exception {

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
    }
}