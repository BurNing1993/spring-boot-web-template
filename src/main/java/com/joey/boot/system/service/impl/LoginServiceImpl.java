package com.joey.boot.system.service.impl;

import com.joey.boot.common.exception.LoginFailException;
import com.joey.boot.system.entity.SecurityUser;
import com.joey.boot.system.utils.TokenManager;
import com.joey.boot.system.entity.request.LoginRequest;
import com.joey.boot.system.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author Joey
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenManager tokenManager;

    @Override
    public String login(LoginRequest loginRequest) {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );
            Authentication authenticate = authenticationManager.authenticate(
                    usernamePasswordAuthenticationToken
            );
            log.info("Authenticate:" + authenticate);
            if (authenticate.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authenticate);
                SecurityUser user = (SecurityUser) authenticate.getPrincipal();
                String token = tokenManager.generateToken(String.valueOf(user.getUser().getId()));
                return token;
            } else {
                log.error("Login Fail:" + loginRequest.toString());
                throw new LoginFailException("账号或密码错误！", loginRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Login Fail:" + loginRequest.toString());
            throw new LoginFailException("账号或密码错误！", loginRequest, e);
        }
    }
}
