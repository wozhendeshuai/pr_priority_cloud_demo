package com.jjyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("pr_repo")
public class PRRepoEntity {

    @TableField("repo_id")
    private Integer repoId;

    @TableField("full_name")
    private String fullName;

    @TableField("repo_name")
    private String repoName;

    @TableField("owner_name")
    private String ownerName;

    @TableField("owner_type")
    private String ownerType;

    @TableField("team_size")
    private Integer teamSize;

    @TableField("project_created_at")
    private Date projectCreatedAt;

    @TableField("project_updated_at")
    private Date projectUpdatedAt;

    @TableField("project_pushed_at")
    private Date projectPushedAt;

    @TableField("watchers")
    private Integer watchers;

    @TableField("stars")
    private Integer stars;

    @TableField("use_language")
    private String useLanguage;

    @TableField("languages")
    private String languages;

    @TableField("project_domain")
    private String projectDomain;

    @TableField("contributor_num")
    private Integer contributorNum;

    @TableField("forks_count")
    private Integer forksCount;

}
