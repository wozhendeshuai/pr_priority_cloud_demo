package com.jjyu.service.impl;

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
public class TestServiceImpl implements TestService {
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private RestTemplate restTemplate;

    private String serviceurl = "http://pr-gateway-9001";

    private String serviceId = "pr-gateway-9001";


    @Override
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


//        String path2 = String.format(serviceInstance.getUri() + "/project/testport");
//        log.info("============path2:  " + path2);
//        Map templateForObject2 = restTemplate.getForObject(path2, Map.class);
//        map.put("map-result2", templateForObject2);
//        log.info("============templateForObject2:  " + templateForObject2);
//        String stringTemplateForObject2 = restTemplate.getForObject(path2, String.class);
//        map.put("string-result2", stringTemplateForObject2);
//        log.info("============stringTemplateForObject2:  " + stringTemplateForObject2);
        return map;
    }
}
