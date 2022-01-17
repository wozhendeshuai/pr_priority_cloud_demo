package com.jjyu.entity;

import lombok.Data;

import java.util.Date;

@Data
public class RepoDayEntity {
    /**
     * 代码仓信息
     */
    private RepoBaseEntity repoBaseEntity;
    /**
     * 代码仓更新时间
     */

    private Date repoUpdatedAt;
    /**
     * 代码仓push时间
     */
    private Date repoPushedAt;
    /**
     * 代码仓关注的人数
     */
    private Integer watchersNum;
    /**
     * 代码仓stars的数量
     */
    private Integer starsNum;
    /**
     * 代码仓fork的数量
     */
    private Integer forksNum;
    /**
     * 代码仓贡献者数量
     */
    private Integer contributorNum;
}
