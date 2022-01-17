package com.jjyu.entity;

import lombok.Data;

import java.util.List;

@Data
public class TeamEntity {
    /**
     * 团队ID
     */
    private Integer teamId;
    /**
     * 团队名称
     */
    private String teamName;
    /**
     * 团队大小
     */
    private Integer teamSize;

    /**
     * 团队中的人员情况
     */
    private List<UserBaseEntity> userBaseEntityList;
}
