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
     */
    @TableField("type")
    private String type;

    /**
     * 任务名称
     */
    @TableField("job_name")
    private String job_name;

    /**
     * 任务分组
     */
    @TableField("job_group")
    private String job_group;
    /**
     * 任务涉及的代码仓
     */
    @TableField("repo_name")
    private String repo_name;
    /**
     * 任务描述
     */
    @TableField("description")
    private String description;
    /**
     * 任务所属用户
     */
    @TableField("job_user")
    private String job_user;

    /**
     * 执行类
     */
    @TableField("job_class_name")
    private String job_class_name;
    /**
     * 执行表达式
     */
    @TableField("cron_expression")
    private String cron_expression;
    /**
     * 执行时间
     */
    @TableField("trigger_time")
    private String trigger_time;
    /**
     * 执行状态
     */
    @TableField("trigger_state")
    private String trigger_state;
    /**
     * 排序
     */
    @TableField("order_by")
    private String order_by;
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private String create_time;
    /**
     * 创建人
     */
    @TableField("create_user")
    private String create_user;
    /**
     * 创建组织
     */
    @TableField("create_organize")
    private String create_organize;

    /**
     * 修改人
     */
    @TableField("update_user")
    private String update_user;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private String update_time;


    @TableField(exist = false)
    private List<String> auth_organize_ids;

    @TableField(exist = false)
    private String auth_user;

    @TableField(exist = false)
    private String oldJobName;

    @TableField(exist = false)
    private String oldJobGroup;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PRTask)) return false;
        PRTask prTask = (PRTask) o;
        return Objects.equals(id, prTask.id) && Objects.equals(type, prTask.type) && Objects.equals(job_name, prTask.job_name) && Objects.equals(job_group, prTask.job_group) && Objects.equals(repo_name, prTask.repo_name) && Objects.equals(description, prTask.description) && Objects.equals(job_user, prTask.job_user) && Objects.equals(job_class_name, prTask.job_class_name) && Objects.equals(cron_expression, prTask.cron_expression) && Objects.equals(trigger_time, prTask.trigger_time) && Objects.equals(trigger_state, prTask.trigger_state) && Objects.equals(order_by, prTask.order_by) && Objects.equals(remark, prTask.remark) && Objects.equals(create_time, prTask.create_time) && Objects.equals(create_user, prTask.create_user) && Objects.equals(create_organize, prTask.create_organize) && Objects.equals(update_user, prTask.update_user) && Objects.equals(update_time, prTask.update_time) && Objects.equals(auth_organize_ids, prTask.auth_organize_ids) && Objects.equals(auth_user, prTask.auth_user) && Objects.equals(oldJobName, prTask.oldJobName) && Objects.equals(oldJobGroup, prTask.oldJobGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, job_name, job_group, repo_name, description, job_user, job_class_name, cron_expression, trigger_time, trigger_state, order_by, remark, create_time, create_user, create_organize, update_user, update_time, auth_organize_ids, auth_user, oldJobName, oldJobGroup);
    }
}
