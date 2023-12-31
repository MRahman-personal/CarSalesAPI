package com.CarSales.CarSalesApi.Service;

import com.CarSales.CarSalesApi.Repository.SaleRepository;
import com.CarSales.CarSalesApi.JPAModel.Sale;
import com.CarSales.CarSalesApi.Repository.CarRepository;
import com.CarSales.CarSalesApi.JPAModel.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleDataService {

    @Autowired
    CarRepository carRepository;

    @Autowired
    SaleRepository saleRepository;

    public Optional<Car> findCarById(int id){
        return carRepository.findById(id);
    }

    public void save(Car car) {
        carRepository.save(car);
    }

    public void deleteCarById(int id){
        carRepository.deleteById(id);
    }

    public List<Sale> getAllSales() {
        return (List<Sale>) saleRepository.findAll();
    }

    public List<Car> getAllCars() {
        return (List<Car>) carRepository.findAll();
    }

    public Optional<Sale> findSaleById(int id) {
        return saleRepository.findById(id);
    }

    public void saveSale(Sale sale) {
        saleRepository.save(sale);
    }

    public void deleteSaleById(int id) {
        saleRepository.deleteById(id);
    }

    public void deleteAllCars(){
        carRepository.deleteAll();
    }
    public void deleteAllSales(){
        saleRepository.deleteAll();
    }
}
