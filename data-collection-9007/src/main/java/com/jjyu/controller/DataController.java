package com.jjyu.controller;


import cn.hutool.core.map.MapUtil;
import com.jjyu.entity.RepoBaseEntity;
import com.jjyu.service.DataService;
import com.jjyu.utils.ResultForFront;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController()
@RequestMapping("dataCollection/data")
@Slf4j
public class DataController {
    @Autowired
    private DataService dataService;

    @PostMapping("/synData")
    public ResultForFront synData(@RequestParam("maxPRNum") String maxPRNum,
                                  @RequestParam("ownerName") String ownerName,
                                  @RequestParam("repoName") String repoName) {
        log.info("=============syndata执行开始");

        dataService.synData(maxPRNum, ownerName, repoName);

        log.info("=============syndata执行结束");
        return ResultForFront.succ(200, "已成功发送指令，后台正在执行，请等待~…~", MapUtil.builder().put("now", new Date()).build());
    }

    @GetMapping("/testbaseEntity")
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
