package com.jjyu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("alg_sort_result")
public class SortResult implements Serializable {
    private static final long serialVersionUID = -4352868070794165001L;

    /**
     * 排序日期
     */
    @TableId(value = "sort_day")
    private String sortDay;

    /**
     * 项目名称
     */
    @TableField("repo_name")
    private String repoName;

    /**
     * 算法名称
     */
    @TableField("alg_name")
    private String algName;

    /**
     * PR编号
     */
    @TableField("pr_number")
    private String prNumber;
    /**
     * PR排序
     */
    @TableField("pr_order")
    private String prOrder;


}
