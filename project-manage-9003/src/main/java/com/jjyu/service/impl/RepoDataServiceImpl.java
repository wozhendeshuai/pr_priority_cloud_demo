package com.jjyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jjyu.entity.RepoBaseEntity;
import com.jjyu.service.RepoBaseService;
import com.jjyu.service.RepoDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Slf4j
public class RepoDataServiceImpl implements RepoDataService {
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RepoBaseService repoBaseService;

    private String serviceurl = "http://pr-gateway-9001";

    private String serviceId = "pr-gateway-9001";

    @Override
    public RepoBaseEntity getRepoBaseDataFromDataCollection(String repoName) {

        ServiceInstance serviceInstance = loadBalancerClient.choose(serviceId);
        log.info("============serviceInstance:  " + serviceInstance.toString());

        //拼接URL
        String path = String.format(serviceurl + "/dataCollection/data/testbaseEntity?repoName=" + repoName);//
        log.info("============path:  " + path);
        Map<String, Object> templateForObject = restTemplate.getForObject(path, Map.class);
        Map<String, Object> dataMap = (Map<String, Object>) templateForObject.get("data");
        RepoBaseEntity repoBaseEntity = new RepoBaseEntity();
        repoBaseEntity.setRepoId((Integer) dataMap.get("repoId"));
        repoBaseEntity.setFullName((String) dataMap.get("fullName"));
        repoBaseEntity.setRepoName((String) dataMap.get("repoName"));
        log.info("============templateForObject:  " + templateForObject);

        return repoBaseEntity;
    }

    @Override
    @Async("taskExecutor")
    public void synAllData(String repoName, Integer maxPRNum) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("repo_name", repoName);
        RepoBaseEntity repoBaseEntity = repoBaseService.getOne(queryWrapper);

        ServiceInstance serviceInstance = loadBalancerClient.choose(serviceId);
        log.info("============serviceInstance:  " + serviceInstance.toString());
        //拼接URL
        String path = String.format(serviceurl + "/dataCollection/allData/synAllData?maxPRNum=" + repoName
                + "&ownerName=" + repoBaseEntity.getTeamName()
                + "&repoName=" + repoName);
        log.info("============path:  " + path);
        Map<String, Object> templateForObject = restTemplate.getForObject(path, Map.class);
        Map<String, Object> dataMap = (Map<String, Object>) templateForObject.get("data");

        log.info("============templateForObject:  " + templateForObject);

    }

}
