package com.jjyu.controller;


import com.jjyu.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("prManage/test")
public class TestController {
    @Value("${server.port}")
    private int port;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TestService testService;

    //@RequestParam("prId") String prId,@RequestParam("fileId") String fileId
    @GetMapping("/testport")
    public Map<String, Object> findPRselfAndFile() {
//        ServiceInstance serviceInstance = loadBalancerClient.choose("PRFileApplication");
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", true);
//        map.put("msg", "当前调用的是PR服务，查询PR的id：" + prId + " 查询的PRfile为：" + fileId);
//        String path=String.format(serviceInstance+"/prfile/find?id=%s",fileId);
//        map.put("fileService", "调用prFile服务的结果是：" + restTemplate.getForObject(path, String.class));
        map.put("port", "当前的端口是：" + port);


        return map;
    }

    @GetMapping("/testprojectport")
    public Map<String, Object> testprojectport() {
//        ServiceInstance serviceInstance = loadBalancerClient.choose("PRFileApplication");
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", true);
//        map.put("msg", "当前调用的是PR服务，查询PR的id：" + prId + " 查询的PRfile为：" + fileId);
//        String path=String.format(serviceInstance+"/prfile/find?id=%s",fileId);
//        map.put("fileService", "调用prFile服务的结果是：" + restTemplate.getForObject(path, String.class));
        map.put("port", "当前的端口是：" + port);
        map.put("调用project的结果", testService.getProjectPort());


        return map;
    }
}
