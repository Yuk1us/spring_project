package org.artyomnikitin.spring.controller;


import org.artyomnikitin.spring.dto.UserBody;
import org.artyomnikitin.spring.service.UserService;
import org.artyomnikitin.spring.dto.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }


    /**Create
     * <br>
     * Регистрация пользователя в сети*/
    @RequestMapping("/signUp")
    public ResponseEntity<?> signUpUser(@RequestBody User user){
        return userService.signUpUser(user);
    }


    /**Create
     * <br>
     * Вход пользователя в систему*/
    @RequestMapping("/logInto")
    @PostMapping
    public ResponseEntity<?> logInto(@RequestBody User user){
        return userService.logInto(user);
    }


    /**Read All Users
     * <br>
     * @return LOGIN всех пользователей в системе*/
    @GetMapping("/users")
    @RequestMapping("/users")
    public ResponseEntity<List<String>>getAllUsers(){
        return userService.getAllUsers();
    }


    /**Update
     * <br>
     * Update Password*/
    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody UserBody userBody){
        return userService.updatePassword(userBody);
    }






    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody User user){
        return userService.deleteUser(user);
    }

}
