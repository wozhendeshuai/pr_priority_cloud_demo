package com.jjyu.service.impl;

import com.alibaba.fastjson.JSON;
import com.jjyu.entity.PRTask;
import com.jjyu.entity.SortResult;
import com.jjyu.entity.SortedPRDetail;
import com.jjyu.service.PRSortService;
import com.jjyu.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;

@Service
@Slf4j
public class PRSortServiceImpl implements PRSortService {

    @Autowired
    private RestTemplate restTemplate;

    private String sortServiceurl = "http://localhost:9004";//"pr-sorting-engine-9004"; // //"http://pr-gateway-9001";
    private String dataServiceurl = "http://localhost:9007"; //"http://pr-gateway-9001";

    @Override
    public List<SortedPRDetail> listRule(String repoName, String sortRule) {
        //获取规则排序结果
        String path = String.format(sortServiceurl + "/prSorting/rule/sortOriginalData?repoName=" + repoName + "&sortRule=" + sortRule);//
        log.info("============path:  " + path);
        Map<String, Object> templateForRuleSort = restTemplate.getForObject(path, Map.class);
        List ruleSort = (List) templateForRuleSort.get("data");
        List<SortResult> sortResultList = new ArrayList<>();
        for (int i = 0; i < ruleSort.size(); i++) {
            SortResult sortResult = JSON.parseObject(JSON.toJSONString(ruleSort.get(i)), SortResult.class);
            log.info("==============sortResult" + sortResult.toString());
            sortResultList.add(sortResult);
        }
        //按照传入的顺序进行初步排序
        sortResultList.sort((o1, o2) -> o1.getPrOrder() - o2.getPrOrder());
        //获取所有处于打开状态的PR
        String pathData = String.format(dataServiceurl + "/dataCollection/data/getOpenData?repoName=" + repoName);
        log.info("============pathData:  " + pathData);
        Map<String, Object> templateForData = restTemplate.getForObject(pathData, Map.class);
        List openData = (List) templateForData.get("data");
        Map<Integer, SortedPRDetail> prNumberDataMap = new HashMap<>();
        for (int i = 0; i < openData.size(); i++) {
            SortedPRDetail sortedPRDetail = JSON.parseObject(JSON.toJSONString(openData.get(i)), SortedPRDetail.class);
            prNumberDataMap.put(sortedPRDetail.getPrNumber(), sortedPRDetail);
        }

        List<SortedPRDetail> reList = new ArrayList<>();
        for (SortResult temp : sortResultList) {
            reList.add(prNumberDataMap.get(temp.getPrNumber()));
        }
        return reList;
    }

    @Override
    public List<SortedPRDetail> listAlg(String userName, String prNumber, String repoName) {
        return null;
    }

    @Override
    public List<SortedPRDetail> listAlgEval(String userName, String prNumber, String repoName) {
        return null;
    }

    @Override
    public boolean reTrainModel(String prNumber, String repoName) {
        return false;
    }

    @Override
    public boolean getSortTimeTask(String userName, String prNumber, String repoName, String commentContent) {
        return false;
    }

    @Override
    public boolean setSortTimeTask(PRTask prTask) {
        return false;
    }
}
