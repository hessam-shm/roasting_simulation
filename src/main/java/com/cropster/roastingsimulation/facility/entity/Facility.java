package com.cropster.roastingsimulation.facility.entity;

import com.cropster.roastingsimulation.greencoffee.entity.GreenCoffee;
import com.cropster.roastingsimulation.machine.entity.Machine;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "facilities", schema = "cropster", uniqueConstraints =
        @UniqueConstraint(name="unique_facility_name",columnNames = "name"))
public class Facility {

    protected Facility(){}
    public Facility(String name){
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Facility name cannot be empty!")
    private String name;

    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Machine> machines = new ArrayList<>();

    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GreenCoffee> greenCoffees = new ArrayList<>();

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

    public void addMachine(Machine machine){
        machines.add(machine);
        machine.setFacility(this);
    }

    public void addGreenCoffee(GreenCoffee greenCoffee){
        greenCoffees.add(greenCoffee);
        greenCoffee.setFacility(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Facility facility = (Facility) o;
        return getName().equals(facility.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Facility{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
