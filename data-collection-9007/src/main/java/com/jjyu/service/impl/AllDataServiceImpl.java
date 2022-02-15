package com.jjyu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jjyu.entity.PRRepoEntity;
import com.jjyu.entity.RepoBaseEntity;
import com.jjyu.entity.RepoDayEntity;
import com.jjyu.service.AllDataService;
import com.jjyu.service.DataService;
import com.jjyu.service.PRRepoService;
import com.jjyu.utils.GetPythonOutputThread;
import com.jjyu.utils.PythonFilePath;
import com.jjyu.utils.ResultForFront;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.List;

@Async("taskExecutor")
@Service
@Slf4j
public class AllDataServiceImpl implements AllDataService {
    @Autowired
    private PRRepoService prRepoService;
    @Autowired
    private RestTemplate restTemplate;

    private String projectManageURL = "http://localhost:9003";
    @Override
    public void synData(String maxPRNum, String ownerName, String repoName) {
        String maxPRNumStr = maxPRNum.toString();
        //测试多种条件下不同的输出情况 !!!==加上参数u让脚本实时输出==!!!
        String args1 = "python  -u " + PythonFilePath.get_data_step_process_for_java + " " + maxPRNumStr + " " + ownerName + " " + repoName;

        log.info("===================DataServiceImpl 现在的命令是args1：" + args1);
        try {
            //执行命令
            Process process = Runtime.getRuntime().exec(args1);
            Thread oThread = GetPythonOutputThread.printMessage(process.getInputStream(), process.getErrorStream());
//            oThread.start();
            oThread.join();
            int exitVal = process.waitFor();
            if (0 != exitVal) {
                log.info("===================执行脚本失败");
            }
            log.info("===================执行脚本成功");

            int status = process.waitFor();
            log.info("===================Script exit code is:" + status);

            //获取所有的数据后，要将相关数据同步到项目管理微服务中
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("repo_name", repoName);
            queryWrapper.eq("owner_name",ownerName);
            PRRepoEntity prRepoEntity=prRepoService.getOne(queryWrapper);
            log.info("==================="+prRepoEntity.toString());
            //装载要传送的对象,先装载基础对象
            RepoBaseEntity repoBaseEntity=new RepoBaseEntity();
            repoBaseEntity.setRepoId(prRepoEntity.getRepoId());
            repoBaseEntity.setFullName(prRepoEntity.getFullName());
            repoBaseEntity.setRepoName(prRepoEntity.getRepoName());
            repoBaseEntity.setTeamId(prRepoEntity.getTeamSize());
            repoBaseEntity.setTeamName(prRepoEntity.getOwnerName());
            repoBaseEntity.setRepoCreatedAt(prRepoEntity.getProjectCreatedAt());
            repoBaseEntity.setUseLanguage(prRepoEntity.getUseLanguage());
            repoBaseEntity.setLanguages(prRepoEntity.getLanguages());
            repoBaseEntity.setProjectDomain(prRepoEntity.getProjectDomain());
            //再装载RepoDayEntity对象
            RepoDayEntity repoDayEntity=new RepoDayEntity();
            repoDayEntity.setRepoBaseEntity(repoBaseEntity);
            repoDayEntity.setRepoId(prRepoEntity.getRepoId());
            repoDayEntity.setRepoUpdatedAt(prRepoEntity.getProjectUpdatedAt());
            repoDayEntity.setRepoPushedAt(prRepoEntity.getProjectPushedAt());
            repoDayEntity.setWatchersNum(prRepoEntity.getWatchers());
            repoDayEntity.setStarsNum(prRepoEntity.getStars());
            repoDayEntity.setForksNum(prRepoEntity.getForksCount());
            repoDayEntity.setContributorNum(prRepoEntity.getContributorNum());
            JSONObject jsonObject= (JSONObject) JSON.toJSON(repoDayEntity);
            log.info(jsonObject.toJSONString());

            ResultForFront resultForFrontRepo=restTemplate.postForObject(projectManageURL+"/project/repoData/synAllRepoData",jsonObject,ResultForFront.class);
            System.out.println(resultForFrontRepo.getMsg());
            System.out.println(resultForFrontRepo.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
