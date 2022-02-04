package com.jjyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("repo_base_db")
public class RepoBaseEntity implements Serializable {
    private static final long serialVersionUID = -4352868070794165001L;
    /**
     * 代码仓Id
     */
    @TableField("repo_id")
    private Integer repoId;
    /**
     * 代码仓全名
     */
    @TableField("full_name")
    private String fullName;
    /**
     * 代码仓名称
     */
    @TableField("repo_name")
    private String repoName;
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
     * 代码仓创建时间
     */
    @TableField("repo_created_at")
    private Date repoCreatedAt;
    /**
     * 代码仓主要使用的语言
     */
    @TableField("use_language")
    private String useLanguage;
    /**
     * 代码仓使用过的语言
     */
    @TableField("languages")
    private String languages;
    /**
     * 代码仓所在领域
     */
    @TableField("project_domain")
    private String projectDomain;

    /**
     * 代码仓团队信息
     */
    @TableField(exist = false)
    private TeamEntity teamEntity;
}
