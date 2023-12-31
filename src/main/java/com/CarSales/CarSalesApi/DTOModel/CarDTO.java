package com.CarSales.CarSalesApi.DTOModel;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class CarDTO {
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String color;
    @NotBlank
    private String drivetrain;
    @Min(1)
    private int mpg;
    @NotBlank
    private String transmission;
    @NotBlank
    private String engine;
    @NotBlank
    private String vin;
    @NotBlank
    private String mileage;
    @Min(1)
    private BigDecimal price;

    public CarDTO() {
    }

    public CarDTO(String color, String drivetrain, int mpg, String transmission, String engine, String vin, String mileage) {
        this.color = color;
        this.drivetrain = drivetrain;
        this.mpg = mpg;
        this.transmission = transmission;
        this.engine = engine;
        this.vin = vin;
        this.mileage = mileage;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDrivetrain() {
        return drivetrain;
    }

    public void setDrivetrain(String drivetrain) {
        this.drivetrain = drivetrain;
    }

    public int getMpg() {
        return mpg;
    }

    public void setMpg(int mpg) {
        this.mpg = mpg;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

