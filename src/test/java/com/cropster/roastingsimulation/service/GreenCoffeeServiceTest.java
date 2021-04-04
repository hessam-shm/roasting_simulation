package com.cropster.roastingsimulation.service;

import com.cropster.roastingsimulation.RoastingSimulationApplication;
import com.cropster.roastingsimulation.entity.Facility;
import com.cropster.roastingsimulation.entity.GreenCoffee;
import com.cropster.roastingsimulation.repository.GreenCoffeeRepository;
import com.cropster.roastingsimulation.service.random.RandomGenerationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;

@ActiveProfiles("test")
@Transactional
@SpringBootTest(classes = RoastingSimulationApplication.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GreenCoffeeServiceTest {

    @Mock
    RandomGenerationService randomGenerationService;
    @Mock
    FacilityService facilityService;
    @Autowired
    GreenCoffeeRepository greenCoffeeRepository;
    @InjectMocks
    @Autowired
    GreenCoffeeServiceImpl greenCoffeeService;

    @BeforeEach
    public void setupMocks(){
        MockitoAnnotations.openMocks(this);
        Mockito.when(randomGenerationService.getRandomGreenCoffeeName())
                .thenReturn("Coffea liberica");
        Mockito.when(randomGenerationService.getRandomInitialGreenCoffeeAmount())
                .thenReturn(1000);
        Mockito.when(facilityService.retrieve("Facility-A"))
                .thenReturn(new Facility("Facility-A"));
    }

    @Test
    public void createTest(){
        Facility facility = facilityService.retrieve("Facility-A");
        GreenCoffee greenCoffee = greenCoffeeService.create("Ethiopia Sidamo",2021,facility);
        Assertions.assertEquals(2021,greenCoffee.getAmount());
        Assertions.assertEquals("Ethiopia Sidamo",greenCoffee.getName());
    }

    @Test
    public void createWithIllegalAmountFails(){
        Assertions.assertThrows(ConstraintViolationException.class,
                () -> greenCoffeeService.create("Ethiopia Sidamo",1,
                        facilityService.retrieve("Facility-A")));
    }

    @Test
    public void createRandomForFacilityTest(){
        GreenCoffee greenCoffee = greenCoffeeService.create(
                randomGenerationService.getRandomGreenCoffeeName(),
                randomGenerationService.getRandomInitialGreenCoffeeAmount(),
                facilityService.retrieve("Facility-A"));

        Assertions.assertEquals("Coffea liberica",greenCoffee.getName());
        Assertions.assertEquals(1000,greenCoffee.getAmount());
    }

    @Test
    public void getRandomFromFacilityTest(){
        GreenCoffee greenCoffee1 = new GreenCoffee();
        greenCoffee1.setName("Ethiopia Sidamo");
        greenCoffee1.setAmount(3000);
        greenCoffee1.setFacility(facilityService.retrieve("Facility-A"));
        GreenCoffee greenCoffee2 = new GreenCoffee();
        greenCoffee2.setName("Coffea Liberica");
        greenCoffee2.setAmount(4000);
        greenCoffee2.setFacility(facilityService.retrieve("Facility-A"));
        greenCoffeeRepository.save(greenCoffee1);
        greenCoffeeRepository.save(greenCoffee2);

        Assertions.assertTrue(Arrays.asList(greenCoffee1.getName(),greenCoffee2.getName()).contains(
                greenCoffeeService.getRandomFromFacility(facilityService.retrieve("Facility-A")).getName()
        ));
        Assertions.assertTrue(Arrays.asList(greenCoffee1.getAmount(),greenCoffee2.getAmount()).contains(
                greenCoffeeService.getRandomFromFacility(facilityService.retrieve("Facility-A")).getAmount()
        ));
    }

    @Test
    public void getRandomFromFacilityWithNoCoffee(){
        Assertions.assertNull(greenCoffeeService.getRandomFromFacility(facilityService.retrieve("Facility-A")));
    }
}
