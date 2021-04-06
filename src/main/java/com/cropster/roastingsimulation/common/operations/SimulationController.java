package com.cropster.roastingsimulation.common.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/simulate")
public class SimulationController {

    @Autowired
    Simulator simulator;

    @PostMapping
    public void simulate(){
        simulator.simulate();
    }

    @GetMapping("/downloadResult")
    public void getSimulateResult(){

    }
}
