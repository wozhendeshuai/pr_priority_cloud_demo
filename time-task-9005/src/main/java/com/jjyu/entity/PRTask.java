package com.jjyu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Data
@TableName("pr_task")
public class PRTask implements Serializable {
    private static final long serialVersionUID = -4352868070794165001L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 类型
     * 两种，一种model，一种alg
     */
    @TableField("type")
    private String type;

    /**
     * 任务名称
     */
    @TableField("job_name")
    private String jobName;

    /**
     * 任务分组
     */
    @TableField("job_group")
    private String jobGroup;
    /**
     * 任务涉及的代码仓
     */
    @TableField("repo_name")
    private String repoName;
    /**
     * 任务涉及的团队名称
     */
    @TableField("team_name")
    private String teamName;
    /**
     * 任务涉及的算法名称
     */
    @TableField("alg_name")
    private String algName;
    /**
     * 任务涉及的算法参数
     */
    @TableField("alg_param")
    private String algParam;

    /**
     * 任务描述
     */
    @TableField("description")
    private String description;
    /**
     * 任务所属用户
     */
    @TableField("job_user")
    private String jobUser;

    /**
     * 执行类
     */
    @TableField("job_class_name")
    private String jobClassName;
    /**
     * 执行表达式
     */
    @TableField("cron_expression")
    private String cronExpression;
    /**
     * 执行时间
     */
    @TableField("trigger_time")
    private String triggerTime;
    /**
     * 执行状态
     */
    @TableField("trigger_state")
    private String triggerState;
    /**
     * 排序
     */
    @TableField("order_by")
    private String orderBy;
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private String createTime;
    /**
     * 创建人
     */
    @TableField("create_user")
    private String createUser;
    /**
     * 创建组织
     */
    @TableField("create_organize")
    private String createOrganize;

    /**
     * 修改人
     */
    @TableField("update_user")
    private String updateUser;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private String updateTime;


    @TableField(exist = false)
    private List<String> authOrganizeIds;

    @TableField(exist = false)
    private String authUser;

    @TableField(exist = false)
    private String oldJobName;

    @TableField(exist = false)
    private String oldJobGroup;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PRTask prTask = (PRTask) o;
        return Objects.equals(id, prTask.id) && Objects.equals(type, prTask.type) && Objects.equals(jobName, prTask.jobName) && Objects.equals(jobGroup, prTask.jobGroup) && Objects.equals(repoName, prTask.repoName) && Objects.equals(teamName, prTask.teamName) && Objects.equals(algName, prTask.algName) && Objects.equals(algParam, prTask.algParam) && Objects.equals(description, prTask.description) && Objects.equals(jobUser, prTask.jobUser) && Objects.equals(jobClassName, prTask.jobClassName) && Objects.equals(cronExpression, prTask.cronExpression) && Objects.equals(triggerTime, prTask.triggerTime) && Objects.equals(triggerState, prTask.triggerState) && Objects.equals(orderBy, prTask.orderBy) && Objects.equals(remark, prTask.remark) && Objects.equals(createTime, prTask.createTime) && Objects.equals(createUser, prTask.createUser) && Objects.equals(createOrganize, prTask.createOrganize) && Objects.equals(updateUser, prTask.updateUser) && Objects.equals(updateTime, prTask.updateTime) && Objects.equals(authOrganizeIds, prTask.authOrganizeIds) && Objects.equals(authUser, prTask.authUser) && Objects.equals(oldJobName, prTask.oldJobName) && Objects.equals(oldJobGroup, prTask.oldJobGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, jobName, jobGroup, repoName, teamName, algName, algParam, description, jobUser, jobClassName, cronExpression, triggerTime, triggerState, orderBy, remark, createTime, createUser, createOrganize, updateUser, updateTime, authOrganizeIds, authUser, oldJobName, oldJobGroup);
    }
}
