package com.joey.boot.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joey.boot.system.entity.dao.UserDO;
import com.joey.boot.system.entity.enums.RoleEnum;
import com.joey.boot.system.entity.request.LoginRequest;
import com.joey.boot.system.entity.request.SignupRequest;
import com.joey.boot.system.service.LoginService;
import com.joey.boot.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Joey
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("LoginRequest:" + loginRequest.toString());
        String token = loginService.login(loginRequest);
        Map<String, String> result = new HashMap<>(8);
        result.put("token", token);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest) {
        UserDO query = new UserDO();
        query.setUsername(signupRequest.getUsername());
        UserDO one = userService.getOne(new QueryWrapper<>(query));
        if (one != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("用户名已存在!");
        }
        UserDO userDO = new UserDO();
        userDO.setUsername(signupRequest.getUsername());
        userDO.setNickname(signupRequest.getUsername());
        userDO.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userDO.setDeleted(false);
        userDO.setRole(RoleEnum.USER);
        userService.save(userDO);
        return ResponseEntity.ok("注册成功!");
    }
}
