package com.joey.boot.system.entity.request;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author EDZ
 */
@Data
public class LoginRequest {

    @NotBlank(message = "账号不能为空")
    @Size(min = 4, max = 16, message = "账号为6~16位")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 16, message = "密码为6~16位")
    private String password;
}
