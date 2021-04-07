package com.cropster.roastingsimulation.service;

import com.cropster.roastingsimulation.RoastingSimulationApplication;
import com.cropster.roastingsimulation.facility.entity.Facility;
import com.cropster.roastingsimulation.facility.service.FacilityService;
import com.cropster.roastingsimulation.greencoffee.entity.GreenCoffee;
import com.cropster.roastingsimulation.greencoffee.repository.GreenCoffeeRepository;
import com.cropster.roastingsimulation.greencoffee.service.GreenCoffeeServiceImpl;
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

import java.util.Arrays;

@ActiveProfiles("test")
@Transactional
@SpringBootTest(classes = RoastingSimulationApplication.class)
@ExtendWith(MockitoExtension.class)
public class GreenCoffeeServiceTest {

    @MockBean
    RandomGenerationService randomGenerationService;
    @MockBean
    FacilityService facilityService;
    @Autowired
    GreenCoffeeRepository greenCoffeeRepository;
    @InjectMocks
    @Autowired
    GreenCoffeeServiceImpl greenCoffeeService;

    private static final String FACILITY_NAME = "Facility-A";

    @BeforeEach
    public void setupMocks(){
        MockitoAnnotations.openMocks(this);
        Mockito.when(randomGenerationService.getRandomGreenCoffeeName())
                .thenReturn("Coffea liberica");
        Mockito.when(randomGenerationService.getRandomInitialGreenCoffeeAmount())
                .thenReturn(1000);
        Mockito.when(facilityService.retrieve(FACILITY_NAME))
                .thenReturn(new Facility(FACILITY_NAME));
    }

    @Test
    public void createTest(){
        Facility facility = facilityService.retrieve(FACILITY_NAME);
        GreenCoffee greenCoffee = greenCoffeeService.create("Ethiopia Sidamo",2021,facility);
        Assertions.assertEquals(2021,greenCoffee.getAmount());
        Assertions.assertEquals("Ethiopia Sidamo",greenCoffee.getName());
    }

    @Test
    public void createWithIllegalAmountFails(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> greenCoffeeService.create("Ethiopia Sidamo",1,
                        facilityService.retrieve(FACILITY_NAME)));
    }

    @Test
    public void createRandomForFacilityTest(){
        GreenCoffee greenCoffee = greenCoffeeService.createRandomForFacility(
                facilityService.retrieve(FACILITY_NAME));

        Assertions.assertEquals("Coffea liberica",greenCoffee.getName());
        Assertions.assertEquals(1000,greenCoffee.getAmount());
    }

    @Test
    public void getRandomFromFacilityTest(){
        GreenCoffee greenCoffee1 = new GreenCoffee();
        greenCoffee1.setName("Ethiopia Sidamo");
        greenCoffee1.setAmount(3000);
        greenCoffee1.setFacility(facilityService.retrieve(FACILITY_NAME));
        GreenCoffee greenCoffee2 = new GreenCoffee();
        greenCoffee2.setName("Coffea Liberica");
        greenCoffee2.setAmount(4000);
        greenCoffee2.setFacility(facilityService.retrieve(FACILITY_NAME));
        greenCoffeeRepository.save(greenCoffee1);
        greenCoffeeRepository.save(greenCoffee2);

        Assertions.assertTrue(Arrays.asList(greenCoffee1.getName(),greenCoffee2.getName()).contains(
                greenCoffeeService.getRandomFromFacility(facilityService.retrieve(FACILITY_NAME)).getName()
        ));
        Assertions.assertTrue(Arrays.asList(greenCoffee1.getAmount(),greenCoffee2.getAmount()).contains(
                greenCoffeeService.getRandomFromFacility(facilityService.retrieve(FACILITY_NAME)).getAmount()
        ));
    }

    @Test
    public void getRandomFromFacilityWithNoCoffee(){
        Assertions.assertNull(greenCoffeeService.getRandomFromFacility(facilityService.retrieve(FACILITY_NAME)));
    }
}
