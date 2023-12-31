package com.CarSales.CarSalesApi.Controller;

import com.CarSales.CarSalesApi.JPAModel.Car;
import com.CarSales.CarSalesApi.JPAModel.Sale;
import com.CarSales.CarSalesApi.JPAModel.User;
import com.CarSales.CarSalesApi.Service.SaleDataService;
import com.CarSales.CarSalesApi.Service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    SaleDataService saleDataService;
    @Autowired
    UserDataService userDataService;

    @GetMapping("/allusers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userDataService.getAllUsers();

        if(!users.isEmpty()){
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = saleDataService.getAllCars();

        if (!cars.isEmpty()) {
            return new ResponseEntity<>(cars, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/sales")
    public ResponseEntity<List<Sale>> getAllSales() {
        List<Sale> sales = saleDataService.getAllSales();

        if (!sales.isEmpty()) {
            return new ResponseEntity<>(sales, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("users/deleteall")
    public ResponseEntity<String> deleteAllUsers() {
        try {
            userDataService.deleteAllUsers();

            return new ResponseEntity<>("All users are deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("cars/deleteall")
    public ResponseEntity<String> deleteAllCars() {
        try {
            saleDataService.deleteAllCars();

            return new ResponseEntity<>("All cars are deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("sales/deleteall")
    public ResponseEntity<String> deleteAllSales() {
        try {
            saleDataService.deleteAllSales();

            return new ResponseEntity<>("All sales are deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
