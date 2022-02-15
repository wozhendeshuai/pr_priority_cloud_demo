package com.jjyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjyu.entity.RepoBaseEntity;
import com.jjyu.entity.UserBaseEntity;
import com.jjyu.entity.UserTeamEntity;
import com.jjyu.mapper.RepoBaseDao;
import com.jjyu.mapper.UserBaseDao;
import com.jjyu.service.UserService;
import com.jjyu.service.UserTeamService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserBaseDao, UserBaseEntity> implements UserService {
    @Autowired
    private UserBaseDao userBaseDao;
    @Autowired
    private UserTeamService userTeamService;

    @Override
    public boolean hasUserByUserName(String userName) {
        if (userBaseDao.selectUserByUserName(userName) == null)
            return false;
        return true;
    }

    @Override
    public boolean synUserList(List<UserBaseEntity> userBaseEntityList, Integer teamId, String teamName) {
        for (UserBaseEntity userBaseEntity : userBaseEntityList) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("user_name", userBaseEntity.getUserName());
            UserBaseEntity temp = userBaseDao.selectOne(queryWrapper);
            //封装一下这个对象。使其无论如何都是最新的
            UserTeamEntity userTeam = new UserTeamEntity();
            userTeam.setTeamId(teamId);
            userTeam.setTeamName(teamName);
            userTeam.setUserId(userBaseEntity.getUserId());
            userTeam.setUserName(userBaseEntity.getUserName());
            userTeam.setUserRoleInTeam(userBaseEntity.getUserRoleInTeam());
            if (ObjectUtils.isEmpty(temp)) {
                userBaseEntity.setEmail(userBaseEntity.getUserName()+"@prTools.com");
                userBaseEntity.setPassword("12345678");
                userBaseDao.insert(userBaseEntity);
                userTeamService.save(userTeam);
            } else {
                UpdateWrapper updateWrapper = new UpdateWrapper();
                updateWrapper.eq("user_name", userBaseEntity.getUserName());
                //userBaseDao.update(userBaseEntity, updateWrapper);//此处不应更新用户基础信息，不然用户修改的密码等信息会丢失
                queryWrapper.eq("team_name", teamName);
                UserTeamEntity userTeamTemp = userTeamService.getOne(queryWrapper);
                if (ObjectUtils.isEmpty(userTeamTemp)) {
                    userTeamService.save(userTeam);
                } else {
                    updateWrapper.eq("team_name", teamName);
                    userTeamService.update(userTeam, updateWrapper);
                }
            }
        }
        return true;
    }
}
