package com.cropster.roastingsimulation.service;

import com.cropster.roastingsimulation.RoastingSimulationApplication;
import com.cropster.roastingsimulation.facility.entity.Facility;
import com.cropster.roastingsimulation.facility.service.FacilityService;
import com.cropster.roastingsimulation.greencoffee.service.GreenCoffeeService;
import com.cropster.roastingsimulation.machine.entity.Machine;
import com.cropster.roastingsimulation.machine.repository.MachineRepository;
import com.cropster.roastingsimulation.machine.service.MachineServiceImpl;
import com.cropster.roastingsimulation.roastingprocess.service.RoastingProcessService;
import com.cropster.roastingsimulation.common.random.RandomGenerationService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;

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

    private static final String FACILITY_NAME = "Facility-A";

    @BeforeEach
    public void setupMocks(TestInfo testInfo){
        MockitoAnnotations.openMocks(this);
        Mockito.when(randomGenerationService.getRandomMachineName())
                .thenReturn("Machine-X");
        Mockito.when(randomGenerationService.getRandomMachineCapacity())
                .thenReturn(88);
        Mockito.when(facilityService.retrieve(FACILITY_NAME))
                .thenReturn(new Facility(FACILITY_NAME));
    }

    @Test
    public void createTest(){
        Facility facility = facilityService.retrieve(FACILITY_NAME);
        Machine machine = machineService.create("Machine-T",50,facility);
        Assertions.assertEquals(machine.getCapacity(),50);
        Assertions.assertEquals(machine.getFacility().getName(),FACILITY_NAME);
    }

    @Test
    public void createWithillegalCapacityFails(){
        Assertions.assertThrows(ConstraintViolationException.class,
                () -> machineService.create("Machine-T",100,
                        facilityService.retrieve(FACILITY_NAME)));
    }

    @Test
    public void sameMachineNameForSameFacilityFails(){
        machineService.create("Machine-A",70, facilityService.retrieve(FACILITY_NAME));
        Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> machineService.create("Machine-A",60,
                        facilityService.retrieve(FACILITY_NAME)));
    }

    @Test
    public void createRandomForFacilityTest(){
        Machine machine = machineService.createRandomForFacility(facilityService.retrieve(FACILITY_NAME));
        Assertions.assertEquals("Machine-X",machine.getName());
        Assertions.assertEquals(88,machine.getCapacity());
    }

    @Test
    public void getRandomFromFacilityTest(){
        Machine machine1 = new Machine();
        machine1.setName("Machine-A");
        machine1.setCapacity(randomGenerationService.getRandomMachineCapacity());
        machine1.setFacility(facilityService.retrieve(FACILITY_NAME));
        Machine machine2 = new Machine();
        machine2.setName("Machine-B");
        machine2.setCapacity(randomGenerationService.getRandomMachineCapacity());
        machine2.setFacility(facilityService.retrieve(FACILITY_NAME));
        machineRepository.save(machine1);
        machineRepository.save(machine2);

        Assertions.assertTrue(Arrays.asList(machine1.getName(),machine2.getName()).contains(
                machineService.getRandomFromFacility(facilityService.retrieve(FACILITY_NAME)).getName()));
        Assertions.assertEquals(88,machineService.getRandomFromFacility(
                facilityService.retrieve(FACILITY_NAME)).getCapacity());
    }

    @Test
    public void getRandomFromFacilityWithNoMachine(){
        Assertions.assertNull(machineService.getRandomFromFacility(facilityService.retrieve(FACILITY_NAME)));
    }

}
