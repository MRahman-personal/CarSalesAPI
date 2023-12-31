package com.CarSales.CarSalesApi.Repository;

import com.CarSales.CarSalesApi.JPAModel.User;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUserName(String userName);
}