package com.jjyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("pr_file")
public class PRFileEntity {

    @TableField("pr_number")
    private Integer prNumber;

    @TableField("repo_name")
    private String repoName;

    @TableField("changed_file_name")
    private String changedFileName;

    @TableField("sha")
    private String sha;

    @TableField("changed_file_status")
    private String changedFileStatus;

    @TableField("lines_added_num")
    private Integer linesAddedNum;

    @TableField("lines_deleted_num")
    private Integer linesDeletedNum;

    @TableField("lines_changed_num")
    private Integer linesChangedNum;

    @TableField("contain_patch")
    private Integer containPatch;

    @TableField("patch_content")
    private Date patchContent;


}
