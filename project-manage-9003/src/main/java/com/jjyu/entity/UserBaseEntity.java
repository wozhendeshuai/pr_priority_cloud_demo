package com.jjyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class UserBaseEntity {
    /**
     * 用户ID
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 用户名称
     */
    @TableField("user_name")
    private String userName;
    /**
     * 用户密码
     */
    @TableField("password")
    private String password;
    /**
     * 用户邮箱
     */
    @TableField("email")
    private String email;
    /**
     * github_token
     */
    @TableField("github_token")
    private String githubToken;
    /**
     * 用户在团队当中的角色
     */
    @TableField(exist = false)
    private String userRoleInTeam;
}
