package com.jjyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("user_base_db")
public class UserBaseEntity implements Serializable {
    private static final long serialVersionUID = -4352868070794165001L;
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
