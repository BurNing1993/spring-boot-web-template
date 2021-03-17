package com.joey.boot.system.entity.request;

import com.joey.boot.system.entity.enums.RoleEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Joey
 */
@Data
public class UserRequest extends BasePageParams{
    private Long id;
    @NotBlank(message = "账号不能为空")
    @Size(min = 4, max = 16, message = "账号为4~16位")
    private String username;
    @NotBlank(message = "昵称不能为空")
    @Size(min = 4, max = 16, message = "昵称为4~16位")
    private String nickname;
    @NotNull(message = "角色不能为空")
    private RoleEnum role;
}
