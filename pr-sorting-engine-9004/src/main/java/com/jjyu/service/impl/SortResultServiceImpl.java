package com.jjyu.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjyu.entity.PRSelfEntity;
import com.jjyu.entity.SortResult;
import com.jjyu.mapper.SortResultMapper;
import com.jjyu.service.SortResultService;
import com.jjyu.utils.SortRuleContext;
import com.jjyu.utils.strategy.ChangeFileSort;
import com.jjyu.utils.strategy.CreateTimeSort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SortResultServiceImpl extends ServiceImpl<SortResultMapper, SortResult> implements SortResultService {
    @Autowired
    private RestTemplate restTemplate;

    private String serviceurl = "http://localhost:9007";
    // "http://pr-gateway-9001";

    private String serviceId = "pr-gateway-9001";

    public List<PRSelfEntity> getPRDataFromDataCollection(String repoName) {
        //拼接URL
        String path = String.format(serviceurl + "/dataCollection/data/getOpenData?repoName=" + repoName);//
        log.info("============path:  " + path);
        Map<String, Object> templateForObject = restTemplate.getForObject(path, Map.class);
        List<PRSelfEntity> dataList = (List<PRSelfEntity>) templateForObject.get("data");

//        log.info("============templateForObject:  " + templateForObject);
        List<PRSelfEntity> reList = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            PRSelfEntity entity = JSON.parseObject(JSON.toJSONString(dataList.get(i)), PRSelfEntity.class);
            log.info("============dataList:  " + entity.toString());
            reList.add(entity);
        }
        log.info("============dataList:  " + dataList);

        return reList;
    }

    @Override
    public List<PRSelfEntity> getPRDataBySortRule(String repoName, String sortRule) {
        List<PRSelfEntity> tempList = getPRDataFromDataCollection(repoName);
        if ((sortRule.toLowerCase()).equals("createtime")) {
            SortRuleContext sortRuleContext = new SortRuleContext(new CreateTimeSort());
            tempList = sortRuleContext.executeStrategy(tempList);
        } else if ((sortRule.toLowerCase()).equals("changefile")) {
            SortRuleContext sortRuleContext = new SortRuleContext(new ChangeFileSort());
            tempList = sortRuleContext.executeStrategy(tempList);
        }
        return tempList;
    }

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
