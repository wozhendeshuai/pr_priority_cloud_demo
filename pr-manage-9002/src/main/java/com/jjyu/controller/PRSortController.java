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
@RequestMapping("prSorting/alg")
public class PRSortController {

    //1.查看排序列表
    @ApiOperation(value = "获取某一算法的排序无序列表", notes = "listAll")
    @GetMapping("listAll")
    public ResultForFront getAll(@RequestParam("repoName") String repoName,
                                 @RequestParam("algName") String algName) {


        return ResultForFront.succ("");
    }
    //2.查看排序效果
    //3.重新训练排序模型
    //4.设置排序定时任务

}
