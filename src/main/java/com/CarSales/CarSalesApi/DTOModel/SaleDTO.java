package com.CarSales.CarSalesApi.DTOModel;

import jakarta.validation.constraints.Min;
import java.math.BigDecimal;

public class SaleDTO {
    @Min(1)
    private int buyerId;
    @Min(1)
    private int sellerId;
    @Min(1)
    private int carId;
    @Min(1)
    private BigDecimal salePrice;
    private boolean saleCompleted;

    public SaleDTO(int buyerId, int sellerId, int carId, BigDecimal salePrice, boolean saleCompleted) {
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.carId = carId;
        this.salePrice = salePrice;
        this.saleCompleted = saleCompleted;
    }

    public SaleDTO() {
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


