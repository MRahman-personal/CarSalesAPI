package com.CarSales.CarSalesApi.Controller;

import com.CarSales.CarSalesApi.Authentication.AuthenticationUtilities;
import com.CarSales.CarSalesApi.JPAModel.User;
import com.CarSales.CarSalesApi.Service.UserDataService;
import com.CarSales.CarSalesApi.DTOModel.LoginDTO;
import com.CarSales.CarSalesApi.DTOModel.UserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    UserDataService userDataService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO) {
        try {
            userDataService.saveUser(new User(userDTO.getUserName(), userDTO.getPassword(), userDTO.getEmailAddress(), userDTO.getRole()));
            return new ResponseEntity<>("User was created successfully.", HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginDTO loginDTO) {

        Optional<User> user = userDataService.findUserByName(loginDTO.getUserName());

        if (user.isPresent() && Objects.equals(user.get().getPassword(), loginDTO.getPassword())) {
            String token = AuthenticationUtilities.generateJWT("cars", String.valueOf(user.get().getUserId()));

            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable int userId, @RequestBody UserDTO userDTO) {
        Optional<User> user = userDataService.findUserById(userId);

        if (user.isPresent()) {

            User _user = user.get();

            if(userDTO.getUserName() != null)
                _user.setUserName(userDTO.getUserName());
            if(userDTO.getPassword() != null)
                _user.setPassword(userDTO.getPassword());
            if(userDTO.getEmailAddress() != null)
                _user.setEmailAddress(userDTO.getEmailAddress());
            if(userDTO.getRole() != null)
                _user.setRole(userDTO.getRole());

            userDataService.saveUser(_user);
            return new ResponseEntity<>("User was updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find user with id " + userId, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        try {
            Optional<User> userOptional = userDataService.findUserById(userId);

            if (userOptional.isEmpty()) {
                return new ResponseEntity<>("Cannot find user with id " + userId, HttpStatus.NOT_FOUND);
            }

            User user = userOptional.get();
            userDataService.deleteById(user.getUserId());
            return new ResponseEntity<>("User was deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete user.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
