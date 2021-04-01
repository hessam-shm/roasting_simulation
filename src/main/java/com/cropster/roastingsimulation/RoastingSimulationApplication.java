package com.cropster.roastingsimulation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class RoastingSimulationApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoastingSimulationApplication.class, args);
    }

}
