package com.cropster.roastingsimulation.service;

import com.cropster.roastingsimulation.RoastingSimulationApplication;
import com.cropster.roastingsimulation.facility.entity.Facility;
import com.cropster.roastingsimulation.facility.repository.FacilityRepository;
import com.cropster.roastingsimulation.facility.service.FacilityServiceImpl;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@ActiveProfiles("test")
@Transactional
@SpringBootTest(classes = RoastingSimulationApplication.class)
@ExtendWith(MockitoExtension.class)
public class FacilityServiceTest {

    @MockBean
    RandomGenerationService randomGenerationService;
    @Autowired
    FacilityRepository facilityRepository;
    @InjectMocks
    @Autowired
    FacilityServiceImpl facilityService;

    @BeforeEach
    public void setupMocks(){
        MockitoAnnotations.openMocks(this);
        Mockito.when(randomGenerationService.getRandomFacilityName())
                .thenReturn("Facility-X");
    }

    @Test
    public void createTest(){
        Facility facilityA = facilityService.create("Facility-A");
        Facility facilityB = facilityService.create("Facility-B");
        Assertions.assertEquals(facilityA.getName(),"Facility-A");
        Assertions.assertEquals(facilityB.getName(),"Facility-B");
    }

    @Test
    public void repeatedNameCreationFailes(){
        facilityRepository.save(new Facility("Facility-T"));
        Assertions.assertThrows(DataIntegrityViolationException.class,() -> facilityService.create("Facility-T"));
    }

    @Test
    public void createRandomTest(){
        Assertions.assertNotNull(facilityService.createRandom().getName());
        Assertions.assertEquals("Facility-X",facilityService.getRandom().getName());
    }

    @Test
    public void getRandomTest(){
        Facility facilityA = facilityRepository.save(new Facility("Facility-A"));
        Facility facilityB = facilityRepository.save(new Facility("Facility-B"));
        Facility randomFacility = facilityService.getRandom();
        Assertions.assertTrue(
                Arrays.asList(facilityA.getName(),facilityB.getName()).contains(randomFacility.getName()));
    }

    @Test
    public void getRandomWithoutData(){
        Assertions.assertNull(facilityService.getRandom());
    }
}
