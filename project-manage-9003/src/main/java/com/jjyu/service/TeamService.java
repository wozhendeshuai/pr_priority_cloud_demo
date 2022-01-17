package com.jjyu.service;

import com.jjyu.entity.TeamEntity;

import java.util.List;

public interface TeamService {
    /**
     * 获取所有团队不包括用户
     *
     * @return
     */
    public List<TeamEntity> getAllTeam();

    /**
     * 获取所有团队，包括用户
     *
     * @return
     */
    public List<TeamEntity> getAllTeamAndUser();

    /**
     * 根据用户名，和团队名判断是否有该用户，在这个团队中
     * @param userName
     * @return
     */
    boolean hasUserByUserName(String userName,String teamName);
}
