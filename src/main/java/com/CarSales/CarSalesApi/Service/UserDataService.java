package com.CarSales.CarSalesApi.Service;

import com.CarSales.CarSalesApi.Repository.UserRepository;
import com.CarSales.CarSalesApi.JPAModel.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDataService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers(){
        return (List<User>) userRepository.findAll();
    }

    public Optional<User> findUserById(int userId){
        return userRepository.findById(userId);
    }
    public Optional<User> findUserByName(String username){
        return userRepository.findByUserName(username);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteById(int id){
        userRepository.deleteById(id);
    }

    public void deleteAllUsers(){
        userRepository.deleteAll();
    }
}
