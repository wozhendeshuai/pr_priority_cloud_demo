package com.jjyu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
public class SortResult implements Serializable {
    private static final long serialVersionUID = -4352868070794165001L;

    /**
     * 排序日期
     */
    private String sortDay;

    /**
     * 项目名称
     */
    private String repoName;

    /**
     * 算法名称
     */
    private String algName;

    /**
     * PR编号
     */
    private Integer prNumber;
    /**
     * PR排序
     */
    private Integer prOrder;


}
