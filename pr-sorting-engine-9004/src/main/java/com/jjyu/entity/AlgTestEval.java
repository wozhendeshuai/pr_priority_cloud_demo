package com.jjyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("alg_test_eval")
public class AlgTestEval implements Serializable {
    private static final long serialVersionUID = -4352868070794165001L;

    /**
     * 训练算法的日期
     */
    @TableId(value = "train_day")
    private Date trainDay;

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
     * 测试的日期
     */
    @TableField("test_day")
    private Date testDay;
    /**
     * ndcg
     */
    @TableField("ndcg")
    private double ndcg;
    /**
     * mrr
     */
    @TableField("mrr")
    private double mrr;
    /**
     * ndcg
     */
    @TableField("kendall_tau_distance")
    private double kendall_tau_distance;


}
