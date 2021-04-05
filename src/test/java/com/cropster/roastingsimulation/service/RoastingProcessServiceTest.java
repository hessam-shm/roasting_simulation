package com.cropster.roastingsimulation.service;

import com.cropster.roastingsimulation.RoastingSimulationApplication;
import com.cropster.roastingsimulation.facility.entity.Facility;
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
    @MockBean
    MachineService machineService;
    @MockBean
    GreenCoffeeService greenCoffeeService;
    @InjectMocks
    @Autowired
    RoastingProcessServiceImpl roastingProcessService;

    @BeforeEach
    public void setupMocks() {
        Facility facility = new Facility("Facility-A");

        GreenCoffee greenCoffee = new GreenCoffee();
        greenCoffee.setName("Coffee-Test");
        greenCoffee.setAmount(2000);
        greenCoffee.setFacility(facility);

        Machine machine = new Machine();
        machine.setName("Machine-A");
        machine.setCapacity(60);
        machine.setFacility(facility);

        MockitoAnnotations.openMocks(this);
        Mockito.when(randomGenerationService.getRandomRoastingStartWeight(60))
                .thenReturn(20);
        Mockito.when(randomGenerationService.getRandomWeightLoss(20))
                .thenReturn(18);
        Mockito.when(randomGenerationService.getRandomStartTime())
                .thenReturn(new Date(Instant.now().minus(Duration.ofMinutes(80)).toEpochMilli()));
        Mockito.when(randomGenerationService.getRandomProductName())
                .thenReturn("Product-A");
        Mockito.when(randomGenerationService.getRandomDuraion())
                .thenReturn(10);
        Mockito.when(greenCoffeeService.create("Coffee-Test", 2000, new Facility("Facility-A")))
                .thenReturn(greenCoffee);
        Mockito.when(machineService.create("Machine-A", 60, new Facility("Facility-A")))
                .thenReturn(machine);
    }

    @Test
    public void createTest() {
        GreenCoffee greenCoffee = greenCoffeeService.create("Coffee-Test", 2000, new Facility("Facility-A"));
        Machine machine = machineService.create("Machine-A", 60, new Facility("Facility-A"));
        RoastingProcess roastingProcess = roastingProcessService.create(
                30, 28, new Date(Instant.now().minus(Duration.ofMinutes(35)).toEpochMilli()),
                new Date(Instant.now().minus(Duration.ofMinutes(25)).toEpochMilli()), "Product-A",
                machine, greenCoffee);
        Assertions.assertEquals("Product-A", roastingProcess.getProductName());
        Assertions.assertEquals(28, roastingProcess.getEndWeight());
        Assertions.assertEquals(2000, roastingProcess.getGreenCoffee().getAmount());
        Assertions.assertEquals(60, roastingProcess.getMachine().getCapacity());
    }

    @Test
    public void createRandomTest() {
        RoastingProcess roastingProcess = roastingProcessService.createRandomWithGreenCoffee(
                machineService.create("Machine-A", 60, new Facility("Facility-A")),
                greenCoffeeService.create("Coffee-Test", 2000, new Facility("Facility-A")));
        Assertions.assertEquals("Product-A", roastingProcess.getProductName());
        Assertions.assertEquals(20, roastingProcess.getStartWeight());
    }

    @Test
    public void createWithIllegalWeightFails() {
        GreenCoffee greenCoffee = greenCoffeeService.create("Coffee-Test", 2000,
                new Facility("Facility-A"));
        Machine machine = machineService.create("Machine-A", 60, new Facility("Facility-A"));
        Assertions.assertThrows(ConstraintViolationException.class, () ->
                roastingProcessService.create(30, 32,
                new Date(Instant.now().minus(Duration.ofMinutes(35)).toEpochMilli()),
                new Date(Instant.now().minus(Duration.ofMinutes(25)).toEpochMilli()),
                "Product-A", machine, greenCoffee));
    }
}
