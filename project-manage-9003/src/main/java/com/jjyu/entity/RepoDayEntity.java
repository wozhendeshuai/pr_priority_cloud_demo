package com.jjyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("repo_day_db")
public class RepoDayEntity implements Serializable {
    private static final long serialVersionUID = -4352868070794165001L;
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
     * 数据日期
     */
    @TableField("date_day")
    @JsonFormat(pattern = "yyyy年MM月dd日", timezone = "Asia/Shanghai")
    private Date dateDay;
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
    /**
     * 代码仓开放状态PR数量
     */
    @TableField("open_pr_num")
    private Integer openPrNum;
}
