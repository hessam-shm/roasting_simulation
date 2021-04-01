package com.cropster.roastingsimulation.entity;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "roasting_processes")
public class RoastingProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double startWeight;
    private double endWeight;

    @NotNull(message = "Start date cannot be empty!")
    private Date startDate;

    @NotNull(message = "End date cannot be empty!")
    private Date endDate;

    @NotNull(message = "Product name cannot be empty!")
    @Column(name = "product_name")
    private String productName;

    @ManyToOne
    @JoinColumn(name = "green_coffee_id", nullable = false)
    private GreenCoffee greenCoffee;

    @AssertTrue(message = "Start weight must be greater than end weight!")
    private boolean isValid(){
        return this.startWeight > this.endWeight;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(double startWeight) {
        this.startWeight = startWeight;
    }

    public double getEndWeight() {
        return endWeight;
    }

    public void setEndWeight(double endWeight) {
        this.endWeight = endWeight;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public GreenCoffee getGreenCoffee() {
        return greenCoffee;
    }

    public void setGreenCoffee(GreenCoffee greenCoffee) {
        this.greenCoffee = greenCoffee;
    }
}
