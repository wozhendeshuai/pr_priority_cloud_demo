package com.jjyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 此类用于展示PR详情界面中单个PR相关信息
 */
@Data
@TableName("user_operation")
public class UserOperationEntity implements Serializable {
    private static final long serialVersionUID = -4352868070794165001L;
    /**
     * 用户ID
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 用户名称
     */
    @TableField("user_name")
    private String userName;
    /**
     * 仓库id
     */
    @TableField("repo_id")
    private Integer repoId;
    /**
     * 仓库名称
     */
    @TableField("repo_name")
    private String repoName;
    /**
     * pr编号
     */
    @TableField("pr_number")
    private Integer prNumber;
    /**
     * 操作时间
     */
    @TableField("operation_time")
    private String operationTime;
    /**
     * 具体操作
     */
    @TableField("operation")
    private String operation;
}
