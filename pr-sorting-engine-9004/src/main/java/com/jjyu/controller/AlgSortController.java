package com.jjyu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jjyu.entity.SortResult;
import com.jjyu.service.SortResultService;
import com.jjyu.utils.DateTimeUtil;
import com.jjyu.utils.ResultForFront;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("prSorting/alg")
public class AlgSortController {
    @Autowired
    private SortResultService sortResultService;

    //1.获取某一算法的排序列表
    @GetMapping("listall")
    public ResultForFront getAll(@RequestParam("repoName") String repoName,
                                 @RequestParam("algName") String algName) throws ParseException {

        String dateTime = DateTimeUtil.getDate();

        //查询是否已有pr相关定时任务
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("repo_name", repoName);
        queryWrapper.eq("alg_name", algName);
        queryWrapper.eq("sort_day", dateTime);
        List<SortResult> list = sortResultService.list(queryWrapper);
        return ResultForFront.succ(list);
    }
    //2.重新训练某一算法模型
    //3.重新计算某一算法/所有算法排序列表
    //4.获取排序列表在测试集上的三个指标效果列表
}
