package com.jjyu.service.impl;

import com.jjyu.dao.TeamBaseDao;
import com.jjyu.dao.UserBaseDao;
import com.jjyu.entity.TeamEntity;
import com.jjyu.entity.UserBaseEntity;
import com.jjyu.service.TeamService;
import com.jjyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
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
        for(TeamEntity te:allTeamAndUser){
            if(te.getTeamName().equals(teamName)){
                for(UserBaseEntity ube:te.getUserBaseEntityList()){
                    if(ube.getUserName().equals(userName))
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public void addUser(String teamName, String userName, String userRoleInTeam) {
        int userID = userBaseDao.selectUserByUserName(userName).getUserId();
        int teamId = teamBaseDao.selectTeamByTeamName(teamName).getTeamId();
        System.out.println(teamId+" "+teamName+" "+userID+" "+userName+" "+userRoleInTeam);
        teamBaseDao.addUser(teamId, teamName, userID, userName, userRoleInTeam);
    }
}
