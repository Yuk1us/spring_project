package org.artyomnikitin;

import org.artyomnikitin.spring.dao.UserRepositoryImpl;
import org.artyomnikitin.spring.dto.User;

import org.artyomnikitin.spring.dto.UserBody;
import org.artyomnikitin.spring.service.UserService;


import org.junit.*;


import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;


import static org.junit.Assert.*;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AppTest{
    User user = new User(1, "FakeUser", "FakePassword");
    UserBody userBody = new UserBody("FakeUser", "FakePassword", "NewFakePassword");

    @Mock
    private UserRepositoryImpl userRepository;
    @InjectMocks
    UserService userService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.update(userBody)).thenReturn(true);
        when(userRepository.exists(user)).thenReturn(true);
    }

    /**(1) Test LogIn Logic*/
    @Test
    public void testLogIn(){
        userService.logInto(user);
    }
    /**(2) Test SighUp Logic*/
    @Test
    public void testSighUp(){
        assertTrue(userRepository.exists(user));
    }
    /**(3) Test Read-All Logic*/
    @Test
    public void testGetAll(){
        List<User> users = (List<User>) userRepository.findAll();
        assertFalse(users.isEmpty());
    }
    /**(4) Test Update-Password Logic*/
    @Test
    public void testUpdate(){
        userService.updatePassword(new UserBody("FakeUser", "FakePassword", "newFakePassword"));
    }
    @Test
    public void unrelated(){

    }

}
