package com.cropster.roastingsimulation.machine.controller;

import com.cropster.roastingsimulation.facility.service.FacilityService;
import com.cropster.roastingsimulation.machine.entity.Machine;
import com.cropster.roastingsimulation.machine.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@RequestMapping("/machine")
public class MachineController {

    @Autowired
    MachineService machineService;
    @Autowired
    FacilityService facilityService;

    @GetMapping
    public Machine getMachine(@RequestParam String name, @RequestParam String facilityName){
        return machineService.retrieve(name, facilityService.retrieve(facilityName));
    }

    @PostMapping(value = "/create", consumes = "application/json")
    public @ResponseBody
    ResponseEntity<Machine> createMachine(@Valid @RequestBody Machine machine){
        return new ResponseEntity<>(machineService.create(machine.getName(),
                machine.getCapacity(),machine.getFacility()), HttpStatus.OK);
    }
}
