package com.jjyu.job;

import lombok.SneakyThrows;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
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
        System.out.println("EasyJob==================EasyJob" + LocalDateTime.now());
    }
}
