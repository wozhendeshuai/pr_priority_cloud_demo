package com.jjyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("team_base_db")
public class TeamEntity implements Serializable {
    private static final long serialVersionUID = -4352868070794165001L;
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
     * 团队大小
     */
    @TableField("team_size")
    private Integer teamSize;

    /**
     * 团队中的人员情况
     */
    @TableField(exist = false)
    private List<UserBaseEntity> userBaseEntityList;
}
