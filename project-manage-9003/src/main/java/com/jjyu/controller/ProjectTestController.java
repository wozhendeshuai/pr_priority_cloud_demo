package com.jjyu.controller;


import com.jjyu.service.*;
import com.jjyu.utils.ResultForFront;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Api(value = "project/test", tags = {"项目测试接口"})
@RestController
@RequestMapping("project/test")
@Slf4j
public class ProjectTestController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private RestTemplate restTemplate;

//    //@RequestParam("prId") String prId,@RequestParam("fileId") String fileId
//    @ApiOperation(value = "测试port", notes = "testport")
//    @GetMapping("/testport")
//    public Map<String, Object> findPRselfAndFile() {
//        log.info("=============ProjectTestController调用到啦");
////        ServiceInstance serviceInstance = loadBalancerClient.choose("PRFileApplication");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("status", true);
////        map.put("msg", "当前调用的是PR服务，查询PR的id：" + prId + " 查询的PRfile为：" + fileId);
////        String path=String.format(serviceInstance+"/prfile/find?id=%s",fileId);
////        map.put("fileService", "调用prFile服务的结果是：" + restTemplate.getForObject(path, String.class));
//        map.put("port", "当前的端口是：" + port);
//
//
//        return map;
//    }

    @Autowired
    private RepoBaseService repoBaseService;

    @ApiOperation(value = "listRepo", notes = "listRepo")
    @GetMapping("/listRepo")
    public ResultForFront listRepo() {
        return ResultForFront.succ(repoBaseService.list());
    }

    @Autowired
    private RepoDayService repoDayService;

    @ApiOperation(value = "listRepoDay", notes = "listRepoDay")
    @GetMapping("/listRepoDay")
    public ResultForFront listRepoDay() {
        return ResultForFront.succ(repoDayService.list());
    }

    @Autowired
    private TeamService teamService;

    @ApiOperation(value = "listTeam", notes = "listTeam")
    @GetMapping("/listTeam")
    public ResultForFront listTeam() {
        return ResultForFront.succ(teamService.list());
    }

    @Autowired
    private UserService userService;

    @ApiOperation(value = "listUser", notes = "listUser")
    @GetMapping("/listUser")
    public ResultForFront listUser() {
        return ResultForFront.succ(userService.list());
    }

    @Autowired
    private UserTeamService userTeamService;

    @ApiOperation(value = "listUserTeam", notes = "listUserTeam")
    @GetMapping("/listUserTeam")
    public ResultForFront listUserTeam() {
        return ResultForFront.succ(userTeamService.list());
    }

}
