package com.cropster.roastingsimulation.entity;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "roasting_processes")
public class RoastingProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "start_weight")
    private double startWeight;

    @Column(name = "end_weight")
    private double endWeight;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @NotNull(message = "Product name cannot be empty!")
    @Column(name = "product_name")
    private String productName;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startDate) {
        this.startTime = startDate;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endDate) {
        this.endTime = endDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoastingProcess that = (RoastingProcess) o;
        return Double.compare(that.getStartWeight(), getStartWeight()) == 0 && Double.compare(that.getEndWeight(), getEndWeight()) == 0 && Objects.equals(getStartTime(), that.getStartTime()) && Objects.equals(getEndTime(), that.getEndTime()) && Objects.equals(getProductName(), that.getProductName()) && Objects.equals(getGreenCoffee(), that.getGreenCoffee());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartWeight(), getEndWeight(), getStartTime(), getEndTime(), getProductName(), getGreenCoffee());
    }

    @Override
    public String toString() {
        return "RoastingProcess{" +
                "id=" + id +
                ", startWeight=" + startWeight +
                ", endWeight=" + endWeight +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", productName='" + productName + '\'' +
                ", greenCoffee=" + greenCoffee +
                '}';
    }
}
