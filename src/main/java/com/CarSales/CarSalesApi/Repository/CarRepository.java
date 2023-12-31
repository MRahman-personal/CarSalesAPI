package com.CarSales.CarSalesApi.Repository;

import com.CarSales.CarSalesApi.JPAModel.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Integer> {
}