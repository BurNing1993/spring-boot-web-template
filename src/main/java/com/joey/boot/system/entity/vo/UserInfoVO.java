package com.joey.boot.system.entity.vo;

import com.joey.boot.system.entity.enums.RoleEnum;
import lombok.Data;

/**
 * @author EDZ
 */
@Data
public class UserInfoVO {
    private String username;
    private String nickname;
    private RoleVO role;
}
