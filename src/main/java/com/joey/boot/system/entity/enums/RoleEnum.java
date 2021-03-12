package com.joey.boot.system.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Joey
 * 角色
 */
public enum RoleEnum {
    /**
     * 角色
     */
    ADMIN(0, "管理员"),
    USER(1, "用户");

    /**
     * 标记数据库存的值是code
     */
    @EnumValue
    private final int code;
    @JsonValue
    private final String name;

    RoleEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
