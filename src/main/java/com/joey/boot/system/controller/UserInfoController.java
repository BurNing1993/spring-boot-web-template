package com.joey.boot.system.controller;

import com.joey.boot.system.entity.dao.UserDO;
import com.joey.boot.system.entity.vo.UserInfoVO;
import com.joey.boot.system.service.UserService;
import com.joey.boot.system.utils.TokenManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Joey
 */
@RestController
public class UserInfoController {

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    public ResponseEntity getUserInfo(HttpServletRequest request) {
        String payload = tokenManager.getPayloadFromRequest(request);
        UserDO userDO = userService.getById(payload);
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(userDO,userInfoVO);
        return ResponseEntity.ok(userInfoVO);
    }
}
