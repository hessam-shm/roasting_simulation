package com.cropster.roastingsimulation.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "facilities")
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Facility name cannot be empty!")
    private String name;

    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
    private List<Machine> machines;

    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
    private List<GreenCoffee> greenCoffees;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }

    public List<GreenCoffee> getGreenCoffees() {
        return greenCoffees;
    }

    public void setGreenCoffees(List<GreenCoffee> greenCoffees) {
        this.greenCoffees = greenCoffees;
    }
}
