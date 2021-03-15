package com.joey.boot.system.controller;

import com.joey.boot.system.entity.enums.RoleEnum;
import com.joey.boot.system.entity.vo.RoleVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Joey
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @GetMapping("")
    public ResponseEntity getRoleList() {
        List<RoleVO> roleVOList = Arrays.stream(RoleEnum.values())
                .map(roleEnum -> new RoleVO(roleEnum.getName(), roleEnum.getCode()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(roleVOList);
    }
}
