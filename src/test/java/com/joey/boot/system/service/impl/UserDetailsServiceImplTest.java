package com.joey.boot.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class UserDetailsServiceImplTest {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsername() {
        UserDetails userDetail = userDetailsService.loadUserByUsername("admin");
        log.info(userDetail.toString());
        Assertions.assertTrue(userDetail!=null);
    }
}