package com.cropster.roastingsimulation.greencoffee.controller;

import com.cropster.roastingsimulation.facility.service.FacilityService;
import com.cropster.roastingsimulation.greencoffee.entity.GreenCoffee;
import com.cropster.roastingsimulation.greencoffee.service.GreenCoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@RequestMapping("/greencoffee")
public class GreenCoffeeController {

    @Autowired
    GreenCoffeeService greenCoffeeService;
    @Autowired
    FacilityService facilityService;

    @GetMapping(produces = "application/json")
    public GreenCoffee getGreenCoffee(@RequestParam String name, @RequestParam String facilityName){
        return greenCoffeeService.retrieve(name,facilityService.retrieve(facilityName));
    }

    @PostMapping(value = "/create", consumes = "application/json")
    public @ResponseBody
    ResponseEntity<GreenCoffee> createGreenCoffee(@Valid @RequestBody GreenCoffee greenCoffee){
        return new ResponseEntity<>(greenCoffeeService.create(greenCoffee.getName(),
                greenCoffee.getAmount(),greenCoffee.getFacility()), HttpStatus.OK);
    }
}
