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
        queryWrapper.eq("file_to_alg_name", fileToAlgName);
        queryWrapper.eq("create_time", DateTimeUtil.getDate());
        List<FeatureFilePathEntity> filePathEntityList = featureFilePathService.list(queryWrapper);
        if (ObjectUtils.isEmpty(filePathEntityList)) {
            featureFilePathService.createFeatureFile(repoName, fileToAlgName);
            filePathEntityList = featureFilePathService.list(queryWrapper);
        }
        log.info("=============getFeatureFilePath执行结束");
        return ResultForFront.succ(filePathEntityList);
    }


}