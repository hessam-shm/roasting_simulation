package com.cropster.roastingsimulation.repository.random;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class RandomNameRepository {
    String LETTERs_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    List<String> GREEN_COFFEE_NAMES = Arrays.asList("Ethiopia Sidamo","Daterra Peaberry","natural Guji",
            "Kiryama","Catuai","Capulines","Arabica");

    public String getLetterPool(){
        return LETTERs_POOL;
    }

    public List<String> getGreenCoffeeNames(){
        return GREEN_COFFEE_NAMES;
    }


}
