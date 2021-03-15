package com.joey.boot.system.entity.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joey.boot.system.entity.enums.RoleEnum;
import lombok.Data;

/**
 * @author Joey
 */
@Data
@TableName("user")
public class UserDO extends BaseDO{
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String nickname;
    private RoleEnum role;
}
