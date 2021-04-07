package com.cropster.roastingsimulation.common.random;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class RandomNameRepository {
    private final String LETTERs_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final List<String> GREEN_COFFEE_NAMES = Arrays.asList("Ethiopia Sidamo","Daterra Peaberry","natural Guji",
            "Kiryama","Catuai","Capulines","Arabica");

    public String getLetterPool(){
        return LETTERs_POOL;
    }

    public List<String> getGreenCoffeeNames(){
        return GREEN_COFFEE_NAMES;
    }

}
