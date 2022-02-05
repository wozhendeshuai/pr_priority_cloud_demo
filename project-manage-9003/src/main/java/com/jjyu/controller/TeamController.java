package com.jjyu.controller;

import cn.hutool.core.map.MapUtil;
import com.jjyu.entity.TeamEntity;
import com.jjyu.service.TeamService;
import com.jjyu.service.UserService;
import com.jjyu.utils.ResultForFront;
import com.jjyu.utils.UserTeamRole;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
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
    @Resource
    private UserService userService;

    //@RequestParam("prId") String prId,@RequestParam("fileId") String fileId
    @GetMapping("/listteam")
    public ResultForFront listTeam() {
        log.info("=============listTeam");


        List<TeamEntity> teamEntityList = teamService.getAllTeam();
        for (TeamEntity temp : teamEntityList) {
            log.info(temp.toString());
        }


        return ResultForFront.succ(MapUtil.builder()
                .put("port", "当前的端口是：" + port)
                .put("teamEntityList", teamEntityList)
                .build());
    }

    //@RequestParam("prId") String prId,@RequestParam("fileId") String fileId
    @GetMapping("/listTeamAndUser")
    public ResultForFront listTeamAndUser() {
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
    @RequestMapping("/addmember")
    public ResultForFront addMember(@RequestParam("teamName") String teamName,
                                    @RequestParam("userName") String userName,
                                    @RequestParam("userRoleInTeam") String userRoleInTeam) {
        //调用枚举类判断是否有该角色
        if (!UserTeamRole.hasRole(userRoleInTeam)) {
            return ResultForFront.fail("角色不存在");
        }
        //判断是否存在该用户
        if (!userService.hasUserByUserName(userName)) {
            return ResultForFront.fail("用户不存在");
        }
        //判断该用户是否已在团队中。
        if (teamService.hasUserByUserName(userName, teamName)) {
            return ResultForFront.fail("用户已在团队");
        }

        teamService.addMember(teamName, userName, userRoleInTeam);

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
    @RequestMapping("/delmember")
    public ResultForFront deleteMember(@RequestParam("teamName") String teamName,
                                       @RequestParam("userName") String userName) {
        //判断是否存在该用户
        if (!userService.hasUserByUserName(userName)) {
            return ResultForFront.fail("用户不存在");
        }
        //判断该用户是否已在团队中。
        if (!teamService.hasUserByUserName(userName, teamName)) {
            return ResultForFront.fail("用户不在团队");
        }

        teamService.deleteMember(teamName, userName);

        List<TeamEntity> teamEntityList = teamService.getAllTeamAndUser();
        for (TeamEntity temp : teamEntityList) {
            log.info(temp.toString());
        }

        return ResultForFront.succ(MapUtil.builder()
                .put("status", true)
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
    @RequestMapping("/updatemember")
    public ResultForFront updateMember(@RequestParam("teamName") String teamName,
                                       @RequestParam("userName") String userName,
                                       @RequestParam("userRoleInTeam") String userRoleInTeam) {
        //判断是否存在该用户
        if (!userService.hasUserByUserName(userName)) {
            return ResultForFront.fail("用户不存在");
        }
        //判断该用户是否已在团队中。
        if (!teamService.hasUserByUserName(userName, teamName)) {
            return ResultForFront.fail("用户不在团队");
        }

        teamService.updateMember(teamName, userName, userRoleInTeam);
        List<TeamEntity> teamEntityList = teamService.getAllTeamAndUser();
        for (TeamEntity temp : teamEntityList) {
            log.info(temp.toString());
        }

        return ResultForFront.succ(MapUtil.builder()
                .put("status", true)
                .put("port", "当前的端口是：" + port)
                .put("teamEntityList", teamEntityList)
                .build());
    }
}
