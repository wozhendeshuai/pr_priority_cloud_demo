package com.jjyu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jjyu.entity.RepoBaseEntity;
import com.jjyu.entity.UserBaseEntity;
import com.jjyu.entity.UserTeamEntity;
import com.jjyu.service.RepoBaseService;
import com.jjyu.service.UserService;
import com.jjyu.service.UserTeamService;
import com.jjyu.utils.ResultForFront;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(value = "project/user", tags = {"用户controller"})
@Slf4j
@RestController
@RequestMapping("project/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RepoBaseService repoBaseService;
    @Autowired
    private UserTeamService userTeamService;
    //1.用户基础信息的修改

    /**
     * 更新用户信息
     *
     * @return
     */
    @ApiOperation(value = "updateUser", notes = "updateUser")
    @PostMapping("/updateUser")
    public ResultForFront updateUser(UserBaseEntity userBaseEntity) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userBaseEntity.getUserId());
        UserBaseEntity userBaseEntityTemp = userService.getOne(queryWrapper);
        if (userBaseEntityTemp == null) {
            return ResultForFront.fail("无该用户");
        }
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("user_id", userBaseEntity.getUserId());

        boolean update = userService.update(userBaseEntity, updateWrapper);

        return ResultForFront.succ(update);
    }

    //1.1数据同步时新增用户，初始化相关数据
    @ApiOperation(value = "数据同步时新增用户，初始化相关数据", notes = "insertUser")
    @GetMapping("/insertUser")
    public ResultForFront insertUser(@RequestParam("repoName") String repoName,
                                     @RequestParam("userName") String userName) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name", userName);
        UserBaseEntity userBaseEntityTemp = userService.getOne(queryWrapper);
        if (userBaseEntityTemp == null) {
            return ResultForFront.fail("无该用户");
        }
        return ResultForFront.succ("");
    }

    //2.用户权限查询
    @ApiOperation(value = "userAuth", notes = "userAuth")
    @GetMapping("/userAuth")
    public ResultForFront userAuth(@RequestParam("repoName") String repoName,
                                   @RequestParam("userName") String userName) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name", userName);
        UserBaseEntity userBaseEntityTemp = userService.getOne(queryWrapper);
        if (userBaseEntityTemp == null) {
            return ResultForFront.fail("无该用户");
        }
        QueryWrapper repoQuery = new QueryWrapper();
        repoQuery.eq("repo_name", repoName);
        RepoBaseEntity repoBaseEntity = repoBaseService.getOne(repoQuery);

        QueryWrapper userTeamQuery = new QueryWrapper();
        userTeamQuery.eq("team_id", repoBaseEntity.getTeamId());
        userTeamQuery.eq("user_id", userBaseEntityTemp.getUserId());
        UserTeamEntity userTeamEntity = userTeamService.getOne(userTeamQuery);
        if (userTeamEntity == null) {
            return ResultForFront.fail("此用户无相关权限");
        }
        return ResultForFront.succ(userTeamEntity.getUserRoleInTeam());
    }
}
