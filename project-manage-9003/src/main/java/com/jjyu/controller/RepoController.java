package com.jjyu.controller;


import cn.hutool.core.map.MapUtil;
import com.jjyu.entity.RepoBaseEntity;
import com.jjyu.entity.TeamEntity;
import com.jjyu.service.RepoService;
import com.jjyu.service.UserService;
import com.jjyu.utils.ResultForFront;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "project/repo", tags = {"项目controller"})
@Slf4j
@RestController
@RequestMapping("project/repo")
public class RepoController {
    @Value("${server.port}")
    private int port;
    @Resource
    private UserService userService;

    @Autowired
    private RepoService repoService;

    //@RequestParam("prId") String prId,@RequestParam("fileId") String fileId
    @GetMapping("/listrepo")
    public ResultForFront findPRselfAndFile() {
        log.info("=============listTeam");


        List<RepoBaseEntity> repoBaseEntityList = repoService.getAllRepo();
        for (RepoBaseEntity temp : repoBaseEntityList) {
            log.info(temp.toString());
        }


        return ResultForFront.succ(MapUtil.builder()
                .put("port", port)
                .build());

    }


}
