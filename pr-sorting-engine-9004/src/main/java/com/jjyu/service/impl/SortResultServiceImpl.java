package com.jjyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjyu.entity.SortResult;
import com.jjyu.mapper.SortResultMapper;
import com.jjyu.service.SortResultService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class SortResultServiceImpl extends ServiceImpl<SortResultMapper, SortResult> implements SortResultService {
    @Override
    public List<SortResult> getSortListByOrder(String repoName, String dateTime, String algName) {
        //查询是否已有pr相关定时任务
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("repo_name", repoName);
        queryWrapper.eq("alg_name", algName);
        queryWrapper.eq("sort_day", dateTime);
        List<SortResult> temp = this.baseMapper.selectList(queryWrapper);
        //按照prOrder升序排列
        temp.sort(Comparator.comparingInt(o -> Integer.parseInt(o.getPrOrder())));
        return temp;
    }
}
