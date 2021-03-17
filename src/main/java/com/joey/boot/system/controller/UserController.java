package com.joey.boot.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joey.boot.system.entity.dao.UserDO;
import com.joey.boot.system.entity.request.UserRequest;
import com.joey.boot.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * @param params
     * @return
     * @PreAuthorize("hasRole('ADMIN')")
     */
    @GetMapping("/page")
    public ResponseEntity getUserPage(UserRequest params) {
        log.info("UserPageRequest : {}", params.toString());
        UserDO userDO =new UserDO();
        BeanUtils.copyProperties(params,userDO);
        LambdaQueryWrapper<UserDO> queryWrapper = new QueryWrapper<>(userDO).lambda().orderByDesc(UserDO::getUpdateTime);
        IPage<UserDO> userPage = userService.getPage(new Page<>(params.getCurrent(), params.getSize()), queryWrapper);
        return ResponseEntity.ok(userPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserPage(@PathVariable int id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    /**
     * @param userRequest
     * @return
     * @PreAuthorize("hasAuthority('ADMIN')")
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
        if (save){
            return ResponseEntity.ok("添加成功!");
        }else {
            return ResponseEntity.status(500).body("添加失败!");
        }
    }

    @PutMapping("")
    public ResponseEntity updateUser(@Valid @RequestBody UserRequest userRequest) {
        log.info("UpdateUser -- UserRequest : {}", userRequest.toString());
        UserDO userOne = userService.getOne(new QueryWrapper<UserDO>().lambda().eq(UserDO::getUsername, userRequest.getUsername()));
        if (userOne != null && !userOne.getId().equals(userRequest.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userRequest.getUsername() + "用户已存在!");
        }
        UserDO user = userService.getById(userRequest.getId());
        BeanUtils.copyProperties(userRequest, user);
        boolean save = userService.updateById(user);
        if (save){
            return ResponseEntity.ok("编辑成功!");
        }else {
            return ResponseEntity.status(500).body("编辑失败!");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long  id) {
        log.info("DeleteUser -- userid : {}", id);
        Long ADMIN_ID = 1L;
        if (ADMIN_ID.equals(id)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("不允许删除管理员!");
        }
        boolean bool = userService.removeById(id);
        if (bool){
            return ResponseEntity.ok("删除成功!");
        }else {
            return ResponseEntity.status(500).body("删除失败!");
        }
    }
}
