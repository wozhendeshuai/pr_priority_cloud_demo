package com.jjyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("feature_file_path")
public class FeatureFilePathEntity {
    @TableField("id")
    private Integer id;

    @TableField("file_name")
    private String fileName;

    @TableField("file_type")
    private String fileType;

    @TableField("file_to_alg_name")
    private String fileToAlgName;

    @TableField("file_path")
    private String filePath;

    @TableField("repo_name")
    private String repoName;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_user_name")
    private String createUserName;

}
