package com.jjyu.service.impl;

import com.jjyu.dao.TeamBaseDao;
import com.jjyu.entity.TeamEntity;
import com.jjyu.service.TeamService;
import com.jjyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamBaseDao teamBaseDao;


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
        return false;
    }
}
