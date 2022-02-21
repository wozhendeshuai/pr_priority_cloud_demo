package com.jjyu.service.impl;

import com.alibaba.fastjson.JSON;
import com.jjyu.entity.PRDetail;
import com.jjyu.entity.PRFileDetail;
import com.jjyu.service.PRBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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
    public boolean newPR(String userName, String repoName) {
        return false;
    }

    @Override
    public boolean mergePR(String userName, String prNumber, String repoName) {
        return false;
    }

    @Override
    public boolean closePR(String userName, String prNumber, String repoName) {
        return false;
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


}
