package com.jjyu.service.impl;

import com.jjyu.entity.SortedPRDetail;
import com.jjyu.service.PRSortService;
import com.jjyu.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PRSortServiceImpl implements PRSortService {
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private RestTemplate restTemplate;

    private String sortServiceurl = "http://localhost:9004"; //"http://pr-gateway-9001";


    @Override
    public List<SortedPRDetail> listRule(String repoName, String sortRule) {
        String path = String.format(sortServiceurl + "/prSorting/rule/sortOriginalData?repoName=" + repoName + "&sortRule=" + sortRule);//
        log.info("============path:  " + path);
        Map<String, Object> templateForObject = restTemplate.getForObject(path, Map.class);

        return null;
    }

    @Override
    public List<SortedPRDetail> listAlg(String userName, String prNumber, String repoName) {
        return null;
    }

    @Override
    public List<SortedPRDetail> listAlgEval(String userName, String prNumber, String repoName) {
        return null;
    }

    @Override
    public boolean reTrainModel(String prNumber, String repoName) {
        return false;
    }

    @Override
    public boolean getSortTimeTask(String userName, String prNumber, String repoName, String commentContent) {
        return false;
    }

    @Override
    public boolean setSortTimeTask(String userName, String prNumber, String repoName, String reviewContent) {
        return false;
    }
}
