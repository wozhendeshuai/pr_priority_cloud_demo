package com.jjyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class RepoBaseEntity {
    /**
     * 代码仓Id
     */
    private Integer repoId;
    /**
     * 代码仓全名
     */
    private String fullName;
    /**
     * 代码仓名称
     */
    private String repoName;
    /**
     * 团队ID
     */
    private Integer teamId;
    /**
     * 团队名称
     */
    private String teamName;
    /**
     * 代码仓团队信息
     */
    private TeamEntity teamEntity;
    /**
     * 代码仓创建时间
     */
    private Date repoCreatedAt;
    /**
     * 代码仓主要使用的语言
     */
    private String useLanguage;
    /**
     * 代码仓使用过的语言
     */
    private String languages;
    /**
     * 代码仓所在领域
     */
    private String projectDomain;
}
