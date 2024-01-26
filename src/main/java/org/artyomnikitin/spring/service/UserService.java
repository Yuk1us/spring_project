package org.artyomnikitin.spring.service;



import jakarta.transaction.Transactional;
import org.artyomnikitin.spring.dao.UserRepositoryImpl;
import org.artyomnikitin.spring.dto.User;


import org.artyomnikitin.spring.dto.UserBody;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class UserService {
    private final UserRepositoryImpl userRepository;


    @Autowired
    public UserService(final UserRepositoryImpl userRepository){
        this.userRepository = userRepository;
    }


    /**Create
     * <br>
     * Регистрация пользователя в сети
     * */
    @Transactional
    public ResponseEntity<?> signUpUser(User user){
        try{
            userRepository.save(user);
            return new ResponseEntity<User>(user, HttpStatus.CREATED);
        }catch (ConstraintViolationException e){
            return new ResponseEntity<String>("User with your login already EXISTS", HttpStatus.I_AM_A_TEAPOT);
        }
    }


    /**Create
     * <br>
     * Вход пользователя в систему*/
    @Transactional
    public ResponseEntity<?> logInto(User user){
        try{
            if(userRepository.exists(user)){
                return new ResponseEntity<String>(String.format("Welcome, %s", user.getLogin()),HttpStatus.ACCEPTED);
            } else {throw new RuntimeException();}

        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return new ResponseEntity<>("Wrong login/password",HttpStatus.UNAUTHORIZED);
        }
    }


    /**Read All Users
     * <br>
     * 24.01.24 DONE*/
    @Transactional
    public ResponseEntity<List<String>> getAllUsers(){
        List<String> usersLogins = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            usersLogins.add(user.getLogin());
        });
        return new ResponseEntity<List<String>>(usersLogins, HttpStatus.OK);
    }


    /**Update
     * <br>
     * Update Password*/
    @Transactional
    public ResponseEntity<?> updatePassword(UserBody userBody){
        try {
            if(userRepository.update(userBody)){
                return new ResponseEntity<String>(String.format("Password for user %s CHANGED", userBody.getLogin()),HttpStatus.ACCEPTED);
            }else {throw new RuntimeException();}
        } catch (Exception ex){
            return new ResponseEntity<>("Wrong login/password",HttpStatus.UNAUTHORIZED);
        }
    }

    @Transactional
    public ResponseEntity<?> deleteUser(User user){
        try {
            if(userRepository.delete(user)){
                return new ResponseEntity<String>("User DELETED",HttpStatus.OK);
            } else {throw new RuntimeException();}
        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>("Wrong login/password",HttpStatus.UNAUTHORIZED);
        }
    }
}
