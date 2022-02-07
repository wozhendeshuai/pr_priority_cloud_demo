package com.jjyu.controller;

import com.jjyu.entity.PRTask;
import com.jjyu.service.PRSortService;
import com.jjyu.utils.ResultForFront;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "prManage/sort", tags = {"PR排序操作"})
@Slf4j
@RestController
@RequestMapping("prManage/sort")
public class PRSortController {
    @Autowired
    private PRSortService prSortService;

    //1.1.查看规则排序列表
    @ApiOperation(value = "获取某一规则的排序列表", notes = "listRule")
    @GetMapping("listRule")
    public ResultForFront listRule(@RequestParam("repoName") String repoName,
                                   @RequestParam("sortRule") String sortRule) {

        return ResultForFront.succ(prSortService.listRule(repoName, sortRule));
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
    @PostMapping("setSortTimeTask")
    public ResultForFront setSortTimeTask(PRTask prTask) {
        return ResultForFront.succ("");
    }

}
