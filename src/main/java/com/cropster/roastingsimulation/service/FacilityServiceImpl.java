package com.cropster.roastingsimulation.service;

import com.cropster.roastingsimulation.entity.Facility;
import com.cropster.roastingsimulation.repository.FacilityRepository;
import com.cropster.roastingsimulation.service.random.RandomGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class FacilityServiceImpl implements FacilityService{

    @Autowired
    FacilityRepository facilityRepository;
    @Autowired
    RandomGenerationService randomGenerationService;

    @Override
    public Facility retrieve(String name){
        return facilityRepository.findByName(name);
    }

    @Override
    public Facility create(String name){
        Facility facility = new Facility(name);
        Facility savedFacility = null;
        try{
            savedFacility = facilityRepository.save(facility);
        } catch (ConstraintViolationException | DataIntegrityViolationException e){
            System.out.println(e.getMessage());
            throw(e);
        }
        return savedFacility;
    }

    //could check the name instead of using constraint but to keep the code consistance used constraint
    @Override
    public Facility createRandom(){
        boolean repeatedName = true;
        String name;
        do{
            name = randomGenerationService.getRandomFacilityName();
            if(facilityRepository.findByName(name) == null)
                repeatedName = false;
        } while (repeatedName);
        return create(name);
    }

    @Override
    public Facility getRandom(){
        Facility facility = null;
        long count = facilityRepository.count();
        if(count <= 0)
            return facility;
        Page<Facility> facilityPage = facilityRepository.findAll(
                PageRequest.of(ThreadLocalRandom.current().nextInt((int)count),1));
        if(facilityPage.hasContent())
            facility = facilityPage.getContent().get(0);
        return facility;
    }
}
