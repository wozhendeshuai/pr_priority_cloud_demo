package com.jjyu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jjyu.utils.DateTimeUtil;
import com.jjyu.utils.ResultForFront;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "prManage/base", tags = {"PR基础操作"})
@Slf4j
@RestController
@RequestMapping("prManage/base")
public class PRBaseController {

    //1.新建PR
    @ApiOperation(value = "newPR", notes = "newPR")
    @GetMapping("newPR")
    public ResultForFront newPR(@RequestParam("userName") String userName,
                                @RequestParam("repoName") String repoName) {

        return ResultForFront.succ("");
    }

    //2.合入PR
    @ApiOperation(value = "mergePR", notes = "mergePR")
    @GetMapping("mergePR")
    public ResultForFront mergePR(@RequestParam("userName") String userName,
                                  @RequestParam("prNumber") String prNumber,
                                  @RequestParam("repoName") String repoName) {
        return ResultForFront.succ("");
    }

    //3.关闭PR
    @ApiOperation(value = "closePR", notes = "closePR")
    @GetMapping("closePR")
    public ResultForFront closePR(@RequestParam("userName") String userName,
                                  @RequestParam("prNumber") String prNumber,
                                  @RequestParam("repoName") String repoName) {
        return ResultForFront.succ("");
    }

    //4.查看PR详情
    @ApiOperation(value = "prDetail", notes = "prDetail")
    @GetMapping("prDetail")
    public ResultForFront prDetail(@RequestParam("prNumber") String prNumber,
                                   @RequestParam("repoName") String repoName) {
        return ResultForFront.succ("");
    }
    //5.评审评论PR
    @ApiOperation(value = "commentPR", notes = "commentPR")
    @GetMapping("commentPR")
    public ResultForFront commentPR(@RequestParam("userName") String userName,
                                    @RequestParam("prNumber") String prNumber,
                                   @RequestParam("repoName") String repoName,
                                    @RequestParam("commentContent") String commentContent) {
        return ResultForFront.succ("");
    }
}
