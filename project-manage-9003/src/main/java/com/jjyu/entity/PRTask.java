package com.jjyu.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 用于传递定时任务相关配置信息
 */
@Data
public class PRTask implements Serializable {
    private static final long serialVersionUID = -4352868070794165001L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 类型
     */
    private String type;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务分组
     */
    private String jobGroup;
    /**
     * 任务涉及的代码仓
     */
    private String repoName;

    private String teamName;
    /**
     * 任务涉及的算法名称
     */
    private String algName;
    /**
     * 任务涉及的算法参数
     */
    private String algParam;
    /**
     * 任务描述
     */
    private String description;
    /**
     * 任务所属用户
     */
    private String jobUser;

    /**
     * 执行类
     */
    private String jobClassName;
    /**
     * 执行表达式
     */
    private String cronExpression;
    /**
     * 执行时间
     */
    private String triggerTime;
    /**
     * 执行状态
     */
    private String triggerState;
    /**
     * 排序
     */
    private String orderBy;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 创建组织
     */
    private String createOrganize;

    /**
     * 修改人
     */
    private String updateUser;
    /**
     * 修改时间
     */
    private String updateTime;

    private List<String> authOrganizeIds;

    private String authUser;

    private String oldJobName;

    private String oldJobGroup;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PRTask)) return false;
        PRTask prTask = (PRTask) o;
        return Objects.equals(id, prTask.id) && Objects.equals(type, prTask.type) && Objects.equals(jobName, prTask.jobName) && Objects.equals(jobGroup, prTask.jobGroup) && Objects.equals(repoName, prTask.repoName) && Objects.equals(description, prTask.description) && Objects.equals(jobUser, prTask.jobUser) && Objects.equals(jobClassName, prTask.jobClassName) && Objects.equals(cronExpression, prTask.cronExpression) && Objects.equals(triggerTime, prTask.triggerTime) && Objects.equals(triggerState, prTask.triggerState) && Objects.equals(orderBy, prTask.orderBy) && Objects.equals(remark, prTask.remark) && Objects.equals(createTime, prTask.createTime) && Objects.equals(createUser, prTask.createUser) && Objects.equals(createOrganize, prTask.createOrganize) && Objects.equals(updateUser, prTask.updateUser) && Objects.equals(updateTime, prTask.updateTime) && Objects.equals(authOrganizeIds, prTask.authOrganizeIds) && Objects.equals(authUser, prTask.authUser) && Objects.equals(oldJobName, prTask.oldJobName) && Objects.equals(oldJobGroup, prTask.oldJobGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, jobName, jobGroup, repoName, description, jobUser, jobClassName, cronExpression, triggerTime, triggerState, orderBy, remark, createTime, createUser, createOrganize, updateUser, updateTime, authOrganizeIds, authUser, oldJobName, oldJobGroup);
    }
}
