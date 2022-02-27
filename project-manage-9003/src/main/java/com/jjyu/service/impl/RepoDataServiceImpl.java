package com.jjyu.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jjyu.entity.PRTask;
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

import java.util.ArrayList;
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
    public void addNewRepo(String repoName, String ownerName, Integer maxPRNum, String userName) {
        ServiceInstance serviceInstance = loadBalancerClient.choose(serviceId);
        log.info("============serviceInstance:  " + serviceInstance.toString());

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("repo_name", repoName);
        queryWrapper.eq("team_name", ownerName);


        RepoBaseEntity repoBaseEntity = repoBaseService.getOne(queryWrapper);
        if (ObjectUtils.isEmpty(repoBaseEntity)) {
            //拼接URL
            String path = String.format(serviceurl + "/dataCollection/allData/synAllData?maxPRNum=" + maxPRNum
                    + "&ownerName=" + ownerName
                    + "&repoName=" + repoName);
            log.info("============path:  " + path);
            Map<String, Object> templateForObject = restTemplate.getForObject(path, Map.class);
            Map<String, Object> dataMap = (Map<String, Object>) templateForObject.get("data");

            log.info("============templateForObject:  " + templateForObject);
        }

        PRTask prTask = new PRTask();
        prTask.setJobUser(userName);
        prTask.setJobGroup(ownerName);
        prTask.setCreateOrganize(ownerName);
        prTask.setTeamName(ownerName);
        prTask.setType("repo");
        prTask.setRepoName(repoName);
        prTask.setJobClassName("com.jjyu.job.ProjectDataCollectionJob");
        prTask.setCronExpression("0 0 0/1 * * ? ");
        //拼接URL
        String path = String.format(serviceurl + "/prTask/save");
        log.info("============path:  " + path);
        Map<String, Object> templateForObject = restTemplate.postForObject(path, prTask, Map.class);
        log.info("============templateForObject" + templateForObject);
        log.info("=========================已经设定好数据同步定时任务");
        List<String> algNameList = new ArrayList<>();
        algNameList.add("MART");
        algNameList.add("RankNet");
        algNameList.add("RankBoost");
        algNameList.add("AdaRank");
        algNameList.add("Coordinate_Ascent");
        algNameList.add("LambdaMART");
        algNameList.add("ListNet");
        algNameList.add("Random_Forests");
        for (String alg_name : algNameList) {
            log.info("=========================开始初始化" + alg_name + "模型训练定时任务");
            prTask = new PRTask();
            prTask.setJobUser(userName);
            prTask.setJobGroup(ownerName);
            prTask.setCreateOrganize(ownerName);
            prTask.setTeamName(ownerName);
            prTask.setType("alg" + alg_name);
            prTask.setRepoName(repoName);
            prTask.setJobClassName("com.jjyu.job.ModelTrainJob");
            prTask.setCronExpression("0 0 0/1 * * ? ");
            prTask.setAlgName(alg_name);
            prTask.setAlgParam("alg_paramTestTestNOne");
            //拼接URL
            path = String.format(serviceurl + "/prTask/save");
            log.info("============path:  " + path);
            templateForObject = restTemplate.postForObject(path, prTask, Map.class);
            log.info("============templateForObject" + templateForObject);
            log.info("=========================已经设定好" + alg_name + "模型训练定时任务");
        }

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
