package com.joey.boot.system.controller;

import com.joey.boot.system.entity.request.LoginRequest;
import com.joey.boot.system.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author EDZ
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("LoginRequest:" + loginRequest.toString());
        String token = loginService.login(loginRequest);
        Map<String, String> result = new HashMap<>(8);
        result.put("token", token);
        return ResponseEntity.ok(result);
    }
}
