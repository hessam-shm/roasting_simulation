package com.cropster.roastingsimulation.common.operations;

import com.cropster.roastingsimulation.facility.entity.Facility;
import com.cropster.roastingsimulation.facility.service.FacilityService;
import com.cropster.roastingsimulation.greencoffee.entity.GreenCoffee;
import com.cropster.roastingsimulation.greencoffee.service.GreenCoffeeService;
import com.cropster.roastingsimulation.machine.entity.Machine;
import com.cropster.roastingsimulation.machine.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Simulator {

    @Autowired
    FacilityService facilityService;
    @Autowired
    MachineService machineService;
    @Autowired
    GreenCoffeeService greenCoffeeService;

    public void simulate(){

        //all facilities
        List<Facility> facilities = facilityService.retrieveAll();
        //facilities and related machines
        Map<Facility, List<Machine>> facilitiesMachines = new HashMap<>();
        facilities.forEach(f -> facilitiesMachines.put(f,machineService.retrieveAllByFacility(f)));
        //get machine with minimum capacity of each facility
        Map<Facility, Integer> FACILITY_MIN_MACHINE_CAPACITY = new HashMap<>();
        facilities.forEach(f -> FACILITY_MIN_MACHINE_CAPACITY.put(f,
                facilitiesMachines.get(f).stream().min(
                        Comparator.comparing(Machine::getCapacity)).get().getCapacity()));

        do{
            Facility facility = facilityService.getRandom();
            machineService.roastRandom(machineService.getRandomFromFacility(facility),greenCoffeeService.getRandomFromFacility(facility));
        } while(simulationSentinel(FACILITY_MIN_MACHINE_CAPACITY));

    }

    private boolean simulationSentinel(Map<Facility,Integer> facilityMachineCapacity){
        for(Map.Entry<Facility,Integer> entry: facilityMachineCapacity.entrySet()){
            if(entry.getValue() > currentMaxStock(entry.getKey()))
                return false;
        }
        return true;
    }

    private int currentMaxStock(Facility facility){
        return greenCoffeeService.retrieveAllByFacility(facility).stream().max(
                Comparator.comparing(GreenCoffee::getAmount)).get().getAmount();

    }
}
