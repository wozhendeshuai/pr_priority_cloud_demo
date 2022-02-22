package com.jjyu.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jjyu.entity.PRTask;
import com.jjyu.entity.RepoBaseEntity;
import com.jjyu.entity.TeamEntity;

import com.jjyu.entity.UserTeamEntity;
import com.jjyu.service.RepoBaseService;
import com.jjyu.service.UserService;
import com.jjyu.service.UserTeamService;
import com.jjyu.utils.ResultForFront;
import com.jjyu.utils.UserTeamRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "project/repo", tags = {"项目controller"})
@Slf4j
@RestController
@RequestMapping("project/repo")
public class RepoController {

    @Resource
    private UserService userService;

    @Autowired
    private RepoBaseService repoBaseService;
    @Autowired
    private UserTeamService userTeamService;


    /**
     * 找到该用户参与的所有项目
     *
     * @param userName
     * @return
     */
    @ApiOperation(value = "手动同步项目以及项目所有数据", notes = "reGetRepoData")
    @GetMapping("userProject")
    public ResultForFront userProject(@RequestParam("userName") String userName) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name", userName);
        List<UserTeamEntity> userTeamEntities = userTeamService.list(queryWrapper);
        if (ObjectUtils.isEmpty(userTeamEntities)) {
            return ResultForFront.fail("没有参与过项目，还请积极参与!");
        }
        List<String> teamNameList = new ArrayList<>();
        for (UserTeamEntity userTeam : userTeamEntities) {
            teamNameList.add(userTeam.getTeamName());
        }
        queryWrapper=new QueryWrapper();
        queryWrapper.in("team_name",teamNameList);
        List<RepoBaseEntity> repoBaseEntityList=repoBaseService.list(queryWrapper);
        return ResultForFront.succ(repoBaseEntityList);
    }

    //@RequestParam("prId") String prId,@RequestParam("fileId") String fileId
    @GetMapping("/listRepoData")
    @ApiOperation(value = "listRepoData", notes = "listRepoData")
    public ResultForFront listRepoData(@RequestParam("repoName") String repoName) {
        log.info("=============listTeam");


        List<RepoBaseEntity> repoBaseEntityList = repoBaseService.getAllRepo();
        for (RepoBaseEntity temp : repoBaseEntityList) {
            log.info(temp.toString());
        }


        return ResultForFront.succ(repoBaseEntityList);
    }

    //1. 手动同步项目以及项目所有数据
    @ApiOperation(value = "手动同步项目以及项目所有数据", notes = "reGetRepoData")
    @GetMapping("reSynRepoData")
    public ResultForFront reSynRepoData(@RequestParam("repoName") String repoName,
                                        @RequestParam("userName") String userName) {
        return ResultForFront.succ("");
    }

    //2. 设置自动数据同步相关参数
    //2. 查看自动数据同步相关参数
    @ApiOperation(value = "查看自动数据同步相关参数", notes = "getRepoDataTask")
    @GetMapping("getRepoDataSynTask")
    public ResultForFront getRepoDataSynTask(@RequestParam("repoName") String repoName) {
        return ResultForFront.succ("");
    }

    @ApiOperation(value = "设置自动数据同步相关参数", notes = "setRepoDataTask")
    @PostMapping("setRepoDataSynTask")
    public ResultForFront setRepoDataSynTask(PRTask prTask) {
        return ResultForFront.succ("");
    }

}
