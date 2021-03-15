package com.joey.boot.system.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Joey
 */
@Data
@AllArgsConstructor
public class RoleVO {
    private String name;
    private String desc;
    private Integer code;
}
