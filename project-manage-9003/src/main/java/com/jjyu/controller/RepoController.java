package com.jjyu.controller;


import com.jjyu.entity.RepoBaseEntity;
import com.jjyu.entity.TeamEntity;
import com.jjyu.service.RepoService;
import com.jjyu.utils.ResultForFront;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("repo")
@Slf4j
public class RepoController {
    @Value("${server.port}")
    private int port;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RepoService repoService;

    //@RequestParam("prId") String prId,@RequestParam("fileId") String fileId
    @GetMapping("/listrepo")
    public Map<String, Object> findPRselfAndFile() {
        log.info("=============listTeam");


        List<RepoBaseEntity> repoBaseEntityList = repoService.getAllRepo();
        for (RepoBaseEntity temp : repoBaseEntityList) {
            log.info(temp.toString());
        }


        return ResultForFront.ok().put("status", true).put("port", "当前的端口是：" + port).put("repoBaseEntityList", repoBaseEntityList);
    }
}
