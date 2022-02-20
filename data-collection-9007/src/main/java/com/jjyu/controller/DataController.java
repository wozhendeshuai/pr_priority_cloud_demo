package com.jjyu.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jjyu.entity.PRSelfEntity;
import com.jjyu.entity.RepoBaseEntity;
import com.jjyu.service.DataService;
import com.jjyu.service.PRSelfService;
import com.jjyu.utils.ResultForFront;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Api(value = "dataCollection/data", tags = {"部分数据同步接口"})
@RestController()
@RequestMapping("dataCollection/data")
@Slf4j
public class DataController {
    @Autowired
    private DataService dataService;
    @Autowired
    private PRSelfService prSelfService;

    @GetMapping("/getOpenData")
    @ApiOperation(value = "getOpenData", notes = "getOpenData")
    public ResultForFront getOpenData(
            @RequestParam("repoName") String repoName) {
        log.info("=============getOpenData执行开始");

        List<PRSelfEntity> allOpenPRList = prSelfService.getAllOpenPRFromRepoName(repoName);

        log.info("=============getOpenData执行结束");
        return ResultForFront.succ(allOpenPRList);
    }

    @GetMapping("/getAllData")
    @ApiOperation(value = "getAllData", notes = "获取所有PR的相信信息")
    public ResultForFront getAllData(
            @RequestParam("repoName") String repoName) {
        log.info("=============getAllData执行开始");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("repo_name", repoName);
        List<PRSelfEntity> allPRList = prSelfService.list(queryWrapper);
        log.info("=============getAllData执行结束");
        return ResultForFront.succ(allPRList);
    }

    @GetMapping("/getPRData")
    @ApiOperation(value = "getPRData", notes = "获取一个PR的相信信息")
    public ResultForFront getPRData(
            @RequestParam("repoName") String repoName, @RequestParam("prNumber") Integer prNumber) {
        log.info("=============getAllData执行开始");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("repo_name", repoName);
        queryWrapper.eq("pr_number", prNumber);
        PRSelfEntity prSelfEntity = prSelfService.getOne(queryWrapper);
        log.info("=============getAllData执行结束");
        return ResultForFront.succ(prSelfEntity);
    }

    @PostMapping("/synData")
    @ApiOperation(value = "synData", notes = "synData")
    public ResultForFront synData(@RequestParam("maxPRNum") String maxPRNum,
                                  @RequestParam("ownerName") String ownerName,
                                  @RequestParam("repoName") String repoName) {
        log.info("=============syndata执行开始");

        dataService.synData(maxPRNum, ownerName, repoName);

        log.info("=============syndata执行结束");
        return ResultForFront.succ(200, "已成功发送指令，后台正在执行，请等待~…~", MapUtil.builder().put("now", new Date()).build());
    }

    @GetMapping("/testbaseEntity")
    @ApiOperation(value = "testbaseEntity", notes = "testbaseEntity")
    public ResultForFront testbaseEntity(@RequestParam("repoName") String repoName) {
        log.info("=============testbaseEntity执行开始");

        RepoBaseEntity repoBaseEntity = new RepoBaseEntity();
        repoBaseEntity.setRepoId(123);
        repoBaseEntity.setFullName("test/testrepo");
        repoBaseEntity.setRepoName(repoName);

        log.info("=============syndata执行结束");
        return ResultForFront.succ(200, "ok成功", repoBaseEntity);
    }
}
