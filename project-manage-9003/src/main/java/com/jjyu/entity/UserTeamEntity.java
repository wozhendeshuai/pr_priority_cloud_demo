package com.jjyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("user_team_db")
public class UserTeamEntity implements Serializable {
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
     * 团队ID
     */
    @TableField("team_id")
    private Integer teamId;
    /**
     * 团队名称
     */
    @TableField("team_name")
    private String teamName;
    /**
     * 用户在团队当中的权限，以#隔开
     */
    @TableField("user_role_in_team")
    private String userRoleInTeam;
}
