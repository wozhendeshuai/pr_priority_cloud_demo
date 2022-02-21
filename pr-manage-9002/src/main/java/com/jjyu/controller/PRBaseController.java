package com.jjyu.controller;

import com.jjyu.entity.PRDetail;
import com.jjyu.entity.PRFileDetail;
import com.jjyu.service.PRBaseService;
import com.jjyu.utils.ResultForFront;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "prManage/base", tags = {"PR基础操作"})
@Slf4j
@RestController
@RequestMapping("prManage/prBase")
public class PRBaseController {
    @Autowired
    PRBaseService prBaseService;


    @ApiOperation(value = "getPRNumberAndTitle", notes = "获取项目中所有PRnumber和title")
    @GetMapping("getPRNumberAndTitle")
    public ResultForFront getPRNumberAndTitle(@RequestParam("userName") String userName,
                                              @RequestParam("repoName") String repoName) {
        if (ObjectUtils.isEmpty(repoName)) {
            return ResultForFront.fail("未选择项目，请选择！");
        }
        List<String> prNumbers = prBaseService.listPRNumberAndTitle(repoName);
        if (ObjectUtils.isEmpty(prNumbers)) {
            return ResultForFront.fail("后端prNumberList出错");
        }
        return ResultForFront.succ(prNumbers);
    }

    @ApiOperation(value = "getPRFileList", notes = "获取PR中所有修改的文件列表")
    @GetMapping("getPRFileList")
    public ResultForFront getPRFileList(@RequestParam("userName") String userName,
                                        @RequestParam("repoName") String repoName,
                                        @RequestParam("prNumber") String prNumber) {
        if (ObjectUtils.isEmpty(repoName)) {
            return ResultForFront.fail("未选择项目，请选择！");
        }
        List<String> prFileNameList = prBaseService.listPRFileList(repoName, prNumber);
        if (ObjectUtils.isEmpty(prFileNameList)) {
            return ResultForFront.fail("后端getPRFileList出错");
        }
        return ResultForFront.succ(prFileNameList);
    }

    @ApiOperation(value = "getPRFileDetail", notes = "获取PR中所有修改的文件列表")
    @GetMapping("getPRFileDetail")
    public ResultForFront getPRFileDetail(@RequestParam("userName") String userName,
                                          @RequestParam("repoName") String repoName,
                                          @RequestParam("prNumber") String prNumber,
                                          @RequestParam("fileName") String fileName) {

        PRFileDetail prFileDetail = prBaseService.getPRFileDetail(repoName, prNumber, fileName);
        if (ObjectUtils.isEmpty(prFileDetail)) {
            return ResultForFront.fail("后端getPRFileDetail出错");
        }
        return ResultForFront.succ(prFileDetail);
    }


    @ApiOperation(value = "getPRDetail", notes = "获取项目中某个PR的详情信息")
    @GetMapping("getPRDetail")
    public ResultForFront getPRDetail(@RequestParam("userName") String userName,
                                      @RequestParam("prNumber") Integer prNumber,
                                      @RequestParam("repoName") String repoName) {
        PRDetail prDetail = prBaseService.getOnePRDetail(repoName, prNumber);
        if (ObjectUtils.isEmpty(prDetail)) {
            return ResultForFront.fail("后端prNumberList出错");
        }
        return ResultForFront.succ(prDetail);
    }

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

    //5.评论PR
    @ApiOperation(value = "commentPR", notes = "commentPR")
    @GetMapping("commentPR")
    public ResultForFront commentPR(@RequestParam("userName") String userName,
                                    @RequestParam("prNumber") String prNumber,
                                    @RequestParam("repoName") String repoName,
                                    @RequestParam("commentContent") String commentContent) {
        return ResultForFront.succ("");
    }

    //5.评审PR
    @ApiOperation(value = "reviewPR", notes = "reviewPR")
    @GetMapping("reviewPR")
    public ResultForFront reviewPR(@RequestParam("userName") String userName,
                                   @RequestParam("prNumber") String prNumber,
                                   @RequestParam("repoName") String repoName,
                                   @RequestParam("reviewContent") String reviewContent) {
        return ResultForFront.succ("");
    }
}
