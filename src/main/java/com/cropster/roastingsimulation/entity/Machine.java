package com.cropster.roastingsimulation.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "machines",
    uniqueConstraints =
        @UniqueConstraint(name = "unique_machine_per_facility", columnNames = {"facility_id","name"}))
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Machine name cannot be empty!")
    private String name;
    @Min(value = 15, message = "Capacity cannot be less than 15")
    @Max(value = 90, message = "Capacity cannot be greater than 90")
    private int capacity;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;

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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }
}
