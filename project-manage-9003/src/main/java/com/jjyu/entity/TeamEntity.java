package com.jjyu.entity;

import lombok.Data;

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
}
