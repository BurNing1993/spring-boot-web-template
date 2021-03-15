package com.joey.boot.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joey.boot.system.entity.dao.UserDO;
import com.joey.boot.system.entity.request.BasePageParams;
import com.joey.boot.system.entity.request.UserRequest;
import com.joey.boot.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Joey
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    private static final String DEFAULT_PASSWORD = "123456";
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @PreAuthorize("hasRole('ADMIN')")
     * @param params
     * @return
     */
    @GetMapping("/page")
    public ResponseEntity getUserPage(BasePageParams params) {
        IPage<UserDO> userPage = userService.getPage(new Page<>(params.getCurrent(), params.getSize()), null);
        return ResponseEntity.ok(userPage);
    }

    /**
     *  @PreAuthorize("hasAuthority('ADMIN')")
     * @param userRequest
     * @return
     */
    @PostMapping("")
    public ResponseEntity addUser(@Valid @RequestBody UserRequest userRequest) {
        log.info("UserRequest : {}", userRequest.toString());
        UserDO userOne = userService.getOne(new QueryWrapper<UserDO>().lambda().eq(UserDO::getUsername, userRequest.getUsername()));
        if (userOne != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userRequest.getUsername() + "用户已存在!");
        }
        UserDO user = new UserDO();
        BeanUtils.copyProperties(userRequest, user);
        user.setRole(user.getRole());
        user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        boolean save = userService.save(user);
        return ResponseEntity.ok(save);
    }
}
