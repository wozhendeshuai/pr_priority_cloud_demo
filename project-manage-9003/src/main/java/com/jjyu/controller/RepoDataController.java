package com.jjyu.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jjyu.entity.RepoBaseEntity;
import com.jjyu.entity.RepoDayEntity;
import com.jjyu.entity.TeamEntity;
import com.jjyu.service.*;

import com.jjyu.utils.ResultForFront;
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
import java.util.List;

@Api(value = "project/repoData", tags = {"项目数据接口"})
@RestController
@RequestMapping("project/repoData")
@Slf4j
public class RepoDataController {
    @Resource
    private UserService userService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private RepoBaseService repoBaseService;

    @Autowired
    private RepoDayService repoDayService;


    @ApiOperation(value = "getAllData", notes = "获取所有数据")
    @GetMapping("/getAllData")
    public ResultForFront getAllData(@RequestParam("userName") String userName,
                                     @RequestParam("repoName") String repoName) {
        log.info("=============getAllData");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("repo_name", repoName);
        RepoBaseEntity repoBaseEntity = repoBaseService.getOne(queryWrapper);
        queryWrapper = new QueryWrapper();
        queryWrapper.eq("repo_id", repoBaseEntity.getRepoId());
        RepoDayEntity repoDayEntity = repoDayService.getOne(queryWrapper);
        repoDayEntity.setRepoBaseEntity(repoBaseEntity);
        return ResultForFront.succ(repoDayEntity);

    }

    @ApiOperation(value = "synAllRepoData", notes = "同步所有项目数据，交由数据处理微服务调用")
    @PostMapping("/synAllRepoData")
    public ResultForFront synAllRepoData(@RequestBody RepoDayEntity repoDayEntity) {
        log.info("=============synAllRepoData");

        log.info(repoDayEntity.toString());
        //对团队相关信息进行更新
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("team_name", repoDayEntity.getRepoBaseEntity().getTeamName());
        TeamEntity teamEntity = teamService.getOne(queryWrapper);
        if (ObjectUtils.isEmpty(teamEntity)) {
            teamService.save(repoDayEntity.getRepoBaseEntity().getTeamEntity());
        } else {
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.eq("team_name", repoDayEntity.getRepoBaseEntity().getTeamName());
            teamService.update(teamEntity, updateWrapper);
        }
        //获取到团队ID
        teamEntity = teamService.getOne(queryWrapper);
        //判断一下是否已有
        Integer repoId = repoDayEntity.getRepoId();
        queryWrapper = new QueryWrapper();
        queryWrapper.eq("repo_id", repoId);
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("repo_id", repoId);
        RepoBaseEntity repoBaseEntity = repoBaseService.getOne(queryWrapper);
        RepoDayEntity repoDayEntityTemp = repoDayService.getOne(queryWrapper);

        RepoBaseEntity repoBase = repoDayEntity.getRepoBaseEntity();
        repoBase.setTeamId(teamEntity.getTeamId());
        if (ObjectUtils.isEmpty(repoBaseEntity)) {
            repoBaseService.save(repoBase);
        } else {
            repoBaseService.update(repoBase, updateWrapper);
        }

        if (ObjectUtils.isEmpty(repoDayEntityTemp)) {
            repoDayService.save(repoDayEntity);
        } else {
            repoDayService.update(repoDayEntity, updateWrapper);
        }

        return ResultForFront.succ(repoDayEntity);

    }
}
