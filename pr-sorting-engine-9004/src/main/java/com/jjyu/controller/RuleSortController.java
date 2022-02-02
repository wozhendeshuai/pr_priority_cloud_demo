package com.jjyu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jjyu.entity.SortResult;
import com.jjyu.service.SortResultService;
import com.jjyu.utils.DateTimeUtil;
import com.jjyu.utils.ResultForFront;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("prSorting/rule")
public class RuleSortController {
    @Autowired
    private SortResultService sortResultService;

    //0.先尝试获取原始数据
    @GetMapping("listOriginalData")
    public ResultForFront listOriginalData(@RequestParam("repoName") String repoName) {

        List reList = sortResultService.getPRDataFromDataCollection(repoName);
        return ResultForFront.succ(reList);
    }
    //1.获取某一规则对PR的排序列表

    //2.当有其它对PR的操作时，要实时更新相关数据

}
