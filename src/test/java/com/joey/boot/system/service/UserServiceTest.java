package com.joey.boot.system.service;

import com.joey.boot.system.entity.dao.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void getUserTest(){
        UserDO userDO = userService.getById(1L);
        log.info(userDO.toString());
        Assertions.assertTrue(userDO!=null);
    }
}