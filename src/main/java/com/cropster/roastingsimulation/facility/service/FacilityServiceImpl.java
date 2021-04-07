package com.cropster.roastingsimulation.facility.service;

import com.cropster.roastingsimulation.common.log.Logger;
import com.cropster.roastingsimulation.common.random.RandomGenerationService;
import com.cropster.roastingsimulation.facility.entity.Facility;
import com.cropster.roastingsimulation.facility.repository.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Transactional
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
        Facility savedFacility;
        try{
            savedFacility = facilityRepository.save(facility);
        } catch (ConstraintViolationException | DataIntegrityViolationException e){
            System.out.println(e.getMessage());
            Logger.logException(this.getClass(),e);
            Logger.info(this.getClass(),e.getMessage());
            throw(e);
        }
        return savedFacility;
    }

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
    public Facility retrieveRandom(){
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

    @Override
    public List<Facility> retrieveAll() {
        List<Facility> facilities = new ArrayList<>();
        facilityRepository.findAll().forEach(facilities::add);
        return facilities;
    }
}
