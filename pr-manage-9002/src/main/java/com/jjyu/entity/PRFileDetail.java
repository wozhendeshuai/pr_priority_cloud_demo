package com.jjyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
public class PRFileDetail {
    private Integer prNumber;

    private String repoName;

    private String changedFileName;

    private String sha;

    private String changedFileStatus;

    private Integer linesAddedNum;

    private Integer linesDeletedNum;

    private Integer linesChangedNum;

    private Integer containPatch;

    private String patchContent;

}
