package com.jjyu.controller;


import cn.hutool.core.map.MapUtil;
import com.jjyu.service.AllDataService;
import com.jjyu.service.DataService;
import com.jjyu.service.PRRepoService;
import com.jjyu.utils.ResultForFront;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
@Api(value = "dataCollection/allData", tags = {"所有数据同步"})
@RestController()
@RequestMapping("dataCollection/allData")
@Slf4j
public class AllDataCollectionController {
    @Autowired
    private AllDataService allDataService;


    @PostMapping("/synAllData")
    @ApiOperation(value = "synData", notes = "synData")
    public ResultForFront synData(@RequestParam("maxPRNum") String maxPRNum,
                                  @RequestParam("ownerName") String ownerName,
                                  @RequestParam("repoName") String repoName) {
        log.info("=============syndata执行开始");

        allDataService.synData(maxPRNum, ownerName, repoName);

        log.info("=============syndata执行结束");
        return ResultForFront.succ(200, "已成功发送指令，后台正在执行，请等待~…~", MapUtil.builder().put("now", new Date()).build());
    }
}
