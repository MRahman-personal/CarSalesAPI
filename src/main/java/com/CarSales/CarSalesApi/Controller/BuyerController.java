package com.CarSales.CarSalesApi.Controller;

import com.CarSales.CarSalesApi.JPAModel.Car;
import com.CarSales.CarSalesApi.JPAModel.Sale;
import com.CarSales.CarSalesApi.Service.SaleDataService;
import com.CarSales.CarSalesApi.Service.UserDataService;
import com.CarSales.CarSalesApi.DTOModel.SaleDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer")
public class BuyerController {

    @Autowired
    SaleDataService saleDataService;
    @Autowired
    UserDataService userDataService;

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getAllCars() {
        try {
            List<Car> cars = saleDataService.getAllCars();

            if(cars.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(cars, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/bought/{buyerId}")
    public ResponseEntity<List<Car>> getBoughtCars(@PathVariable int buyerId) {
        try {
            List<Sale> sales = saleDataService.getAllSales();
            List<Car> cars = saleDataService.getAllCars().stream()
                    .filter(car -> sales.stream()
                            .anyMatch(sale -> sale.getCarId() == car.getListingId()
                                    && sale.isSaleCompleted()
                                    && sale.getBuyerId() == buyerId))
                    .collect(Collectors.toList());

            if(cars.isEmpty() || sales.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(cars, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/progress/{buyerId}")
    public ResponseEntity<List<Sale>> getSalesInProgress(@PathVariable int buyerId) {
        try {
            List<Sale> sales = saleDataService.getAllSales();
            List<Car> cars = saleDataService.getAllCars().stream()
                    .filter(car -> sales.stream()
                            .anyMatch(sale -> sale.getCarId() == car.getListingId()
                                    && sale.isSaleCompleted()
                                    && sale.getBuyerId() == buyerId))
                    .toList();

            if(cars.isEmpty() || sales.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(sales, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable int id) {
        Optional<Car> carOptional = saleDataService.findCarById(id);

        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            return new ResponseEntity<>(car, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/buy")
    public ResponseEntity<String> createSale(@Valid @RequestBody SaleDTO saleDTO) {
        try {
            saleDataService.saveSale(new Sale(saleDTO.getBuyerId(), saleDTO.getSellerId(), saleDTO.getCarId(), saleDTO.getSalePrice(), saleDTO.isSaleCompleted()));
            return new ResponseEntity<>("Sale was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/cancelSale/{id}")
    public ResponseEntity<String> deleteSale(@PathVariable int id) {
        try {
            saleDataService.deleteSaleById(id);
            return new ResponseEntity<>("Sale was deleted successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/sales/{id}")
    public ResponseEntity<Sale> getSale(@PathVariable int id) {
        Optional<Sale> saleOptional = saleDataService.findSaleById(id);

        if (saleOptional.isPresent()) {
            Sale sale = saleOptional.get();
            return new ResponseEntity<>(sale, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}


