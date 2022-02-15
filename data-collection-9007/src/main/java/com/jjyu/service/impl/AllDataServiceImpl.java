package com.jjyu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jjyu.entity.*;
import com.jjyu.service.*;
import com.jjyu.utils.GetPythonOutputThread;
import com.jjyu.utils.PythonFilePath;
import com.jjyu.utils.ResultForFront;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Async("taskExecutor")
@Service
@Slf4j
public class AllDataServiceImpl implements AllDataService {
    @Autowired
    private PRRepoService prRepoService;
    @Autowired
    private PRSelfService prSelfService;
    @Autowired
    private PRUserService prUserService;
    @Autowired
    private RestTemplate restTemplate;

    private final String projectManageURL = "http://localhost:9003";

    @Override
    public void synData(String maxPRNum, String ownerName, String repoName) {
        String maxPRNumStr = maxPRNum;
        //测试多种条件下不同的输出情况 !!!==加上参数u让脚本实时输出==!!!
        String args1 = "python  -u " + PythonFilePath.get_data_step_process_for_java + " " + maxPRNumStr + " " + ownerName + " " + repoName;

        log.info("===================DataServiceImpl 现在的命令是args1：" + args1);
        try {
            //执行命令
            Process process = Runtime.getRuntime().exec(args1);
            Thread oThread = GetPythonOutputThread.printMessage(process.getInputStream(), process.getErrorStream());

            oThread.join();
            int exitVal = process.waitFor();
            if (0 != exitVal) {
                log.info("===================执行脚本失败");
            }
            log.info("===================执行脚本成功");

            int status = process.waitFor();
            log.info("===================Script exit code is:" + status);

            insertRepoToProjectManageMicroService(ownerName, repoName);
            insertTeamAndUserToProjectManageMicroService(repoName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将最新的数据插入到项目管理微服务中
     *
     * @param repoName
     */
    private void insertTeamAndUserToProjectManageMicroService(String repoName) {
        //获取所有的数据后，要将相关数据同步到项目管理微服务中
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("distinct pr_user_name").eq("repo_name", repoName);
        List<PRSelfEntity> prSelfEntitiyList = prSelfService.list(queryWrapper);
        log.info("===================" + prSelfEntitiyList.toString());
        List<String> prUserNameList = new ArrayList<>();
        for (PRSelfEntity prSelf : prSelfEntitiyList) {
            prUserNameList.add(prSelf.getPrUserName());
        }
        log.info("===================" + prUserNameList);
        queryWrapper = new QueryWrapper();
        queryWrapper.in("user_name", prUserNameList);
        List<PRUserEntity> prUserEntityList = prUserService.list(queryWrapper);
        log.info("===================" + prUserEntityList.toString());
        //装载要传送的对象,先装载基础对象
        List<UserBaseEntity> userBaseEntityList = new ArrayList<>();
        for (PRUserEntity prUserEntity : prUserEntityList) {
            UserBaseEntity userBaseEntityTemp = new UserBaseEntity();
            userBaseEntityTemp.setUserId(prUserEntity.getUserId());
            userBaseEntityTemp.setUserName(prUserEntity.getUserName());
            JSONObject jsonObject = JSON.parseObject(prUserEntity.getAuthorAssociationWithRepo());
            userBaseEntityTemp.setUserRoleInTeam((String) jsonObject.get(repoName));
            userBaseEntityList.add(userBaseEntityTemp);
        }

        //再装载TeamEntity对象
        queryWrapper = new QueryWrapper();
        queryWrapper.eq("repo_name", repoName);
        PRRepoEntity prRepoEntity = prRepoService.getOne(queryWrapper);
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setTeamName(prRepoEntity.getOwnerName());
        teamEntity.setTeamSize(prRepoEntity.getTeamSize());
        teamEntity.setUserBaseEntityList(userBaseEntityList);

        JSONObject teamJsonObject = (JSONObject) JSON.toJSON(teamEntity);
        log.info(teamJsonObject.toJSONString());

        ResultForFront resultForFrontRepo = restTemplate.postForObject(projectManageURL + "/project/team/synAllTeamData", teamJsonObject, ResultForFront.class);
        System.out.println(resultForFrontRepo.getMsg());
        System.out.println(resultForFrontRepo);

    }

    /**
     * 将最新的数据插入到项目管理微服务中
     *
     * @param ownerName
     * @param repoName
     */
    private void insertRepoToProjectManageMicroService(String ownerName, String repoName) {
        //获取所有的数据后，要将相关数据同步到项目管理微服务中
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("repo_name", repoName);
        queryWrapper.eq("owner_name", ownerName);
        PRRepoEntity prRepoEntity = prRepoService.getOne(queryWrapper);
        log.info("===================" + prRepoEntity.toString());
        //装载要传送的对象,先装载基础对象
        TeamEntity teamEntity=new TeamEntity();
        teamEntity.setTeamSize(prRepoEntity.getTeamSize());
        teamEntity.setTeamName(prRepoEntity.getOwnerName());

        RepoBaseEntity repoBaseEntity = new RepoBaseEntity();
        repoBaseEntity.setRepoId(prRepoEntity.getRepoId());
        repoBaseEntity.setFullName(prRepoEntity.getFullName());
        repoBaseEntity.setRepoName(prRepoEntity.getRepoName());
        repoBaseEntity.setTeamEntity(teamEntity);
        repoBaseEntity.setTeamName(prRepoEntity.getOwnerName());
        repoBaseEntity.setRepoCreatedAt(prRepoEntity.getProjectCreatedAt());
        repoBaseEntity.setUseLanguage(prRepoEntity.getUseLanguage());
        repoBaseEntity.setLanguages(prRepoEntity.getLanguages());
        repoBaseEntity.setProjectDomain(prRepoEntity.getProjectDomain());
        //再装载RepoDayEntity对象
        RepoDayEntity repoDayEntity = new RepoDayEntity();
        repoDayEntity.setRepoBaseEntity(repoBaseEntity);
        repoDayEntity.setRepoId(prRepoEntity.getRepoId());
        repoDayEntity.setRepoUpdatedAt(prRepoEntity.getProjectUpdatedAt());
        repoDayEntity.setRepoPushedAt(prRepoEntity.getProjectPushedAt());
        repoDayEntity.setWatchersNum(prRepoEntity.getWatchers());
        repoDayEntity.setStarsNum(prRepoEntity.getStars());
        repoDayEntity.setForksNum(prRepoEntity.getForksCount());
        repoDayEntity.setContributorNum(prRepoEntity.getContributorNum());
        JSONObject jsonObject = (JSONObject) JSON.toJSON(repoDayEntity);
        log.info(jsonObject.toJSONString());

        ResultForFront resultForFrontRepo = restTemplate.postForObject(projectManageURL + "/project/repoData/synAllRepoData", jsonObject, ResultForFront.class);
        System.out.println(resultForFrontRepo.getMsg());
        System.out.println(resultForFrontRepo);
    }
}
