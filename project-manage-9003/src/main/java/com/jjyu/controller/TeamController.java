package com.jjyu.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jjyu.entity.*;
import com.jjyu.service.TeamService;
import com.jjyu.service.UserService;
import com.jjyu.service.UserTeamService;
import com.jjyu.utils.ResultForFront;
import com.jjyu.utils.UserTeamRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "project/team", tags = {"团队controller"})
@Slf4j
@RestController
@RequestMapping("project/team")
public class TeamController {

    @Value("${server.port}")
    private int port;
    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserTeamService userTeamService;

    //@RequestParam("prId") String prId,@RequestParam("fileId") String fileId
    @ApiOperation(value = "查看团队列表", notes = "listTeam")
    @GetMapping("/listteam")
    public ResultForFront listTeam() {
        log.info("=============listTeam");


        List<TeamEntity> teamEntityList = teamService.list();
        for (TeamEntity temp : teamEntityList) {
            log.info(temp.toString());
        }


        return ResultForFront.succ(MapUtil.builder()
                .put("port", "当前的端口是：" + port)
                .put("teamEntityList", teamEntityList)
                .build());
    }

    //@RequestParam("prId") String prId,@RequestParam("fileId") String fileId
    //这个没改
    @ApiOperation(value = "查看团队列表和团队中的用户", notes = "listTeamAndUser")
    @GetMapping("/listTeamAndUser")
    public ResultForFront listTeamAndUser(@RequestParam("teamName") String teamName) {
        log.info("=============listTeam");

        List<TeamEntity> teamEntityList = teamService.getAllTeamAndUser();
        for (TeamEntity temp : teamEntityList) {
            log.info(temp.toString());
        }

        return ResultForFront.succ(MapUtil.builder()
                .put("port", "当前的端口是：" + port)
                .put("teamEntityList", teamEntityList)
                .build());
    }

    /**
     * 为团队新增成员
     *
     * @param teamName
     * @param userName
     * @param userRoleInTeam
     * @return
     */
    //@RequestParam("prId") String prId,@RequestParam("fileId") String fileId
    @ApiOperation(value = "为团队新增成员", notes = "addMember")
    @GetMapping("/addmember")
    public ResultForFront addMember(@RequestParam("teamName") String teamName,
                                    @RequestParam("userName") String userName,
                                    @RequestParam("userRoleInTeam") String userRoleInTeam) {
        //调用枚举类判断是否有该角色
        if (!UserTeamRole.hasRole(userRoleInTeam)) {
            return ResultForFront.fail("角色不存在");
        }
        //判断是否存在该用户
        QueryWrapper userQueryWrapper = new QueryWrapper();
        userQueryWrapper.eq("user_name", userName);
        UserBaseEntity userBaseEntityTemp = userService.getOne(userQueryWrapper);
        if (userBaseEntityTemp == null) {
            return ResultForFront.fail("无该用户");
        }

        //判断团队是否存在
        QueryWrapper teamQueryWrapper = new QueryWrapper();
        teamQueryWrapper.eq("team_name", teamName);
        TeamEntity teamEntityTemp = teamService.getOne(teamQueryWrapper);
        if (teamEntityTemp == null) {
            return ResultForFront.fail("无该团队");
        }

        //判断该用户是否已在团队中。
        if (teamService.hasUserByUserName(userName, teamName)) {
            return ResultForFront.fail("用户已在团队");
        }

        UserTeamEntity userTeamEntity = new UserTeamEntity();

        userTeamEntity.setUserName(userName);
        userTeamEntity.setUserId(userBaseEntityTemp.getUserId());

        userTeamEntity.setTeamName(teamName);
        userTeamEntity.setTeamId(teamEntityTemp.getTeamId());

        userTeamEntity.setUserRoleInTeam(userRoleInTeam);

        userTeamService.save(userTeamEntity);

        List<TeamEntity> teamEntityList = teamService.getAllTeamAndUser();
        for (TeamEntity temp : teamEntityList) {
            log.info(temp.toString());
        }

        //todo: 发邮件


        return ResultForFront.succ(MapUtil.builder()
                .put("port", "当前的端口是：" + port)
                .put("teamEntityList", teamEntityList)
                .build());
    }

    /**
     * 从指定团队中删除用户
     *
     * @param teamName
     * @param userName
     * @return
     */
    @ApiOperation(value = "从指定团队中删除用户", notes = "deleteMember")
    @PostMapping("/delmember")
    public ResultForFront deleteMember(@RequestParam("teamName") String teamName,
                                       @RequestParam("userName") String userName) {
        //判断是否存在该用户
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name", userName);
        UserBaseEntity userBaseEntityTemp = userService.getOne(queryWrapper);
        if (userBaseEntityTemp == null) {
            return ResultForFront.fail("无该用户");
        }

        //判断团队是否存在
        QueryWrapper teamQueryWrapper = new QueryWrapper();
        teamQueryWrapper.eq("team_name", teamName);
        TeamEntity teamEntityTemp = teamService.getOne(teamQueryWrapper);
        if (teamEntityTemp == null) {
            return ResultForFront.fail("无该团队");
        }

        //判断该用户是否已在团队中。
        if (!teamService.hasUserByUserName(userName, teamName)) {
            return ResultForFront.fail("用户不在团队");
        }

        queryWrapper.eq("team_name", teamName);
        boolean remove = userTeamService.remove(queryWrapper);

        List<TeamEntity> teamEntityList = teamService.getAllTeamAndUser();
        for (TeamEntity temp : teamEntityList) {
            log.info(temp.toString());
        }

        return ResultForFront.succ(MapUtil.builder()
                .put("status", remove)
                .put("port", "当前的端口是：" + port)
                .put("teamEntityList", teamEntityList).build());
    }

    /**
     * 权限管理
     *
     * @param teamName
     * @param userName
     * @param userRoleInTeam
     * @return
     */
    @ApiOperation(value = "权限管理", notes = "updateMember")
    @PostMapping("/updatemember")
    public ResultForFront updateMember(@RequestParam("teamName") String teamName,
                                       @RequestParam("userName") String userName,
                                       @RequestParam("userRoleInTeam") String userRoleInTeam) {
        //调用枚举类判断是否有该角色
        if (!UserTeamRole.hasRole(userRoleInTeam)) {
            return ResultForFront.fail("角色不存在");
        }

        //判断是否存在该用户
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name", userName);
        UserBaseEntity userBaseEntityTemp = userService.getOne(queryWrapper);
        if (userBaseEntityTemp == null) {
            return ResultForFront.fail("无该用户");
        }

        //判断团队是否存在
        QueryWrapper teamQueryWrapper = new QueryWrapper();
        teamQueryWrapper.eq("team_name", teamName);
        TeamEntity teamEntityTemp = teamService.getOne(teamQueryWrapper);
        if (teamEntityTemp == null) {
            return ResultForFront.fail("无该团队");
        }

        //判断该用户是否已在团队中。
        if (!teamService.hasUserByUserName(userName, teamName)) {
            return ResultForFront.fail("用户不在团队");
        }
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("user_name", userName);
        updateWrapper.eq("team_name", teamName);
        UserTeamEntity userTeamEntity = userTeamService.getOne(updateWrapper);
        userTeamEntity.setUserRoleInTeam(userRoleInTeam);
        boolean update = userTeamService.update(userTeamEntity, updateWrapper);

        List<TeamEntity> teamEntityList = teamService.getAllTeamAndUser();
        for (TeamEntity temp : teamEntityList) {
            log.info(temp.toString());
        }

        return ResultForFront.succ(MapUtil.builder()
                .put("status", update)
                .put("port", "当前的端口是：" + port)
                .put("teamEntityList", teamEntityList)
                .build());
    }

    @ApiOperation(value = "synAllTeamData", notes = "同步团队所有数据")
    @PostMapping("/synAllTeamData")
    public ResultForFront synAllTeamData(@RequestBody TeamEntity teamEntity) {
        log.info("=============synAllRepoData");
        // RepoBaseEntity repoBaseEntity = repoDataService.getRepoBaseDataFromDataCollection(repoName);
        log.info(teamEntity.toString());
        //判断一下是否已有
        String teamName = teamEntity.getTeamName();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("team_name", teamName);
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("team_name", teamName);
        TeamEntity teamEntityTemp = teamService.getOne(queryWrapper);
        //若是不存在，则插入一下
        if (ObjectUtils.isEmpty(teamEntityTemp)) {
            teamService.save(teamEntity);
        } else {
            //若是存在，则更新
            teamEntity.setTeamId(teamEntityTemp.getTeamId());
            teamService.update(teamEntity, updateWrapper);
        }
        List<UserBaseEntity> userBaseEntityList = teamEntity.getUserBaseEntityList();
        boolean insertUsers = userService.synUserList(userBaseEntityList, teamEntity.getTeamId(), teamEntity.getTeamName());
        return ResultForFront.succ("成功完成数据同步");

    }


}
