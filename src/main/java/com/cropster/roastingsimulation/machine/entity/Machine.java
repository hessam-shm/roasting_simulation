package com.cropster.roastingsimulation.machine.entity;

import com.cropster.roastingsimulation.facility.entity.Facility;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Machine machine = (Machine) o;
        return getCapacity() == machine.getCapacity() && Objects.equals(getName(), machine.getName()) && Objects.equals(getFacility(), machine.getFacility());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCapacity(), getFacility());
    }

    @Override
    public String toString() {
        return "Machine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", facility=" + facility +
                '}';
    }
}
