package com.jjyu.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jjyu.entity.RepoBaseEntity;
import com.jjyu.service.RepoBaseService;
import com.jjyu.service.RepoDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
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

        //此处自动获取最大PR编号
        maxPRNum = findMaxPRNumber(repoBaseEntity.getTeamName(), repoName);
        log.info("============maxPRNum" + maxPRNum);

        ServiceInstance serviceInstance = loadBalancerClient.choose(serviceId);
        log.info("============serviceInstance:  " + serviceInstance.toString());
        //拼接URL
        String path = String.format(serviceurl + "/dataCollection/allData/synAllData?maxPRNum=" + maxPRNum
                + "&ownerName=" + repoBaseEntity.getTeamName()
                + "&repoName=" + repoName);
        log.info("============path:  " + path);
        Map<String, Object> templateForObject = restTemplate.getForObject(path, Map.class);
        Map<String, Object> dataMap = (Map<String, Object>) templateForObject.get("data");

        log.info("============templateForObject:  " + templateForObject);

    }

    @Override
    @Async("taskExecutor")
    public void addNewRepo(String repoName, String ownerName, Integer maxPRNum) {
        ServiceInstance serviceInstance = loadBalancerClient.choose(serviceId);
        log.info("============serviceInstance:  " + serviceInstance.toString());
        //拼接URL
        String path = String.format(serviceurl + "/dataCollection/allData/synAllData?maxPRNum=" + maxPRNum
                + "&ownerName=" + ownerName
                + "&repoName=" + repoName);
        log.info("============path:  " + path);
        Map<String, Object> templateForObject = restTemplate.getForObject(path, Map.class);
        Map<String, Object> dataMap = (Map<String, Object>) templateForObject.get("data");

        log.info("============templateForObject:  " + templateForObject);
//       todo: 设置一个定时任务

    }

    private int findMaxPRNumber(String ownerName, String repoName) {
        RestTemplate tempRestTemplate = new RestTemplate();
        String pullListPath = String.format("https://api.github.com/repos/" + ownerName + "/" + repoName + "/pulls");//
        log.info("============pullListPath:  " + pullListPath);
        List templateForObject = tempRestTemplate.getForObject(pullListPath, List.class);
        if (ObjectUtils.isEmpty(templateForObject)) {
            return 0;
        }
        System.out.println(templateForObject);

        JSONObject jsonObject = new JSONObject(templateForObject.get(0));

        System.out.println(jsonObject);

        return jsonObject.get("number", Integer.class) + 2;
    }
}
