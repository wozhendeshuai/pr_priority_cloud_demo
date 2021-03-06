package com.jjyu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jjyu.entity.AlgTestEval;
import com.jjyu.entity.PRSelfEntity;
import com.jjyu.entity.SortResult;
import com.jjyu.service.AlgTestEvalService;
import com.jjyu.service.SortResultService;
import com.jjyu.utils.DateTimeUtil;
import com.jjyu.utils.ResultForFront;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@Api(value = "prSorting/alg", tags = {"算法排序"})
@Slf4j
@RestController
@RequestMapping("prSorting/alg")
public class AlgSortController {
    @Autowired
    private SortResultService sortResultService;
    @Autowired
    private AlgTestEvalService algTestEvalService;

    //0.获取某一算法的排序无序列表
    @ApiOperation(value = "获取某一算法的排序无序列表", notes = "listAll")
    @GetMapping("listAll")
    public ResultForFront getAll(@RequestParam("repoName") String repoName,
                                 @RequestParam("algName") String algName) {

        String dateTime = DateTimeUtil.getDate();
        //查询是否已有pr相关定时任务
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("repo_name", repoName);
        queryWrapper.eq("alg_name", algName);
        queryWrapper.eq("sort_day", dateTime);
        List<SortResult> list = sortResultService.list(queryWrapper);
        return ResultForFront.succ(list);
    }

    //1.获取某一算法的排序有序列表
    @ApiOperation(value = "获取某一算法的排序有序列表", notes = "listOrder")
    @GetMapping("listOrder")
    public ResultForFront getListorder(@RequestParam("repoName") String repoName,
                                       @RequestParam("algName") String algName) {

        String dateTime = DateTimeUtil.getDate();
        List<PRSelfEntity> list = sortResultService.getSortListByOrder(repoName, dateTime, algName);
        return ResultForFront.succ(list);
    }

    //2.重新训练某一算法模型此处应当为异步方法
    @GetMapping("reTrainAlg")
    @ApiOperation(value = "重新训练某一算法模型此处应当为异步方法", notes = "reTrainAlg")
    public ResultForFront reTrainAlg(@RequestParam("repoName") String repoName,
                                     @RequestParam("algName") String algName,
                                     @RequestParam("algParam") String algParam,
                                     @RequestParam("newFeature") Boolean newFeature,
                                     @RequestParam("userName") String userName) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("repo_name", repoName);
        queryWrapper.eq("alg_name", algName);
        Future<String> result = sortResultService.reTrainAlg(repoName, algName, algParam, newFeature);
        return ResultForFront.succ(result);
    }

    //3.重新计算某一算法/所有算法排序列表
    @GetMapping("reCalResult")
    @ApiOperation(value = "重新计算某一算法/所有算法排序列表", notes = "reCalResult")
    public ResultForFront reCalResult(@RequestParam("repoName") String repoName,
                                      @RequestParam("algName") String algName) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("repo_name", repoName);
        queryWrapper.eq("alg_name", algName);
        List<AlgTestEval> list = algTestEvalService.list(queryWrapper);
        return ResultForFront.succ(list);
    }

    //4.获取排序列表在测试集上的三个指标效果列表
    @GetMapping("listEval")
    @ApiOperation(value = "获取排序列表在测试集上的三个指标效果列表", notes = "listEval")
    public ResultForFront getlistEval(@RequestParam("repoName") String repoName,
                                      @RequestParam("algName") String algName) {
        String dateTime = DateTimeUtil.getDate();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("repo_name", repoName);
        queryWrapper.eq("alg_name", algName);
        queryWrapper.eq("train_day", dateTime);
        List<AlgTestEval> list = algTestEvalService.list(queryWrapper);
        if (ObjectUtils.isEmpty(list)) {
            if (ObjectUtils.isEmpty(repoName)) {
                return ResultForFront.fail("传参出错repoName为空");
            } else if (ObjectUtils.isEmpty(algName)) {
                return ResultForFront.fail("传参出错sortRule为空");
            }
            return ResultForFront.fail("今日还未训练模型，还请先手动训练。");
        }
        return ResultForFront.succ(list);
    }
}
