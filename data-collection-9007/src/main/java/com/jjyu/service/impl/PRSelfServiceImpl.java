package com.jjyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjyu.entity.PRSelfEntity;

import com.jjyu.mapper.PRSelfMapper;

import com.jjyu.service.PRSelfService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PRSelfServiceImpl extends ServiceImpl<PRSelfMapper, PRSelfEntity> implements PRSelfService {

    @Override
    public List<PRSelfEntity> getAllOpenPRFromRepoName(String repoName) {
        //查询是否已有pr相关定时任务
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("repo_name", repoName);
        queryWrapper.eq("state", "open");
        List<PRSelfEntity> temp = this.baseMapper.selectList(queryWrapper);
        return temp;
    }
}
