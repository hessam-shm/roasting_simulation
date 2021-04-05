package com.cropster.roastingsimulation.greencoffee.service;

import com.cropster.roastingsimulation.common.log.Logger;
import com.cropster.roastingsimulation.facility.entity.Facility;
import com.cropster.roastingsimulation.common.random.RandomGenerationService;
import com.cropster.roastingsimulation.greencoffee.entity.GreenCoffee;
import com.cropster.roastingsimulation.greencoffee.repository.GreenCoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class GreenCoffeeServiceImpl implements GreenCoffeeService{

    @Autowired
    GreenCoffeeRepository greenCoffeeRepository;

    @Autowired
    RandomGenerationService randomGenerationService;

    @Override
    public GreenCoffee create(String name, int amount, Facility facility) {
        GreenCoffee greenCoffee = new GreenCoffee();
        greenCoffee.setName(name);
        greenCoffee.setAmount(amount);
        facility.addGreenCoffee(greenCoffee);

        GreenCoffee savedGreenCoffee = null;
        try {
            savedGreenCoffee = greenCoffeeRepository.save(greenCoffee);
        } catch (ConstraintViolationException e){
            Logger.logException(this.getClass(),e);
            Logger.info(this.getClass(),e.getMessage());
            throw(e);
        }
        return savedGreenCoffee;
    }

    @Override
    public GreenCoffee createRandomForFacility(Facility facility) {
        boolean repeated = true;
        String name;
        do{
            name = randomGenerationService.getRandomGreenCoffeeName();
            if(greenCoffeeRepository.findByNameAndFacility_Id(name, facility.getId()) == null)
                repeated = false;
        }while (repeated);

        return create(name,randomGenerationService.getRandomInitialGreenCoffeeAmount(),facility);
    }

    @Override
    public GreenCoffee getRandomFromFacility(Facility facility) {
        GreenCoffee greenCoffee = null;
        long count = greenCoffeeRepository.countAllByFacility_Id(facility.getId());
        if(count <= 0)
            return greenCoffee;
        Page<GreenCoffee> greenCoffeePage = greenCoffeeRepository.findAllByFacility_Id(
                facility.getId(), PageRequest.of(ThreadLocalRandom.current().nextInt((int)count),1));
        if(greenCoffeePage.hasContent())
            greenCoffee = greenCoffeePage.getContent().get(0);
        return greenCoffee;
    }

    @Override
    public GreenCoffee reduceInStockAmount(GreenCoffee greenCoffee, int consumed) {
        if(greenCoffee.getAmount() < consumed)
            throw new IllegalStateException("Cannot consume more than stock amount");
        greenCoffee.setAmount(greenCoffee.getAmount() - consumed);
        return greenCoffeeRepository.save(greenCoffee);
    }

    @Override
    public GreenCoffee retrieve(String name, Facility facility) {
        return greenCoffeeRepository.findByNameAndFacility_Id(name,facility.getId());
    }

    @Override
    public void persist(GreenCoffee greenCoffee) {
        greenCoffeeRepository.save(greenCoffee);
    }


}
