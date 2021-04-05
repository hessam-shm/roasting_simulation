package com.cropster.roastingsimulation.service;

import com.cropster.roastingsimulation.RoastingSimulationApplication;
import com.cropster.roastingsimulation.entity.Facility;
import com.cropster.roastingsimulation.entity.GreenCoffee;
import com.cropster.roastingsimulation.entity.Machine;
import com.cropster.roastingsimulation.repository.MachineRepository;
import com.cropster.roastingsimulation.service.random.RandomGenerationService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

@ActiveProfiles("test")
@Transactional
@SpringBootTest(classes = RoastingSimulationApplication.class)
@ExtendWith(MockitoExtension.class)
public class MachineServiceTest {

    @MockBean
    RandomGenerationService randomGenerationService;
    @MockBean
    FacilityService facilityService;
    @Autowired
    MachineRepository machineRepository;
    @InjectMocks
    @Autowired
    MachineServiceImpl machineService;
    @Autowired
    GreenCoffeeService greenCoffeeService;
    @Autowired
    RoastingProcessService roastingProcessService;


    @BeforeEach
    public void setupMocks(TestInfo testInfo){
        MockitoAnnotations.openMocks(this);
        Mockito.when(randomGenerationService.getRandomMachineName())
                .thenReturn("Machine-X");
        Mockito.when(randomGenerationService.getRandomMachineCapacity())
                .thenReturn(88);
        Mockito.when(facilityService.retrieve("Facility-A"))
                .thenReturn(new Facility("Facility-A"));
    }

    @Test
    public void createTest(){
        Facility facility = facilityService.retrieve("Facility-A");
        Machine machine = machineService.create("Machine-T",50,facility);
        Assertions.assertEquals(machine.getCapacity(),50);
        Assertions.assertEquals(machine.getFacility().getName(),"Facility-A");
    }

    @Test
    public void createWithillegalCapacityFails(){
        Assertions.assertThrows(ConstraintViolationException.class,
                () -> machineService.create("Machine-T",100,
                        facilityService.retrieve("Facility-A")));
    }

    @Test
    public void sameMachineNameForSameFacilityFails(){
        machineService.create("Machine-A",70, facilityService.retrieve("Facility-A"));
        Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> machineService.create("Machine-A",60,
                        facilityService.retrieve("Facility-A")));
    }

    @Test
    public void createRandomForFacilityTest(){
        Machine machine = machineService.createRandomForFacility(facilityService.retrieve("Facility-A"));
        Assertions.assertEquals("Machine-X",machine.getName());
        Assertions.assertEquals(88,machine.getCapacity());
    }

    @Test
    public void getRandomFromFacilityTest(){
        Machine machine1 = new Machine();
        machine1.setName("Machine-A");
        machine1.setCapacity(randomGenerationService.getRandomMachineCapacity());
        machine1.setFacility(facilityService.retrieve("Facility-A"));
        Machine machine2 = new Machine();
        machine2.setName("Machine-B");
        machine2.setCapacity(randomGenerationService.getRandomMachineCapacity());
        machine2.setFacility(facilityService.retrieve("Facility-A"));
        machineRepository.save(machine1);
        machineRepository.save(machine2);

        Assertions.assertTrue(Arrays.asList(machine1.getName(),machine2.getName()).contains(
                machineService.getRandomFromFacility(facilityService.retrieve("Facility-A")).getName()));
        Assertions.assertEquals(88,machineService.getRandomFromFacility(
                facilityService.retrieve("Facility-A")).getCapacity());
    }

    @Test
    public void getRandomFromFacilityWithNoMachine(){
        Assertions.assertNull(machineService.getRandomFromFacility(facilityService.retrieve("Facility-A")));
    }

    @Test
    public void roastTest(){
        Facility facility = facilityService.create("Facility-A");
        GreenCoffee greenCoffee = greenCoffeeService.create("Bean",1000,
                facility);
        machineService.roast(20,18,
                new Date(Instant.now().minus(Duration.ofMinutes(60)).toEpochMilli()),
                new Date(Instant.now().minus(Duration.ofMinutes(54)).toEpochMilli()),
                "Test-Roast",greenCoffee);
        Assertions.assertEquals(998,greenCoffee.getAmount());
    }

}
