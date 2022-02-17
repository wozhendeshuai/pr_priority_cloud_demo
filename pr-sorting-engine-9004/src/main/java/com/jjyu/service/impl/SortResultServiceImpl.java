package com.jjyu.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjyu.entity.PRSelfEntity;
import com.jjyu.entity.SortResult;
import com.jjyu.mapper.SortResultMapper;
import com.jjyu.service.SortResultService;
import com.jjyu.utils.DateTimeUtil;
import com.jjyu.utils.GetPythonOutputThread;
import com.jjyu.utils.PythonFilePath;
import com.jjyu.utils.SortRuleContext;
import com.jjyu.utils.strategy.ChangeFileSort;
import com.jjyu.utils.strategy.CreateTimeSort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.List;
import java.util.concurrent.Future;

@Service
@Slf4j
public class SortResultServiceImpl extends ServiceImpl<SortResultMapper, SortResult> implements SortResultService {
    @Autowired
    private RestTemplate restTemplate;

    private String serviceurl = "http://localhost:9007";
    // "http://pr-gateway-9001";


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
            log.info("============dataList:  " + entity.getPrNumber() + "==========" + entity.getTitle());
            reList.add(entity);
        }
        log.info("============dataList:  " + dataList);

        return reList;
    }

    @Override
    public List<SortResult> getPRDataBySortRule(String repoName, String sortRule) {
        List<PRSelfEntity> tempList = getPRDataFromDataCollection(repoName);
        if ((sortRule).equalsIgnoreCase("createtime")) {
            SortRuleContext sortRuleContext = new SortRuleContext(new CreateTimeSort());
            tempList = sortRuleContext.executeStrategy(tempList);
        } else if ((sortRule).equalsIgnoreCase("changefile")) {
            SortRuleContext sortRuleContext = new SortRuleContext(new ChangeFileSort());
            tempList = sortRuleContext.executeStrategy(tempList);
        }

        String dateTime = DateTimeUtil.getDate();
        List<SortResult> reList = new ArrayList<>();

        for (int i = 0; i < tempList.size(); i++) {
            PRSelfEntity tempPREntity = tempList.get(i);
            SortResult sortResult = new SortResult();
            sortResult.setPrNumber(tempPREntity.getPrNumber());
            sortResult.setSortDay(dateTime);
            sortResult.setAlgName(sortRule);
            sortResult.setRepoName(repoName);
            sortResult.setPrOrder(i);
            reList.add(sortResult);
        }
        return reList;
    }

    @Async("taskExecutor")
    @Override
    public Future<String> reTrainAlg(String repoName, String algName, String algPara) {
        String alg_args = "";
        //测试多种条件下不同的输出情况 !!!==加上参数u让脚本实时输出==!!!
        if (algName.equals("bayesnet")) {
            alg_args = "python  -u " + PythonFilePath.bayesnet_python_alg + " " + repoName + " " + algPara;
        } else if (algName.equals("xgboost")) {
            alg_args = "python  -u " + PythonFilePath.xgboost_python_alg + " " + repoName + " " + algPara;
        } else {
            alg_args = "python  -u " + PythonFilePath.ranklib_python_alg + " " + repoName + " " +algName+" "+ algPara;
        }

        Future<String> future;
        log.info("===================DataServiceImpl 现在的命令是args1：" + alg_args);
        try {
            //执行命令
            Process process = Runtime.getRuntime().exec(alg_args);
            Thread oThread = GetPythonOutputThread.printMessage(process.getInputStream(), process.getErrorStream());

            oThread.join();
            int exitVal = process.waitFor();
            if (0 != exitVal) {
                log.info("===================执行脚本失败");
            }
            log.info("===================执行脚本成功");

            int status = process.waitFor();
            log.info("===================Script exit code is:" + status);
            future = new AsyncResult<String>("算法计算成功，结果已保存到数据库中可以查看");
        } catch (Exception e) {
            future = new AsyncResult<String>("算法失败，具体错误为：" + e);
            e.printStackTrace();
        }
        return future;
    }

    @Override
    public List<SortResult> reCalResult(String repoName, String algName) {
        return null;
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
        temp.sort(Comparator.comparingInt(o -> o.getPrOrder()));
        return temp;
    }
}
