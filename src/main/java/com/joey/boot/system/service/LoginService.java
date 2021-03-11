package com.joey.boot.system.service;

import com.joey.boot.system.entity.request.LoginRequest;

/**
 * @author EDZ
 */
public interface LoginService {
    /**
     * 登录
     * @param loginRequest
     * @return
     */
    String login(LoginRequest loginRequest);
}
