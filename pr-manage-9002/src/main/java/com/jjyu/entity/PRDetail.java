package com.jjyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * 此类用于展示PR详情界面中单个PR相关信息
 */
@Data
public class PRDetail {

    private Integer prNumber;

    private String prUrl;

    private String repoName;

    private Integer prUserId;

    private String prUserName;

    private String prAuthorAssociation;

    private String title;

    private String body;

    private String labels;

    private String state;

    private Date createdAt;

    private Date updatedAt;

    private Date closedAt;

    private Date mergedAt;

    private Integer merged;

    private Integer mergeable;

    private String mergeableState;

    private String mergeCommitSha;

    private String assigneesContent;

    private String requestedReviewersContent;

    private Integer commentsNumber;

    private String commentsContent;

    private Integer reviewCommentsNumber;

    private String reviewCommentsContent;


    private Integer commitNumber;

    private String commitContent;


    private Integer changedFileNum;


    private Integer totalAddLine;


    private Integer totalDeleteLine;

}
