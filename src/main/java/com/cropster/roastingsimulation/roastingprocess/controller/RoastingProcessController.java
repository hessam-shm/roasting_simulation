package com.cropster.roastingsimulation.roastingprocess.controller;

import com.cropster.roastingsimulation.roastingprocess.entity.RoastingProcess;
import com.cropster.roastingsimulation.roastingprocess.service.RoastingProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/roast")
public class RoastingProcessController {

    @Autowired
    RoastingProcessService roastingProcessService;

    @PostMapping(value = "/roast", consumes = "application/json")
    public @ResponseBody ResponseEntity<RoastingProcess> roast(RoastingProcess roastingProcess){
        return new ResponseEntity<>(roastingProcessService.create(
                roastingProcess.getStartWeight(),roastingProcess.getEndWeight(),
                roastingProcess.getStartTime(),roastingProcess.getEndTime(),roastingProcess.getProductName(),
                roastingProcess.getMachine(), roastingProcess.getGreenCoffee()), HttpStatus.OK);
    }
}
