package com.CarSales.CarSalesApi.Repository;

import org.springframework.data.repository.CrudRepository;
import com.CarSales.CarSalesApi.JPAModel.Sale;

public interface SaleRepository extends CrudRepository<Sale, Integer> {
}