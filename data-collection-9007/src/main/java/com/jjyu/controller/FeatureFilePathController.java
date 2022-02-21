package com.jjyu.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jjyu.entity.FeatureFilePathEntity;
import com.jjyu.entity.PRSelfEntity;
import com.jjyu.entity.RepoBaseEntity;
import com.jjyu.service.DataService;
import com.jjyu.service.FeatureFilePathService;
import com.jjyu.service.PRSelfService;
import com.jjyu.utils.DateTimeUtil;
import com.jjyu.utils.ResultForFront;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Api(value = "dataCollection/featureFile", tags = {"数据文件接口"})
@RestController()
@RequestMapping("dataCollection/featureFile")
@Slf4j
public class FeatureFilePathController {
    @Autowired
    private FeatureFilePathService featureFilePathService;


    /**
     * 获取特征文件路径列表
     *
     * @param repoName
     * @param fileToAlgName
     * @return
     */
    @GetMapping("/getFeatureFilePath")
    @ApiOperation(value = "getFeatureFilePath", notes = "获取特征文件路径列表")
    public ResultForFront getFeatureFilePath(@RequestParam("repoName") String repoName,
                                             @RequestParam("fileToAlgName") String fileToAlgName) {

        log.info("=============getFeatureFilePath执行开始");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("repo_name", repoName);
        if (fileToAlgName.equals("bayesnet")) {
            queryWrapper.eq("file_to_alg_name", fileToAlgName);
        }else{
            queryWrapper.eq("file_to_alg_name", "rank_lib");
        }

        queryWrapper.eq("create_time", DateTimeUtil.getDate());
        List<FeatureFilePathEntity> filePathEntityList = featureFilePathService.list(queryWrapper);
        log.info("=============getFeatureFilePath执行结束");
        return ResultForFront.succ(filePathEntityList == null ? "" : filePathEntityList);
    }

    /**
     * 重新计算特征文件
     *
     * @param repoName
     * @param fileToAlgName
     * @return
     */
    @GetMapping("/getNewFeatureFile")
    @ApiOperation(value = "getNewFeatureFile", notes = "重新计算特征文件")
    @Async("taskExecutor")
    public ResultForFront getNewFeatureFile(@RequestParam("repoName") String repoName,
                                            @RequestParam("fileToAlgName") String fileToAlgName) {


        featureFilePathService.createFeatureFile(repoName, fileToAlgName);
        log.info("=============getNewFeatureFile执行中");
        return ResultForFront.succ("=============getNewFeatureFile执行中");
    }

}
