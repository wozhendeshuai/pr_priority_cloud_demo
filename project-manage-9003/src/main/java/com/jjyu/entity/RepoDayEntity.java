package com.jjyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("repo_day_db")
public class RepoDayEntity {
    /**
     * 代码仓信息
     */
    @TableField(exist = false)
    private RepoBaseEntity repoBaseEntity;
    /**
     * 代码仓Id
     */
    @TableField("repo_id")
    private Integer repoId;
    /**
     * 代码仓更新时间
     */
    @TableField("repo_updated_at")
    private Date repoUpdatedAt;
    /**
     * 代码仓push时间
     */
    @TableField("repo_pushed_at")
    private Date repoPushedAt;
    /**
     * 代码仓关注的人数
     */
    @TableField("watchers_num")
    private Integer watchersNum;
    /**
     * 代码仓stars的数量
     */
    @TableField("stars_num")
    private Integer starsNum;
    /**
     * 代码仓fork的数量
     */
    @TableField("forks_num")
    private Integer forksNum;
    /**
     * 代码仓贡献者数量
     */
    @TableField("contributor_num")
    private Integer contributorNum;
}
