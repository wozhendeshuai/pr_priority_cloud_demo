package com.jjyu.job;

import lombok.SneakyThrows;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

public class ModelTrainJob implements Job {
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
        String userName = (String) jobDataMap.get("user_name");
        String teamName = (String) jobDataMap.get("team_name");
        String algName = (String) jobDataMap.get("alg_name");
        String algParam = (String) jobDataMap.get("alg_param");
        String description = jobDetail.getDescription();
        String type = (String) jobDataMap.get("type");
        Class<? extends Job> jobClass = jobDetail.getJobClass();
        JobKey key = jobDetail.getKey();


        System.out.println("---------------------------开始" + repoName + "  " + algName + "模型训练任务------------------------------------");
        System.out.println("---------------------------开始" + repoName + "  " + algName + "模型训练任务------------------------------------");
        System.out.println("---------------------------开始" + repoName + "  " + algName + "模型训练任务------------------------------------");
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
        String path = String.format("http://localhost:9001/prSorting/alg/reTrainAlg?repoName=" + repoName
                + "&algName=" + algName
                + "&algParam=" + algParam
                + "&newFeature=" + true
                + "&userName=" + userName);
        Map forObject = restTemplate.getForObject(path, Map.class);
        System.out.println(forObject);
        System.out.println("---------------------------结束" + repoName + "  " + algName + "模型训练任务------------------------------------");
        System.out.println("---------------------------结束" + repoName + "  " + algName + "模型训练任务------------------------------------");
        System.out.println("---------------------------结束" + repoName + "  " + algName + "模型训练任务------------------------------------");

    }
}
