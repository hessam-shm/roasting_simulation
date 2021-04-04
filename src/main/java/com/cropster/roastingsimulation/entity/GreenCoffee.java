package com.cropster.roastingsimulation.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "green_coffees",uniqueConstraints = @UniqueConstraint(name = "unique_name_per_facility",
        columnNames ={"facility_id","name"}))
public class GreenCoffee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Green coffee name cannot be empty!")
    private String name;

    @Min(value = 500, message = "Amount cannot be less than 500")
    @Max(value = 10000, message = "Amount cannot be greater than 10000")
    private int amount;

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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }
}
