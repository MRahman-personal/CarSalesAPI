package com.CarSales.CarSalesApi.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.CarSales.CarSalesApi.JPAModel.Car;
import com.CarSales.CarSalesApi.JPAModel.Sale;
import com.CarSales.CarSalesApi.Service.SaleDataService;
import com.CarSales.CarSalesApi.DTOModel.CarDTO;
import com.CarSales.CarSalesApi.DTOModel.SaleDTO;
import com.CarSales.CarSalesApi.CarSalesApplication;
import com.CarSales.CarSalesApi.Service.UserDataService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
public class SellerController {
    @Autowired
    SaleDataService saleDataService;
    @Autowired
    UserDataService userDataService;
    @Autowired
    private CarSalesApplication.PubsubOutboundGateway messagingGateway;
    @GetMapping("/sold{sellerId}")
    public ResponseEntity<List<Car>> getSoldCars(@PathVariable int sellerId) {
        try {

            List<Sale> sales = saleDataService.getAllSales();
            List<Car> cars = saleDataService.getAllCars().stream()
                    .filter(car -> sales.stream()
                            .anyMatch(sale -> sale.getCarId() == car.getListingId()
                                    && sale.isSaleCompleted()
                                    && sale.getSellerId() == sellerId))
                    .collect(Collectors.toList());

            if(cars.isEmpty() || sales.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(cars, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/progress/{sellerId}")
    public ResponseEntity<List<Sale>> getInProgressSales(@PathVariable int sellerId) {
        try {
            List<Sale> sales = saleDataService.getAllSales();
            List<Car> cars = saleDataService.getAllCars().stream()
                    .filter(car -> sales.stream()
                            .anyMatch(sale -> sale.getCarId() == car.getListingId()
                                    && !sale.isSaleCompleted()
                                    && sale.getSellerId() == sellerId))
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
    @GetMapping("/sales/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable int id) {
        Optional<Sale> saleOptional = saleDataService.findSaleById(id);

        if (saleOptional.isPresent()) {
            Sale sale = saleOptional.get();
            return new ResponseEntity<>(sale, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/sell")
    public ResponseEntity<String> createCar(@Valid @RequestBody CarDTO carDTO) {
        try {
            saleDataService.save(new Car(carDTO.getColor(), carDTO.getDrivetrain(), carDTO.getMpg(), carDTO.getTransmission(), carDTO.getEngine(), carDTO.getVin(), carDTO.getMileage()));
            return new ResponseEntity<>("Car was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSale(@PathVariable int id, @RequestBody SaleDTO saleDTO) {
        Optional<Sale> sale = saleDataService.findSaleById(id);

        if (sale.isPresent()) {
            Sale _sale = sale.get();

            if(saleDTO.isSaleCompleted() != _sale.isSaleCompleted())
                _sale.setSaleCompleted(saleDTO.isSaleCompleted());
            if(saleDTO.getSellerId() > 0)
                _sale.setSellerId(saleDTO.getSellerId());
            if(saleDTO.getBuyerId() > 0)
                _sale.setBuyerId(saleDTO.getBuyerId());
            if(saleDTO.getCarId() > 0)
                _sale.setCarId(saleDTO.getCarId());
            if(saleDTO.getSalePrice() != null)
                _sale.setSalePrice(saleDTO.getSalePrice());

            saleDataService.saveSale(_sale);
            return new ResponseEntity<>("Sale was updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find Sale with id " + id, HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/approve/{id}")
    public ResponseEntity<String> approveSale(@PathVariable int id) {
        Optional<Sale> sale = saleDataService.findSaleById(id);
        String email = "";

        if (sale.isPresent()) {
            Sale _sale = sale.get();
            _sale.setSaleCompleted(true);

            if(userDataService.findUserById(_sale.getBuyerId()).isPresent())
                email = userDataService.findUserById(_sale.getBuyerId()).get().getEmailAddress();

            saleDataService.saveSale(_sale);
            messagingGateway.sendToPubsub(email + "," + _sale.getSaleId());
            return new ResponseEntity<>("Sale was updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find Sale with id " + id, HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/cars/update/{carId}")
    public ResponseEntity<String> updateCar(@PathVariable int carId,@RequestBody CarDTO carDTO) {
        Optional<Car> car = saleDataService.findCarById(carId);

        if (car.isPresent()) {
            Car _car = getCar(carDTO, car.get());

            saleDataService.save(_car);
            return new ResponseEntity<>("Car was updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find Car with id " + carId, HttpStatus.NOT_FOUND);
        }
    }
    private static Car getCar(CarDTO carDTO, Car _car) {

        if(carDTO.getCity() != null)
            _car.setCity(carDTO.getCity());
        if(carDTO.getState() != null)
            _car.setState(carDTO.getState());
        if(carDTO.getColor() != null)
            _car.setColor(carDTO.getColor());
        if(carDTO.getDrivetrain() != null)
            _car.setDrivetrain(carDTO.getDrivetrain());
        if(carDTO.getMpg() > 0)
            _car.setMpg(carDTO.getMpg());
        if(carDTO.getTransmission() != null)
            _car.setTransmission(carDTO.getTransmission());
        if(carDTO.getEngine() != null)
            _car.setEngine(carDTO.getEngine());
        if(carDTO.getVin() != null)
            _car.setVin(carDTO.getVin());
        if(carDTO.getMileage() != null)
            _car.setMileage(carDTO.getMileage());
        if(carDTO.getPrice() != null)
            _car.setPrice(carDTO.getPrice());

        return _car;
    }
    @DeleteMapping("/cars/delete/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable int id) {
        try {
            saleDataService.deleteCarById(id);
            return new ResponseEntity<>("Car was deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete Car.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}