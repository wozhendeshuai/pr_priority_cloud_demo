package com.jjyu.controller;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jjyu.entity.PRTask;
import com.jjyu.entity.RepoBaseEntity;
import com.jjyu.entity.UserTeamEntity;
import com.jjyu.service.RepoBaseService;
import com.jjyu.service.RepoDataService;
import com.jjyu.service.UserService;
import com.jjyu.service.UserTeamService;
import com.jjyu.utils.ResultForFront;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Api(value = "project/repo", tags = {"项目controller"})
@Slf4j
@RestController
@RequestMapping("project/repo")
public class RepoController {

    @Resource
    private UserService userService;

    @Autowired
    private RepoBaseService repoBaseService;
    @Autowired
    private UserTeamService userTeamService;
    @Autowired
    private RepoDataService repoDataService;

    /**
     * 找到该用户参与的所有项目
     *
     * @param userName
     * @return
     */
    @ApiOperation(value = "找到该用户参与的所有项目", notes = "userProject")
    @GetMapping("userProject")
    public ResultForFront userProject(@RequestParam("userName") String userName) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name", userName);
        List<UserTeamEntity> userTeamEntities = userTeamService.list(queryWrapper);
        if (ObjectUtils.isEmpty(userTeamEntities)) {
            return ResultForFront.fail("没有参与过项目，还请积极参与!");
        }
        List<String> teamNameList = new ArrayList<>();
        for (UserTeamEntity userTeam : userTeamEntities) {
            teamNameList.add(userTeam.getTeamName());
        }
        queryWrapper = new QueryWrapper();
        queryWrapper.in("team_name", teamNameList);
        List<RepoBaseEntity> repoBaseEntityList = repoBaseService.list(queryWrapper);
        return ResultForFront.succ(repoBaseEntityList);
    }

    //@RequestParam("prId") String prId,@RequestParam("fileId") String fileId
    @GetMapping("/listRepoData")
    @ApiOperation(value = "listRepoData", notes = "listRepoData")
    public ResultForFront listRepoData(@RequestParam("repoName") String repoName,
                                       @RequestParam("userName") String userName) {
        log.info("=============listTeam");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name", userName);
        List<UserTeamEntity> userTeamEntities = userTeamService.list(queryWrapper);
        List<String> teamNameList = new ArrayList<>();
        for (UserTeamEntity userTeam : userTeamEntities) {
            teamNameList.add(userTeam.getTeamName());
        }
        log.info("该用户所在团队列表" + teamNameList);
        List<RepoBaseEntity> repoBaseEntityList = repoBaseService.getAllRepo();
        List<RepoBaseEntity> reList = new ArrayList<>();
        for (RepoBaseEntity temp : repoBaseEntityList) {
            if (teamNameList.contains(temp.getTeamName())) {
                reList.add(temp);
                log.info(temp.toString());
            }
        }
        return ResultForFront.succ(reList);
    }

    //1. 新导入项目
    @ApiOperation(value = "新导入项目", notes = "addNewRepo")
    @GetMapping("addNewRepo")
    public ResultForFront addNewRepo(@RequestParam("userName") String userName,
                                     @RequestParam("repoName") String repoName,
                                     @RequestParam("ownerName") String ownerName,
                                     @RequestParam("maxPRNum") Integer maxPRNum) {
        log.info("==================新导入项目所有数据");
        repoDataService.addNewRepo(repoName, ownerName, maxPRNum);
        return ResultForFront.succ("后台正在新增项目中，还请耐心等待。。。");
    }

    //1. 手动同步项目以及项目所有数据
    @ApiOperation(value = "手动同步项目以及项目所有数据", notes = "reGetRepoData")
    @GetMapping("reSynRepoData")
    public ResultForFront reSynRepoData(@RequestParam("repoName") String repoName,
                                        @RequestParam("userName") String userName,
                                        @RequestParam("maxPRNum") Integer maxPRNum) {
        log.info("==================手动同步项目所有数据");
        repoDataService.synAllData(repoName, maxPRNum);
        return ResultForFront.succ("后台正在同步中，还请耐心等待。。。");
    }


    @ApiOperation(value = "查看自动数据同步相关参数", notes = "getRepoDataTask")
    @GetMapping("getRepoDataSynTask")
    public ResultForFront getRepoDataSynTask(@RequestParam("repoName") String repoName) {
        return ResultForFront.succ("");
    }

    @ApiOperation(value = "设置自动数据同步相关参数", notes = "setRepoDataTask")
    @PostMapping("setRepoDataSynTask")
    public ResultForFront setRepoDataSynTask(PRTask prTask) {
        return ResultForFront.succ("");
    }

    @ApiOperation(value = "获取项目所有分支", notes = "repoBranch")
    @GetMapping("repoBranch")
    public ResultForFront repoBranch(@RequestParam("userName") String userName,
                                     @RequestParam("repoName") String repoName) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("repo_name", repoName);
        RepoBaseEntity repoBaseEntity = repoBaseService.getOne(queryWrapper);
        //拼接URL
        RestTemplate restTemplate =new RestTemplate();
        String path = String.format("https://api.github.com/repos/" + repoBaseEntity.getTeamName() + "/" + repoName + "/branches");//
        log.info("============path:  " + path);
        List templateForObject = restTemplate.getForObject(path, List.class);

        log.info("============templateForObject:  " + templateForObject);

        return ResultForFront.succ(templateForObject);
    }
}
