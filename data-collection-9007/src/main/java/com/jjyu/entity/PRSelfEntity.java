package com.jjyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("pr_self")
public class PRSelfEntity {
    @TableField("pr_number")
    private Integer prNumber;
    @TableField("pr_url")
    private String prUrl;
    @TableField("repo_name")
    private String repoName;

    @TableField("pr_user_id")
    private Integer prUserId;

    @TableField("pr_user_name")
    private String prUserName;

    @TableField("pr_author_association")
    private String prAuthorAssociation;

    @TableField("title")
    private String title;

    @TableField("body")
    private String body;

    @TableField("labels")
    private String labels;

    @TableField("state")
    private String state;

    @TableField("created_at")
    private Date createdAt;

    @TableField("updated_at")
    private Date updatedAt;

    @TableField("closed_at")
    private Date closedAt;

    @TableField("merged_at")
    private Date mergedAt;

    @TableField("merged")
    private Integer merged;

    @TableField("mergeable")
    private Integer mergeable;

    @TableField("mergeable_state")
    private String mergeableState;

    @TableField("merge_commit_sha")
    private String mergeCommitSha;

    @TableField("assignees_content")
    private String assigneesContent;

    @TableField("requested_reviewers_content")
    private String requestedReviewersContent;

    @TableField("comments_number")
    private Integer commentsNumber;

    @TableField("comments_content")
    private String commentsContent;

    @TableField("review_comments_number")
    private Integer reviewCommentsNumber;

    @TableField("review_comments_content")
    private String reviewCommentsContent;

    @TableField("commit_number")
    private Integer commitNumber;

    @TableField("commit_content")
    private String commitContent;

    @TableField("changed_file_num")
    private Integer changedFileNum;

    @TableField("total_add_line")
    private Integer totalAddLine;

    @TableField("total_delete_line")
    private Integer totalDeleteLine;

}
