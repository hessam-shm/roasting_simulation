package com.cropster.roastingsimulation.service;

import com.cropster.roastingsimulation.RoastingSimulationApplication;
import com.cropster.roastingsimulation.facility.entity.Facility;
import com.cropster.roastingsimulation.facility.service.FacilityService;
import com.cropster.roastingsimulation.greencoffee.entity.GreenCoffee;
import com.cropster.roastingsimulation.greencoffee.service.GreenCoffeeService;
import com.cropster.roastingsimulation.machine.service.MachineService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@ActiveProfiles("test")
@Transactional
@SpringBootTest(classes = RoastingSimulationApplication.class)
public class MachineServiceIT {

    @Autowired
    FacilityService facilityService;
    @Autowired
    GreenCoffeeService greenCoffeeService;
    @Autowired
    MachineService machineService;

    @Test
    public void roastTest(){
        Facility facility = facilityService.create("Facility-Z");
        GreenCoffee greenCoffee = greenCoffeeService.create("Bean",1000,
                facility);
        machineService.roast(20,18,
                new Date(Instant.now().minus(Duration.ofMinutes(60)).toEpochMilli()),
                new Date(Instant.now().minus(Duration.ofMinutes(54)).toEpochMilli()),
                "Test-Roast",greenCoffee);
        Assertions.assertEquals(998,greenCoffee.getAmount());
    }
}
