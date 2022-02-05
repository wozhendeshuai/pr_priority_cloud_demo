package com.jjyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * 此类用于展示PR排序界面相关PR列表信息
 */
@Data
public class SortedPRDetail {

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

    private Integer commentsNumber;

    private Integer reviewCommentsNumber;

    private Integer commitNumber;

    private Integer changedFileNum;

    private Integer totalAddLine;

    private Integer totalDeleteLine;
}
