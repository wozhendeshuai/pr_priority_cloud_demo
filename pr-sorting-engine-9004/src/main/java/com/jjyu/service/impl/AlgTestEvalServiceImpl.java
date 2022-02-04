package com.jjyu.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjyu.entity.AlgTestEval;
import com.jjyu.entity.PRSelfEntity;
import com.jjyu.entity.SortResult;
import com.jjyu.mapper.AlgTestEvalMapper;
import com.jjyu.mapper.SortResultMapper;
import com.jjyu.service.AlgTestEvalService;
import com.jjyu.service.SortResultService;
import com.jjyu.utils.SortRuleContext;
import com.jjyu.utils.strategy.ChangeFileSort;
import com.jjyu.utils.strategy.CreateTimeSort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AlgTestEvalServiceImpl extends ServiceImpl<AlgTestEvalMapper, AlgTestEval> implements AlgTestEvalService {

}
