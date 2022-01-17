package com.jjyu.service.impl;

import com.jjyu.dao.TeamBaseDao;
import com.jjyu.entity.TeamEntity;
import com.jjyu.service.TeamService;
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
}
