package com.jjyu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private String job_name;

    /**
     * 任务分组
     */
    private String job_group;
    /**
     * 任务涉及的代码仓
     */
    private String repo_name;
    /**
     * 任务描述
     */
    private String description;
    /**
     * 任务所属用户
     */
    private String job_user;

    /**
     * 执行类
     */
    private String job_class_name;
    /**
     * 执行表达式
     */
    private String cron_expression;
    /**
     * 执行时间
     */
    private String trigger_time;
    /**
     * 执行状态
     */
    private String trigger_state;
    /**
     * 排序
     */
    private String order_by;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private String create_time;
    /**
     * 创建人
     */
    private String create_user;
    /**
     * 创建组织
     */
    private String create_organize;

    /**
     * 修改人
     */
    private String update_user;
    /**
     * 修改时间
     */
    private String update_time;

    private List<String> auth_organize_ids;

    private String auth_user;

    private String oldJobName;

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
