package com.jjyu.controller;

import com.jjyu.entity.TeamEntity;
import com.jjyu.service.TeamService;
import com.jjyu.utils.ResultForFront;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

    //@RequestParam("prId") String prId,@RequestParam("fileId") String fileId
    @GetMapping("/listteam")
    public Map<String, Object> listTeam() {
        log.info("=============listTeam");


        List<TeamEntity> teamEntityList = teamService.getAllTeam();
        for (TeamEntity temp : teamEntityList) {
            log.info(temp.toString());
        }


        return ResultForFront.ok().put("status", true).put("port", "当前的端口是：" + port).put("tempList", teamEntityList);
    }
}
