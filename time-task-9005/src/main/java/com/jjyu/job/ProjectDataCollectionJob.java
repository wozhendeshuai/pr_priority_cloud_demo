package com.jjyu.job;

import lombok.SneakyThrows;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

public class ProjectDataCollectionJob implements Job {
    @Autowired
    private RestTemplate restTemplate;

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {


        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        String repoName = (String) jobDataMap.get("repo_name");
        String userName = (String) jobDataMap.get("user_name");
        String teamName = (String) jobDataMap.get("team_name");
        String algName = (String) jobDataMap.get("alg_name");
        String algParam = (String) jobDataMap.get("alg_param");
        String description = jobDetail.getDescription();
        String type = (String) jobDataMap.get("type");
        Class<? extends Job> jobClass = jobDetail.getJobClass();
        JobKey key = jobDetail.getKey();

        System.out.println("---------------------------开始" + repoName + "项目数据同步任务------------------------------------");
        System.out.println("---------------------------开始" + repoName + "项目数据同步任务------------------------------------");
        System.out.println("---------------------------开始" + repoName + "项目数据同步任务------------------------------------");
        System.out.println("jobDataMap==================>" + jobDataMap);
        System.out.println("repoName==================>" + repoName);
        System.out.println("userName==================>" + userName);
        System.out.println("teamName==================>" + teamName);
        System.out.println("algName==================>" + algName);
        System.out.println("algParam==================>" + algParam);
        System.out.println("type==================>" + type);
        System.out.println("description==================>" + description);
        System.out.println("jobClass==================>" + jobClass.toString());
        System.out.println("key==================>" + key.toString());
        System.out.println("EasyJob=====================>" + LocalDateTime.now());
        String path = String.format("http://localhost:9001/project/repo/reSynRepoData?repoName=" + repoName + "&userName=" + userName + "&maxPRNum=" + 0);
        Map forObject = restTemplate.getForObject(path, Map.class);
        System.out.println(forObject);
        System.out.println("---------------------------结束" + repoName + "项目数据同步任务------------------------------------");
        System.out.println("---------------------------结束" + repoName + "项目数据同步任务------------------------------------");
        System.out.println("---------------------------结束" + repoName + "项目数据同步任务------------------------------------");
    }
}
