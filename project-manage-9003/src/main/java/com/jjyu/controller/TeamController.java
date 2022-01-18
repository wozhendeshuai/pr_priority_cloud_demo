package com.jjyu.controller;

import com.jjyu.entity.TeamEntity;
import com.jjyu.service.TeamService;
import com.jjyu.service.UserService;
import com.jjyu.utils.ResultForFront;
import com.jjyu.utils.UserTeamRole;
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

@RestController
@RequestMapping("team")
@Slf4j
public class TeamController {

    @Value("${server.port}")
    private int port;
    @Autowired
    private TeamService teamService;
    @Resource
    private UserService userService;

    //@RequestParam("prId") String prId,@RequestParam("fileId") String fileId
    @GetMapping("/listteam")
    public Map<String, Object> listTeam() {
        log.info("=============listTeam");


        List<TeamEntity> teamEntityList = teamService.getAllTeam();
        for (TeamEntity temp : teamEntityList) {
            log.info(temp.toString());
        }


        return ResultForFront.ok().put("status", true).put("port", "当前的端口是：" + port).put("teamEntityList", teamEntityList);
    }

    //@RequestParam("prId") String prId,@RequestParam("fileId") String fileId
    @GetMapping("/listTeamAndUser")
    public Map<String, Object> listTeamAndUser() {
        log.info("=============listTeam");

        List<TeamEntity> teamEntityList = teamService.getAllTeamAndUser();
        for (TeamEntity temp : teamEntityList) {
            log.info(temp.toString());
        }

        return ResultForFront.ok().put("status", true).put("port", "当前的端口是：" + port).put("teamEntityList", teamEntityList);
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
    @RequestMapping("/addmember/{teamName}/{userName}/{userRoleInTeam}")
    public Map<String, Object> listTeam(@PathVariable("teamName") String teamName,
                                        @PathVariable("userName") String userName,
                                        @PathVariable("userRoleInTeam") String userRoleInTeam) {
        log.info("=============listTeam");
        //调用枚举类判断是否有该角色
        if (!UserTeamRole.hasRole(userRoleInTeam)) {
            return ResultForFront.error(200, "角色不存在");
        }
        //判断是否存在该用户
        if (!userService.hasUserByUserName(userName)) {
            return ResultForFront.error(200, "用户不存在");
        }
        //判断该用户是否已在团队中。
        if (teamService.hasUserByUserName(userName, teamName)) {
            return ResultForFront.error(200, "用户已在团队");
        }

        teamService.addUser(teamName, userName, userRoleInTeam);

        List<TeamEntity> teamEntityList = teamService.getAllTeam();
        for (TeamEntity temp : teamEntityList) {
            log.info(temp.toString());
        }

        //todo: 发邮件


        return ResultForFront.ok().put("status", true).put("port", "当前的端口是：" + port).put("teamEntityList", teamEntityList);
    }
}
