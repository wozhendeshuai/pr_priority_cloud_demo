package com.jjyu.entity;

import lombok.Data;

@Data
public class UserBaseEntity {
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * github_token
     */
    private String githubToken;
    /**
     * 用户在团队当中的角色
     */
    private String userRoleInTeam;
}
