package com.jjyu.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("prSorting/rule")
public class RuleSortController {
    //1.获取某一算法的排序列表
    //2.重新训练某一算法模型
    //3.重新计算某一算法/所有算法排序列表
    //4.获取排序列表在测试集上的三个指标效果列表
}
