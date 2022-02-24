package com.jjyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private RepoBaseEntity repoBaseEntity;
    /**
     * 代码仓Id
     */
    private Integer repoId;
    /**
     * 数据日期
     */
    private Date dateDay;
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
    private Integer openPrNum;
}
