package com.jjyu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.jjyu.entity.PRTask;
import com.jjyu.entity.QuartzEntity;
import com.jjyu.service.PRTaskService;
import com.jjyu.utils.ResultForFront;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("prTask")
@Api(value = "prTask", tags = {"定时任务"})
public class PRTaskController {
    @Autowired
    private PRTaskService prTaskService;
    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;

    @ApiOperation(value = "getAll", notes = "getAll")
    @GetMapping("all")
    public ResultForFront getAll() {
        List<PRTask> list = prTaskService.list();
        Map<String, Object> all = new HashMap<>();
        all.put("data", list);
        return ResultForFront.succ(all);
    }

    @ApiOperation(value = "getPRTask", notes = "getPRTask")
    @GetMapping("getPRTask")
    public ResultForFront getPRTask(@RequestParam("repoName") String repoName,
                                    @RequestParam("taskType") String taskType) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", repoName + "#" + taskType);
        PRTask rePRTask = prTaskService.getOne(queryWrapper);
        if (ObjectUtils.isEmpty(rePRTask)) {
            return ResultForFront.fail("无该PR");
        } else {
            return ResultForFront.succ(rePRTask);
        }
    }

    /**
     * @title save
     * @description 保存
     */
    @ApiOperation(value = "save", notes = "save")
    @PostMapping("save")
    public ResultForFront save(@Valid @RequestBody PRTask prTask) {
        try {
            // 创建用户
            String time = getDateTimeStr();
            prTask.setCreateTime(time);
            prTask.setCreateUser(prTask.getJobUser());
            prTask.setCreateOrganize(prTask.getCreateOrganize());
            prTask.setTriggerTime(time);

            prTask.setJobName(prTask.getRepoName() + "#" + prTask.getType() + "#" + prTask.getJobGroup() + "#" + prTask.getJobUser() + "#");
            prTask.setId(prTask.getRepoName() + "#" + prTask.getType());
            //查询是否已有pr相关定时任务
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("id", prTask.getId());

            PRTask one = prTaskService.getOne(queryWrapper);
            if (one != null) {
                return ResultForFront.fail("已存在该项目所属" + prTask.getType() + "定时任务");
            } else {
                try {
                    prTaskService.save(prTask);
                    QuartzEntity quartz = new QuartzEntity();
                    quartz.setJobName(prTask.getJobName());
                    quartz.setJobGroup(prTask.getJobGroup());
                    quartz.setDescription(prTask.getDescription() + "#" + prTask.getJobUser());
                    quartz.setCronExpression(prTask.getCronExpression());
                    quartz.setJobClassName(prTask.getJobClassName());

                    //获取Scheduler实例、废弃、使用自动注入的scheduler、否则spring的service将无法注入
                    //Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
                    //如果是修改  展示旧的 任务
                    if (quartz.getOldJobGroup() != null) {
                        JobKey key = new JobKey(quartz.getOldJobName(), quartz.getOldJobGroup());
                        scheduler.deleteJob(key);
                    }
                    Class cls = Class.forName(quartz.getJobClassName());
//                cls.newInstance();
                    //构建job信息
                    JobDetail job = JobBuilder.newJob(cls)
                            .withIdentity(quartz.getJobName(), quartz.getJobGroup())
                            .withDescription(quartz.getDescription())
                            .usingJobData("repo_name", prTask.getRepoName())
                            .usingJobData("user_name", prTask.getJobUser())
                            .usingJobData("team_name", prTask.getTeamName())
                            .usingJobData("alg_name", prTask.getAlgName())
                            .usingJobData("alg_param", prTask.getAlgParam())
                            .usingJobData("type", prTask.getType())
                            .build();
                    // 触发时间点
                    CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(quartz.getCronExpression());
                    Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger" + quartz.getJobName(), quartz.getJobGroup())
                            .startNow().withSchedule(cronScheduleBuilder).build();
                    //交由Scheduler安排触发
                    scheduler.scheduleJob(job, trigger);
                    return ResultForFront.succ(200, "新增定时任务成功", null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            String message = "新增定时任务失败";
            log.error(message, e);
        }
        return ResultForFront.fail("新增定时任务失败");
    }


    public static String getDateTimeStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currTime = new Date();
        String curTime = formatter.format(currTime);
        return curTime;
    }


    /**
     * @title del
     * @description 删除
     */
    @ApiOperation(value = "delete", notes = "delete")
    @DeleteMapping("/delete/{param_ids}")
    public void delete(@NotBlank(message = "{required}") @PathVariable String param_ids) {

        try {
            String[] ids = param_ids.split(StringPool.COMMA);
            Collection<PRTask> list = prTaskService.listByIds(Arrays.asList(ids));
            for (PRTask prTask : list) {

                TriggerKey triggerKey = TriggerKey.triggerKey(prTask.getJobName(), prTask.getJobGroup());
                // 停止触发器
                scheduler.pauseTrigger(triggerKey);
                // 移除触发器
                scheduler.unscheduleJob(triggerKey);
                // 删除任务
                scheduler.deleteJob(JobKey.jobKey(prTask.getJobName(), prTask.getJobGroup()));
            }
            prTaskService.removeByIds(Arrays.asList(ids));

        } catch (Exception e) {
            String message = "删除信息失败";

            log.error(message, e);

        }

    }


    /**
     * @title update
     * @description 更新
     */
    @PutMapping("/update")
    @ApiOperation(value = "update", notes = "更新")
    public ResultForFront update(@Valid @RequestBody PRTask prTask) {

        try {

            String time = getDateTimeStr();
            prTask.setUpdateTime(time);
            prTask.setTriggerTime(time);
            prTask.setTriggerState("Y");
            prTask.setOldJobName(prTask.getJobName());
            prTask.setOldJobGroup(prTask.getJobGroup());
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("id", prTask.getId());
            PRTask prTaskTemp = prTaskService.getOne(queryWrapper);
            if (prTaskTemp == null) {
                return ResultForFront.fail("无该PRTask");
            } else {
                prTaskService.updateById(prTask);
                try {
                    QuartzEntity quartz = new QuartzEntity();
                    quartz.setJobName(prTask.getJobName());
                    quartz.setJobGroup(prTask.getJobGroup());
                    quartz.setDescription(prTask.getDescription() + "#" + prTask.getJobUser());
                    quartz.setCronExpression(prTask.getCronExpression());
                    quartz.setJobClassName(prTask.getJobClassName());
                    if (prTask.getOldJobName() != null && prTask.getOldJobName().length() != 0) {
                        quartz.setOldJobName(prTask.getOldJobName());
                        quartz.setOldJobGroup(prTask.getOldJobGroup());
                    }
                    //获取Scheduler实例、废弃、使用自动注入的scheduler、否则spring的service将无法注入
                    //Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
                    //如果是修改  展示旧的 任务
                    if (quartz.getOldJobGroup() != null) {
                        JobKey key = new JobKey(quartz.getOldJobName(), quartz.getOldJobGroup());
                        scheduler.deleteJob(key);
                    }
                    Class cls = Class.forName(quartz.getJobClassName());

                    //构建job信息
                    JobDetail job = JobBuilder.newJob(cls).withIdentity(quartz.getJobName(),
                                    quartz.getJobGroup())
                            .withDescription(quartz.getDescription())
                            .usingJobData("repo_name", prTask.getRepoName())
                            .usingJobData("user_name", prTask.getJobUser())
                            .usingJobData("team_name", prTask.getTeamName())
                            .usingJobData("alg_name", prTask.getAlgName())
                            .usingJobData("alg_param", prTask.getAlgParam())
                            .usingJobData("type", prTask.getType())
                            .build();
                    // 触发时间点
                    CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(quartz.getCronExpression());
                    Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger" + quartz.getJobName(), quartz.getJobGroup())
                            .startNow().withSchedule(cronScheduleBuilder).build();
                    //交由Scheduler安排触发
                    scheduler.scheduleJob(job, trigger);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            String message = "修改定时任务失败";
            log.error(message, e);
        }
        return ResultForFront.fail("修改定时任务成功");
    }


    /**
     * @title execution
     * @description 执行
     */
    @GetMapping("/execution")
    @ApiOperation(value = "execution", notes = "执行")
    public void execution(@RequestParam String jobName, @RequestParam String jobGroup) {

        try {
            JobKey key = new JobKey(jobName, jobGroup);
            scheduler.triggerJob(key);

        } catch (Exception e) {
            String message = "执行失败";

            log.error(message, e);

        }

    }


    /**
     * @title stopOrRestore
     * @description 停止和恢复
     */
    @GetMapping("/stopOrRestore")
    @ApiOperation(value = "stopOrRestore", notes = "停止和恢复")
    public void stopOrRestore(PRTask prTask) {

        try {
            JobKey key = new JobKey(prTask.getJobName(), prTask.getJobGroup());
            if (prTask.getTriggerState().equals("N")) {
                scheduler.pauseJob(key);
                System.out.println("停止。。。。。");
            } else if (prTask.getTriggerState().equals("Y")) {
                scheduler.resumeJob(key);
                prTask.setTriggerTime(getDateTimeStr());
            }
            prTask.setUpdateTime(getDateTimeStr());
            prTaskService.updateById(prTask);

        } catch (SchedulerException e) {
            String message = "操作失败";

            log.error(message, e);

        }

    }


}
