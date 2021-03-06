package com.joey.boot.system.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.Getter;

/**
 * @author Joey
 * 角色
 */
@Getter
public enum RoleEnum {
    /**
     * 角色
     */
    ADMIN(0, "ADMIN","管理员"),
    USER(1, "USER","用户");

    /**
     * 标记数据库存的值是code
     */
    @EnumValue
    private final int code;

    @JsonValue
    private final String name;

    private final String desc;

    RoleEnum(int code, String name,String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }
}
