package com.jjyu.controller;

import com.jjyu.utils.ResultForFront;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "prManage/sort", tags = {"PR排序操作"})
@Slf4j
@RestController
@RequestMapping("prManage/sort")
public class PRSortController {
    //1.1.查看算法排序列表
    @ApiOperation(value = "获取某一规则的排序列表", notes = "listRule")
    @GetMapping("listRule")
    public ResultForFront listRule(@RequestParam("repoName") String repoName,
                                   @RequestParam("sortRule") String sortRule) {
        return ResultForFront.succ("");
    }

    //1.2.查看算法排序列表
    @ApiOperation(value = "获取某一算法的排序列表", notes = "listAlg")
    @GetMapping("listAlg")
    public ResultForFront listAlg(@RequestParam("repoName") String repoName,
                                  @RequestParam("algName") String algName) {
        return ResultForFront.succ("");
    }

    //2.查看排序效果
    @ApiOperation(value = "获取某一算法在测试集效果", notes = "listAlgEval")
    @GetMapping("listAlgEval")
    public ResultForFront listAlgEval(@RequestParam("repoName") String repoName,
                                      @RequestParam("algName") String algName) {
        return ResultForFront.succ("");
    }

    //3.手动重新训练排序模型
    @ApiOperation(value = "手动重新训练排序模型", notes = "reTrainModel")
    @GetMapping("reTrainModel")
    public ResultForFront reTrainModel(@RequestParam("repoName") String repoName,
                                       @RequestParam("algName") String algName,
                                       @RequestParam("algParam") String algParam,
                                       @RequestParam("userName") String userName) {
        return ResultForFront.succ("");
    }

    //4.设置排序定时任务
    @ApiOperation(value = "查看排序定时任务", notes = "getSortTimeTask")
    @GetMapping("getSortTimeTask")
    public ResultForFront getSortTimeTask(@RequestParam("repoName") String repoName,
                                          @RequestParam("algName") String algName) {
        return ResultForFront.succ("");
    }

    @ApiOperation(value = "设置排序定时任务", notes = "setSortTimeTask")
    @GetMapping("setSortTimeTask")
    public ResultForFront setSortTimeTask(@RequestParam("repoName") String repoName,
                                          @RequestParam("algName") String algName,
                                          @RequestParam("algParam") String algParam,
                                          @RequestParam("time") String time,
                                          @RequestParam("userName") String userName) {
        return ResultForFront.succ("");
    }

}
