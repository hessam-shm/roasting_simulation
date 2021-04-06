package com.cropster.roastingsimulation.facility.controller;

import com.cropster.roastingsimulation.facility.entity.Facility;
import com.cropster.roastingsimulation.facility.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@RequestMapping("/facility")
public class FacilityController {

    @Autowired
    FacilityService facilityService;

    @GetMapping(produces = "application/json")
    public Facility getFacilityForName(@RequestParam String name){
        return facilityService.retrieve(name);
    }

    @PostMapping(value = "/create", consumes = "application/json")
    public @ResponseBody ResponseEntity<Facility> createFacility(@Valid @RequestBody Facility facility){
        return new ResponseEntity<>(facilityService.create(facility.getName()), HttpStatus.OK);
    }
}
