package com.CarSales.CarSalesApi.JPAModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int saleId;
    private int buyerId;
    private int sellerId;
    private int carId;
    private BigDecimal salePrice;
    private boolean saleCompleted;

    public Sale(int buyerId, int sellerId, int carId, BigDecimal salePrice, boolean saleCompleted) {
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.carId = carId;
        this.salePrice = salePrice;
        this.saleCompleted = saleCompleted;
    }

    public Sale() {
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public boolean isSaleCompleted() {
        return saleCompleted;
    }

    public void setSaleCompleted(boolean saleCompleted) {
        this.saleCompleted = saleCompleted;
    }
}

