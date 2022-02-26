package com.jjyu.service.impl;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.jjyu.entity.PRDetail;
import com.jjyu.entity.PRFileDetail;
import com.jjyu.service.PRBaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PRBaseServiceImpl implements PRBaseService {

    @Autowired
    private RestTemplate restTemplate;

    private String sortServiceurl = "http://localhost:9004";//"pr-sorting-engine-9004"; // //"http://pr-gateway-9001";
    private String dataServiceurl = "http://localhost:9007"; //"http://pr-gateway-9001";

    @Override
    public List<String> listPRFileList(String repoName, String prNumber) {
        //获取规则排序结果
        String path = String.format(dataServiceurl + "/dataCollection/data/getPRFile?repoName=" + repoName + "&prNumber=" + prNumber);//
        log.info("============path:  " + path);
        Map<String, Object> templateForPRList = restTemplate.getForObject(path, Map.class);
        List prFileList = (List) templateForPRList.get("data");
        //准备返回数据
        List<String> reList = new ArrayList<>();
        for (int i = 0; i < prFileList.size(); i++) {
            PRFileDetail prFileDetail = JSON.parseObject(JSON.toJSONString(prFileList.get(i)), PRFileDetail.class);
            reList.add(prFileDetail.getChangedFileName());
        }

        List<PRFileDetail> prFileDetailList = new ArrayList<>();
        return reList;

    }

    @Override
    public PRFileDetail getPRFileDetail(String repoName, String prNumber, String fileName) {
        //获取规则排序结果
        String path = String.format(dataServiceurl + "/dataCollection/data/getPRFileDetail?repoName=" + repoName + "&prNumber=" + prNumber + "&fileName=" + fileName);//
        log.info("============path:  " + path);
        Map<String, Object> templateForPRList = restTemplate.getForObject(path, Map.class);

        PRFileDetail prFileDetail = JSON.parseObject(JSON.toJSONString(templateForPRList.get("data")), PRFileDetail.class);
        log.info("============prFileDetail:  " + prFileDetail);
        return prFileDetail;
    }

    public List<String> listPRNumberAndTitle(String repoName) {
        //获取规则排序结果
        String path = String.format(dataServiceurl + "/dataCollection/data/getAllData?repoName=" + repoName);//
        log.info("============path:  " + path);
        Map<String, Object> templateForRuleSort = restTemplate.getForObject(path, Map.class);
        List ruleSort = (List) templateForRuleSort.get("data");
        List<PRDetail> openPRDetailList = new ArrayList<>();
        List<PRDetail> closePRDetailList = new ArrayList<>();
        for (int i = 0; i < ruleSort.size(); i++) {
            PRDetail prDetail = JSON.parseObject(JSON.toJSONString(ruleSort.get(i)), PRDetail.class);
//            log.info("==============sortResult" + prDetail.toString());
            if (prDetail.getState().equals("open")) {
                openPRDetailList.add(prDetail);
            } else {
                closePRDetailList.add(prDetail);
            }
        }
        //按照传入的顺序进行初步排序
        openPRDetailList.sort((o1, o2) -> o1.getPrNumber() - o2.getPrNumber());
        closePRDetailList.sort((o1, o2) -> o1.getPrNumber() - o2.getPrNumber());
        //准备返回数据
        List<String> reList = new ArrayList<>();
        for (PRDetail prDetail : openPRDetailList) {
            reList.add(prDetail.getPrNumber() + ":" + prDetail.getTitle());
        }
        for (PRDetail prDetail : closePRDetailList) {
            reList.add(prDetail.getPrNumber() + ":" + prDetail.getTitle());
        }
        return reList;
    }

    public PRDetail getOnePRDetail(String repoName, Integer prNumber) {
        //获取规则排序结果
        String path = String.format(dataServiceurl + "/dataCollection/data/getPRData?repoName=" + repoName + "&prNumber=" + prNumber);//
        log.info("============path:  " + path);
        Map<String, Object> templateForRuleSort = restTemplate.getForObject(path, Map.class);

        PRDetail prDetail = JSON.parseObject(JSON.toJSONString(templateForRuleSort.get("data")), PRDetail.class);
        log.info("============prDetail:  " + prDetail);
        //按照传入的顺序进行初步排序
        return prDetail;
    }

    @Override
    public boolean newPR(String userName, String repoName, String ownerName, String baseBranch, String compareBranch, String prTitle, String prContent) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", "application/vnd.github.v3+json");
        httpHeaders.add("Authorization", "token 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");
        log.info("============httpHeaders:  " + httpHeaders);
        //提交参数设置
        HashMap<String, String> map = new HashMap<>();
        map.put("owner", ownerName);
        map.put("repo", repoName);
        map.put("title", prTitle);
        map.put("head", compareBranch);
        map.put("base", baseBranch);
        map.put("body", prContent);
        log.info("============map:  " + map);
        HttpEntity<HashMap<String, String>> request = new HttpEntity<>(map, httpHeaders);
        log.info("============request:  " + request);
        //拼接URL
        RestTemplate tempRestTemplate = new RestTemplate();
        String path = String.format("https://api.github.com/repos/" + ownerName + "/" + repoName + "/pulls");//
        log.info("============path:  " + path);
        try {
            Object templateForObject = tempRestTemplate.postForObject(path, request, Object.class);
            log.info("============templateForObject:  " + templateForObject);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        //结束创建后应当通知数据处理微服务去进行数据同步，然后数据微服务会将所有数据信息同步至项目管理微服务
        synDataForPRChange(repoName, ownerName);
        return true;
    }

    @Override
    public boolean updatePR(String userName, String repoName, String ownerName, Integer prNumber, String title, String body, String state) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", "application/vnd.github.v3+json");
        httpHeaders.add("Authorization", "token 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");
        log.info("============httpHeaders:  " + httpHeaders);
        //提交参数设置,哪个存在设置哪个
        HashMap<String, String> map = new HashMap<>();
        map.put("owner", ownerName);
        map.put("repo", repoName);
        if (!ObjectUtils.isEmpty(title)) {
            map.put("title", title);
        }
        if (!ObjectUtils.isEmpty(body)) {
            map.put("body", body);
        }
        if (!ObjectUtils.isEmpty(state)) {
            map.put("state", state);
        }

        log.info("============map:  " + map);
        HttpEntity<HashMap<String, String>> request = new HttpEntity<>(map, httpHeaders);
        log.info("============request:  " + request);
        //拼接URL，Patch方法需要修改RestTemplate工厂类
        RestTemplate tempRestTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        String path = String.format("https://api.github.com/repos/" + ownerName + "/" + repoName + "/pulls/" + prNumber);//
        log.info("============path:  " + path);
        try {
            Object templateForObject = tempRestTemplate.patchForObject(path, request, Object.class);
            log.info("============templateForObject:  " + templateForObject);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        //结束创建后应当通知数据处理微服务去进行数据同步，然后数据微服务会将所有数据信息同步至项目管理微服务


        synDataForPRChange(repoName, ownerName);
        return true;
    }

    @Override
    public boolean mergePR(String userName, Integer prNumber, String ownerName, String repoName,
                           String commitTitle, String commitMessage, String mergeMethod) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", "application/vnd.github.v3+json");
        httpHeaders.add("Authorization", "token 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");
        log.info("============httpHeaders:  " + httpHeaders);
        //提交参数设置,哪个存在设置哪个
        HashMap<String, String> map = new HashMap<>();
        map.put("owner", ownerName);
        map.put("repo", repoName);
        if (!ObjectUtils.isEmpty(commitTitle)) {
            map.put("commit_title", commitTitle);
        }
        if (!ObjectUtils.isEmpty(commitMessage)) {
            map.put("commit_message", commitMessage);
        }
        if (!ObjectUtils.isEmpty(mergeMethod)) {
            map.put("merge_method", mergeMethod);
        }

        log.info("============map:  " + map);
        HttpEntity<HashMap<String, String>> request = new HttpEntity<>(map, httpHeaders);
        log.info("============request:  " + request);
        //拼接URL，Patch方法需要修改RestTemplate工厂类
        RestTemplate tempRestTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        String path = String.format("https://api.github.com/repos/" + ownerName + "/" + repoName + "/pulls/" + prNumber+"/merge");//
        log.info("============path:  " + path);
        try {
            Object templateForObject = tempRestTemplate.exchange(path, HttpMethod.PUT,request, Object.class);
            log.info("============templateForObject:  " + templateForObject);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        //结束创建后应当通知数据处理微服务去进行数据同步，然后数据微服务会将所有数据信息同步至项目管理微服务


        synDataForPRChange(repoName, ownerName);
        return true;
    }


    @Override
    public PRDetail prDetail(String prNumber, String repoName) {
        return null;
    }

    @Override
    public boolean commentPR(String userName, String prNumber, String repoName, String commentContent) {
        return false;
    }

    @Override
    public boolean reviewPR(String userName, String prNumber, String repoName, String reviewContent) {
        return false;
    }


    @Async("taskExecutor")
    public void synDataForPRChange(String repoName, String ownerName) {
        Integer maxPRNum = 0;
        RestTemplate tempRestTemplate = new RestTemplate();
        //结束创建后应当通知数据处理微服务去进行数据同步，然后数据微服务会将所有数据信息同步至项目管理微服务
        String pullListPath = String.format("https://api.github.com/repos/" + ownerName + "/" + repoName + "/pulls");//
        log.info("============pullListPath:  " + pullListPath);
        List templateForObject = tempRestTemplate.getForObject(pullListPath, List.class);
        if (ObjectUtils.isEmpty(templateForObject)) {
            maxPRNum = 0;
        }
//        System.out.println(templateForObject);

        JSONObject jsonObject = new JSONObject(templateForObject.get(0));

        System.out.println(jsonObject);

        maxPRNum = jsonObject.get("number", Integer.class) + 1;
        //拼接URL
        String path = String.format(dataServiceurl + "/dataCollection/allData/synAllData?maxPRNum=" + maxPRNum
                + "&ownerName=" + ownerName
                + "&repoName=" + repoName);
        log.info("============path:  " + path);
        Map<String, Object> templateForObjectMap = restTemplate.getForObject(path, Map.class);
        Map<String, Object> dataMap = (Map<String, Object>) templateForObjectMap.get("data");

        log.info("============templateForObject:  " + templateForObjectMap);
    }
}
