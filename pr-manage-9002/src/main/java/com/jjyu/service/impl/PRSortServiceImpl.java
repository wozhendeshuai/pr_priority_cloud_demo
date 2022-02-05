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

    private String serviceurl = "http://pr-gateway-9001";

    private String serviceId = "pr-gateway-9001";


    public Map<Object, Object> getProjectPort() {
        ServiceInstance serviceInstance = loadBalancerClient.choose(serviceId);
        log.info("============serviceInstance:  " + serviceInstance.toString());
        HashMap<Object, Object> map = new HashMap<>();
        map.put("status", true);
        String path = String.format(serviceurl + "/project/testport");//
        log.info("============path:  " + path);
        Map templateForObject = restTemplate.getForObject(path, Map.class);
        map.put("map-result1", templateForObject);
        log.info("============templateForObject:  " + templateForObject);
        String stringTemplateForObject = restTemplate.getForObject(path, String.class);
        map.put("string-result1", stringTemplateForObject);
        log.info("============stringTemplateForObject:  " + stringTemplateForObject);
        return map;
    }


    @Override
    public List<SortedPRDetail> listRule(String userName, String repoName) {
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
