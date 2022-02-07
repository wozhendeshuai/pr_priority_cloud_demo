package com.jjyu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjyu.entity.RepoBaseEntity;
import com.jjyu.mapper.RepoBaseDao;
import com.jjyu.mapper.TeamBaseDao;
import com.jjyu.mapper.UserBaseDao;
import com.jjyu.entity.TeamEntity;
import com.jjyu.entity.UserBaseEntity;
import com.jjyu.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl extends ServiceImpl<TeamBaseDao, TeamEntity> implements TeamService {
    @Autowired
    private TeamBaseDao teamBaseDao;
    @Autowired
    private UserBaseDao userBaseDao;

    @Override
    public List<TeamEntity> getAllTeam() {
        return teamBaseDao.selectAllTeam();
    }

    @Override
    public List<TeamEntity> getAllTeamAndUser() {
        return teamBaseDao.selectAllTeamAndUser();
    }

    @Override
    public boolean hasUserByUserName(String userName, String teamName) {
        List<TeamEntity> allTeamAndUser = getAllTeamAndUser();
        for (TeamEntity te : allTeamAndUser) {
            if (te.getTeamName().equals(teamName)) {
                for (UserBaseEntity ube : te.getUserBaseEntityList()) {
                    if (ube.getUserName().equals(userName))
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean addMember(String teamName, String userName, String userRoleInTeam) {
        int userID = userBaseDao.selectUserByUserName(userName).getUserId();
        int teamId = teamBaseDao.selectTeamByTeamName(teamName).getTeamId();
        System.out.println(teamId + " " + teamName + " " + userID + " " + userName + " " + userRoleInTeam);
        teamBaseDao.addMember(teamId, teamName, userID, userName, userRoleInTeam);
        return true;
    }

    @Override
    public boolean deleteMember(String teamName, String userName) {
        teamBaseDao.deleteMember(teamName, userName);
        return true;
    }

    @Override
    public boolean updateMember(String teamName, String userName, String userRoleInTeam) {
        teamBaseDao.updateMember(teamName, userName, userRoleInTeam);
        return true;
    }
}
