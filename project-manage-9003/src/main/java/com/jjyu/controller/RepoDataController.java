package com.jjyu.controller;


import cn.hutool.core.map.MapUtil;
import com.jjyu.entity.RepoBaseEntity;
import com.jjyu.service.RepoDataService;
import com.jjyu.service.RepoService;
import com.jjyu.service.UserService;
import com.jjyu.utils.ResultForFront;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("project/repoData")
@Slf4j
public class RepoDataController {
    @Resource
    private UserService userService;
    @Autowired
    private RepoDataService repoDataService;

    //@RequestParam("prId") String prId,@RequestParam("fileId") String fileId
    @GetMapping("/getAllData")
    public ResultForFront getAllData(@RequestParam("userName") String userName,
                                     @RequestParam("repoName") String repoName) {
        log.info("=============listTeam");
        RepoBaseEntity repoBaseEntity = repoDataService.getRepoBaseDataFromDataCollection(repoName);


        return ResultForFront.succ(repoBaseEntity);

    }
}
