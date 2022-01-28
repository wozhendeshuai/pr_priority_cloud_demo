package com.jjyu.job;

import lombok.SneakyThrows;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

public class EasyJob implements Job {
    @Autowired
    private RestTemplate restTemplate;

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {

//        String path = String.format("http://localhost:9001/prfile/synpr?repo_name=123444repoName");
//        Map forObject = restTemplate.getForObject(path, Map.class);forObject.toString() +
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        String repoName = (String) jobDataMap.get("repo_name");
        String description = jobDetail.getDescription();
        Class<? extends Job> jobClass = jobDetail.getJobClass();
        JobKey key = jobDetail.getKey();


        System.out.println("---------------------------------------------------------------");
        System.out.println("jobDataMap==================>" + jobDataMap);
        System.out.println("repoName==================>" + repoName);
        System.out.println("description==================>" + description);
        System.out.println("jobClass==================>" + jobClass.toString());
        System.out.println("key==================>" + key.toString());
        System.out.println("EasyJob=====================>" + LocalDateTime.now());

    }
}
