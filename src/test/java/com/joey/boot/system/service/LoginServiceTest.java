package com.joey.boot.system.service;

import com.joey.boot.system.entity.dao.UserDO;
import com.joey.boot.system.entity.request.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class LoginServiceTest {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    LoginService loginService;
    @Autowired
    UserService userService;
    String originalPass = "admin123";

    @Test
    void passTest() {
        String pass = passwordEncoder.encode(originalPass);
        log.info("encoded pass: " + pass);
        UserDO userDO = userService.getById(1L);
        userDO.setPassword(pass);
        boolean save = userService.saveOrUpdate(userDO);
        Assertions.assertTrue(save);
    }

    @Test
    void loginTest() {
        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword(originalPass);
        String token = loginService.login(request);
        log.info("Token:"+token);
        Assertions.assertTrue(token.length() > 0);
    }
}