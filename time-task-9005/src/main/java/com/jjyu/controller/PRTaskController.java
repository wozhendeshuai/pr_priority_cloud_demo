package com.jjyu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;

import com.jjyu.entity.PRTask;
import com.jjyu.entity.QuartzEntity;
import com.jjyu.service.PRTaskService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("prTask")
public class PRTaskController {
    @Autowired
    private PRTaskService prTaskService;
    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;

    @GetMapping("all")
    public Map<String, Object> getAll() {
        List<PRTask> list = prTaskService.list();
        Map<String, Object> all = new HashMap<>();

        all.put("allList", list);
        return all;
    }

    /**
     * @title save
     * @description 保存
     */
    @PostMapping
    public String save(@Valid @RequestBody PRTask prTask) {

        try {
            // 创建用户
            String id = "userName-1234";
            prTask.setId(id);
            String time = getDateTimeStr();
            prTask.setCreate_time(time);
            prTask.setTrigger_time(time);
            //处理用户数据

            String user_id = "123jjyu";
            prTask.setCreate_user(user_id);
            String organize_id = "awesome group";
            prTask.setCreate_organize(organize_id);
            prTask.setJob_group(organize_id);
            prTask.setJob_class_name("com.jjyu.job.EasyJob");
            prTask.setTrigger_state("Y");

            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("job_name", prTask.getJob_name());
            queryWrapper.eq("job_group", prTask.getJob_group());
            PRTask one = prTaskService.getOne(queryWrapper);
            if (one != null) {
                return "已存在该标题的定时任务";
            }
            List<PRTask> list = prTaskService.list();
            for (PRTask prTaskTemp : list) {
                log.info("bustaskTemp: " + prTaskTemp.toString());
            }
            try {
                prTaskService.save(prTask);
                QuartzEntity quartz = new QuartzEntity();
                quartz.setJobName(prTask.getJob_name());
                quartz.setJobGroup(prTask.getJob_group());
                quartz.setDescription(prTask.getDescription() + "#" + prTask.getJob_user());
                quartz.setCronExpression(prTask.getCron_expression());
                quartz.setJobClassName(prTask.getJob_class_name());

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
                        .usingJobData("repo_name", prTask.getRepo_name())
                        .usingJobData("user_name", prTask.getJob_user())
                        .build();
                // 触发时间点
                CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(quartz.getCronExpression());
                Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger" + quartz.getJobName(), quartz.getJobGroup())
                        .startNow().withSchedule(cronScheduleBuilder).build();
                //交由Scheduler安排触发
                scheduler.scheduleJob(job, trigger);
                return "新增定时任务成功";
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            String message = "新增信息失败";
            log.error(message, e);
        }
        return "新增信息失败";
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
    @DeleteMapping("/{param_ids}")
    public void del(@NotBlank(message = "{required}") @PathVariable String param_ids) {

        try {
            String[] ids = param_ids.split(StringPool.COMMA);
            Collection<PRTask> list = prTaskService.listByIds(Arrays.asList(ids));
            for (PRTask prTask : list) {

                TriggerKey triggerKey = TriggerKey.triggerKey(prTask.getJob_name(), prTask.getJob_group());
                // 停止触发器
                scheduler.pauseTrigger(triggerKey);
                // 移除触发器
                scheduler.unscheduleJob(triggerKey);
                // 删除任务
                scheduler.deleteJob(JobKey.jobKey(prTask.getJob_name(), prTask.getJob_group()));
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
    @PutMapping
    public String update(@Valid @RequestBody PRTask prTask) {

        try {
            //处理用户数据
//            String user_id = authParams.split(":")[1];
//            String organize_id = authParams.split(":")[2];
            String user_id = "123jjyu";
            prTask.setCreate_user(user_id);
            String organize_id = "awesome group";

            String time = getDateTimeStr();
            prTask.setUpdate_time(time);
            prTask.setTrigger_time(time);
            prTask.setUpdate_user(user_id);
            prTask.setTrigger_state("Y");
            prTask.setJob_group(organize_id);
            prTask.setJob_class_name("com.jjyu.job.EasyJob");

            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("job_name", prTask.getJob_name());
            queryWrapper.eq("job_group", prTask.getJob_group());
            queryWrapper.ne("id", prTask.getId());
            PRTask prTaskTemp = prTaskService.getOne(queryWrapper);
            if (prTaskTemp != null) {

            } else {
                prTaskService.updateById(prTask);
                try {
                    QuartzEntity quartz = new QuartzEntity();
                    quartz.setJobName(prTask.getJob_name());
                    quartz.setJobGroup(prTask.getJob_group());
                    quartz.setDescription(prTask.getDescription() + "#" + prTask.getJob_user());
                    quartz.setCronExpression(prTask.getCron_expression());
                    quartz.setJobClassName(prTask.getJob_class_name());
                    if (prTask.getOldJobName() != null || prTask.getOldJobName().length() != 0) {
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
                            .usingJobData("repo_name", prTask.getRepo_name())
                            .usingJobData("user_name", prTask.getJob_user())
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
            String message = "修改用户失败";
            log.error(message, e);
        }
        return null;
    }


    /**
     * @title execution
     * @description 执行
     * @author qingfeng
     * @updateTime 2021/4/3 0003 20:23
     */
    @GetMapping("/execution")
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
     * @author qingfeng
     * @updateTime 2021/4/3 0003 20:23
     */
    @GetMapping("/stopOrRestore")
    public void stopOrRestore(PRTask prTask) {

        try {
            JobKey key = new JobKey(prTask.getJob_name(), prTask.getJob_group());
            if (prTask.getTrigger_state().equals("N")) {
                scheduler.pauseJob(key);
                System.out.println("停止。。。。。");
            } else if (prTask.getTrigger_state().equals("Y")) {
                scheduler.resumeJob(key);
                prTask.setTrigger_time(getDateTimeStr());
            }
            prTask.setUpdate_time(getDateTimeStr());
            prTaskService.updateById(prTask);

        } catch (SchedulerException e) {
            String message = "操作失败";

            log.error(message, e);

        }

    }


}
