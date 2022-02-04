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

@Api(value = "prSorting/alg", tags = {"算法排序"})
@Slf4j
@RestController
@RequestMapping("prSorting/alg")
public class PRBaseController {

    //0.获取某一算法的排序无序列表
    @ApiOperation(value = "获取某一算法的排序无序列表", notes = "listAll")
    @GetMapping("listAll")
    public ResultForFront getAll(@RequestParam("repoName") String repoName,
                                 @RequestParam("algName") String algName) {


        return ResultForFront.succ("");
    }


}
