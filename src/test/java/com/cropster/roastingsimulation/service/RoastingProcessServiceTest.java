package com.cropster.roastingsimulation.service;

import com.cropster.roastingsimulation.RoastingSimulationApplication;
import com.cropster.roastingsimulation.facility.entity.Facility;
import com.cropster.roastingsimulation.facility.service.FacilityService;
import com.cropster.roastingsimulation.greencoffee.entity.GreenCoffee;
import com.cropster.roastingsimulation.greencoffee.service.GreenCoffeeService;
import com.cropster.roastingsimulation.machine.entity.Machine;
import com.cropster.roastingsimulation.machine.service.MachineService;
import com.cropster.roastingsimulation.roastingprocess.entity.RoastingProcess;
import com.cropster.roastingsimulation.roastingprocess.service.RoastingProcessServiceImpl;
import com.cropster.roastingsimulation.common.random.RandomGenerationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@ActiveProfiles("test")
@Transactional
@SpringBootTest(classes = RoastingSimulationApplication.class)
@ExtendWith(MockitoExtension.class)
public class RoastingProcessServiceTest {

    @MockBean
    RandomGenerationService randomGenerationService;
    @Autowired
    FacilityService facilityService;
    @Autowired
    MachineService machineService;
    @Autowired
    GreenCoffeeService greenCoffeeService;
    @InjectMocks
    @Autowired
    RoastingProcessServiceImpl roastingProcessService;

    private static final String FACILITY_NAME = "Facility-A";
    private static final String MACHINE_NAME = "Machine-A";
    private static final String COFFEE_NAME = "Coffee-Test";
    private static final String PRODUCT_NAME = "Product-A";

    @BeforeEach
    public void setupMocks() {

        facilityService.create(FACILITY_NAME);

        MockitoAnnotations.openMocks(this);
        Mockito.when(randomGenerationService.getRandomRoastingStartWeight(60))
                .thenReturn(20);
        Mockito.when(randomGenerationService.getRandomWeightLoss(20))
                .thenReturn(18);
        Mockito.when(randomGenerationService.getRandomStartTime())
                .thenReturn(new Date(Instant.now().minus(Duration.ofMinutes(80)).toEpochMilli()));
        Mockito.when(randomGenerationService.getRandomProductName())
                .thenReturn(PRODUCT_NAME);
        Mockito.when(randomGenerationService.getRandomDuraion())
                .thenReturn(10);
    }

    @Test
    public void createTest() {
        Facility facility = facilityService.retrieve(FACILITY_NAME);
        RoastingProcess roastingProcess = roastingProcessService.create(
                30, 28, new Date(Instant.now().minus(Duration.ofMinutes(35)).toEpochMilli()),
                new Date(Instant.now().minus(Duration.ofMinutes(25)).toEpochMilli()), PRODUCT_NAME,
                machineService.create(MACHINE_NAME, 60, facility),
                greenCoffeeService.create(COFFEE_NAME, 2000, facility));
        Assertions.assertEquals(PRODUCT_NAME, roastingProcess.getProductName());
        Assertions.assertEquals(28, roastingProcess.getEndWeight());
        Assertions.assertEquals(2000, roastingProcess.getGreenCoffee().getAmount());
        Assertions.assertEquals(60, roastingProcess.getMachine().getCapacity());
    }

    @Test
    public void createRandomTest() {
        Facility facility = facilityService.retrieve(FACILITY_NAME);
        RoastingProcess roastingProcess = roastingProcessService.createRandomWithGreenCoffee(
                machineService.create(MACHINE_NAME, 60, facility),
                greenCoffeeService.create(COFFEE_NAME, 2000, facility));
        Assertions.assertEquals(PRODUCT_NAME, roastingProcess.getProductName());
        Assertions.assertEquals(20, roastingProcess.getStartWeight());
    }

    @Test
    public void createWithIllegalWeightFails() {
        Facility facility = facilityService.retrieve(FACILITY_NAME);
        GreenCoffee greenCoffee = greenCoffeeService.create(COFFEE_NAME, 2000,facility);
        Machine machine = machineService.create(MACHINE_NAME, 60, facility);
        Assertions.assertThrows(ConstraintViolationException.class, () ->
                roastingProcessService.create(30, 32,
                new Date(Instant.now().minus(Duration.ofMinutes(35)).toEpochMilli()),
                new Date(Instant.now().minus(Duration.ofMinutes(25)).toEpochMilli()),
                PRODUCT_NAME, machine, greenCoffee));
    }
}
