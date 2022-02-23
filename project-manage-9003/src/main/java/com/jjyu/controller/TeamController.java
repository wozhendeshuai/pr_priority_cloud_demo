package com.jjyu.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jjyu.entity.*;
import com.jjyu.service.TeamService;
import com.jjyu.service.UserService;
import com.jjyu.service.UserTeamService;
import com.jjyu.utils.ResultForFront;
import com.jjyu.utils.UserRepoRole;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "project/team", tags = {"团队controller"})
@Slf4j
@RestController
@RequestMapping("project/team")
public class TeamController {


    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserTeamService userTeamService;


    /**
     * 找到该用户参与的所有项目
     *
     * @param userName
     * @return
     */
    @ApiOperation(value = "得到用户所在的所有的团队列表", notes = "userTeamList")
    @GetMapping("userTeamList")
    public ResultForFront userTeamList(@RequestParam("userName") String userName) {
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

        return ResultForFront.succ(teamNameList);
    }

    //@RequestParam("prId") String prId,@RequestParam("fileId") String fileId
    @ApiOperation(value = "查看团队列表", notes = "listTeam")
    @GetMapping("/listteam")
    public ResultForFront listTeam() {
        log.info("=============listTeam");

        List<TeamEntity> teamEntityList = teamService.list();
        for (TeamEntity temp : teamEntityList) {
            log.info(temp.toString());
        }

        return ResultForFront.succ(teamEntityList);
    }

    //@RequestParam("prId") String prId,@RequestParam("fileId") String fileId
    //这个没改
    @ApiOperation(value = "查看团队列表和团队中的用户", notes = "listTeamAndMemberUser")
    @GetMapping("/listTeamAndMemberUser")
    public ResultForFront listTeamAndMemberUser(@RequestParam("teamName") String teamName,
                                                @RequestParam("userName") String userName) {
        log.info("=============listTeamAndUser");
        TeamEntity teamEntity = teamService.getTeamAndMemberUser(teamName, userName);
        if (ObjectUtils.isEmpty(teamEntity)) {
            return ResultForFront.fail("后端listTeamAndUser出错");
        }
        return ResultForFront.succ(teamEntity);
    }

    //@RequestParam("prId") String prId,@RequestParam("fileId") String fileId
    //这个没改
    @ApiOperation(value = "查看非团队成员的用户", notes = "listNotTeamUser")
    @GetMapping("/listNotTeamUser")
    public ResultForFront listNotTeamUser(@RequestParam("teamName") String teamName,
                                          @RequestParam("userName") String userName) {
        log.info("=============listTeamAndUser");
        List<String> userNameList = teamService.getNotTeamAndUser(teamName, userName);
        if (ObjectUtils.isEmpty(userNameList)) {
            return ResultForFront.fail("后端listTeamAndUser出错");
        }
        return ResultForFront.succ(userNameList);
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
                                    @RequestParam("userRoleInTeam") String userRoleInTeam,
                                    @RequestParam("newUserName") String newUserName,
                                    @RequestParam("newUserPower") String newUserPower,
                                    @RequestParam("isMail") Boolean isMail) {

        //判断是否存在该用户
        QueryWrapper userQueryWrapper = new QueryWrapper();
        userQueryWrapper.eq("user_name", newUserName);
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


        UserTeamEntity userTeamEntity = new UserTeamEntity();
        userTeamEntity.setUserName(newUserName);
        userTeamEntity.setUserId(userBaseEntityTemp.getUserId());
        userTeamEntity.setTeamName(teamName);
        userTeamEntity.setTeamId(teamEntityTemp.getTeamId());
        userTeamEntity.setUserRoleInTeam(UserTeamRole.MEMBER.getUserRole());
        userTeamEntity.setUserPower(newUserPower);
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("user_name", newUserName);
        updateWrapper.eq("team_name", teamName);
        userTeamService.update(userTeamEntity, updateWrapper);

        if (isMail) {
            //todo: 发邮件
            log.info("此处发邮件");
        }


        return ResultForFront.succ("完成新增团队成员");
    }

    /**
     * 从指定团队中删除用户
     *
     * @param teamName
     * @param userName
     * @return
     */
    @ApiOperation(value = "从指定团队中删除用户", notes = "deleteMember")
    @GetMapping ("/delmember")
    public ResultForFront deleteMember(@RequestParam("teamName") String teamName,
                                       @RequestParam("userName") String userName,
                                       @RequestParam("delUserName") String delUserName) {
        //判断是否存在该用户
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name", delUserName);
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

        TeamEntity teamEntity = teamService.getTeamAndMemberUser(teamName, delUserName);
        List<UserBaseEntity> userBaseEntityList = teamEntity.getUserBaseEntityList();
        if (!userBaseEntityList.contains(userBaseEntityTemp)) {
            return ResultForFront.succ("此用户非团队成员不需删除");
        }

        //将其从团队成员的角色置为非团队成员角色即可，同时权限清空
        UserTeamEntity userTeamEntity = new UserTeamEntity();
        userTeamEntity.setUserName(delUserName);
        userTeamEntity.setUserId(userBaseEntityTemp.getUserId());
        userTeamEntity.setTeamName(teamName);
        userTeamEntity.setTeamId(teamEntityTemp.getTeamId());
        userTeamEntity.setUserRoleInTeam(UserTeamRole.GUEST.getUserRole());
        userTeamEntity.setUserPower(null);
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("user_name", delUserName);
        updateWrapper.eq("team_name", teamName);
        userTeamService.update(userTeamEntity, updateWrapper);

        return ResultForFront.succ("已从团队成员中剔除该用户");
    }

    /**
     * 权限管理
     *
     * @param teamName
     * @param userName
     * @param newUserPower
     * @return
     */
    @ApiOperation(value = "权限管理", notes = "updateMember")
    @GetMapping("/updatemember")
    public ResultForFront updateMember(@RequestParam("teamName") String teamName,
                                       @RequestParam("userName") String userName,
                                       @RequestParam("updateUserName") String updateUserName,
                                       @RequestParam("newUserPower") String newUserPower) {


        //判断是否存在该用户
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name", updateUserName);
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

        TeamEntity teamEntity = teamService.getTeamAndMemberUser(teamName, updateUserName);
        List<UserBaseEntity> userBaseEntityList = teamEntity.getUserBaseEntityList();
        if (!userBaseEntityList.contains(userBaseEntityTemp)) {
            return ResultForFront.succ("此用户非团队成员不无权限需要更新");
        }

        //将其从团队成员的角色置为非团队成员角色即可，同时权限清空
        UserTeamEntity userTeamEntity = new UserTeamEntity();
        userTeamEntity.setUserName(updateUserName);
        userTeamEntity.setUserId(userBaseEntityTemp.getUserId());
        userTeamEntity.setTeamName(teamName);
        userTeamEntity.setTeamId(teamEntityTemp.getTeamId());
        userTeamEntity.setUserRoleInTeam(UserTeamRole.MEMBER.getUserRole());
        userTeamEntity.setUserPower(newUserPower);
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("user_name", updateUserName);
        updateWrapper.eq("team_name", teamName);
        userTeamService.update(userTeamEntity, updateWrapper);

        return ResultForFront.succ("已更新" + updateUserName + "用户权限");
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
