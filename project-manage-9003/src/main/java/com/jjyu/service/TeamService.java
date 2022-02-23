package com.jjyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjyu.entity.RepoBaseEntity;
import com.jjyu.entity.TeamEntity;

import java.util.List;

public interface TeamService extends IService<TeamEntity> {
    /**
     * 获取所有团队不包括用户
     *
     * @return
     */
    List<TeamEntity> getAllTeam();

    /**
     * 获取所有团队，包括用户
     *
     * @return
     */
    List<TeamEntity> getAllTeamAndUser();

    /**
     * 根据用户名，和团队名判断是否有该用户，在这个团队中
     *
     * @param userName
     * @return
     */
    boolean hasUserByUserName(String userName, String teamName);

    /**
     * 向团队中增加用户
     */
    boolean addMember(String teamName, String userName, String userRoleInTeam);

    /**
     * 从团队中删除用户
     *
     * @param teamName
     * @param userName
     */
    boolean deleteMember(String teamName, String userName);

    /**
     * 权限管理
     *
     * @param teamName
     * @param userName
     * @param userRoleInTeam
     */
    boolean updateMember(String teamName, String userName, String userRoleInTeam);

    /**
     * 得到用户所在团队中所有的member成员
     *
     * @param teamName
     * @param userName
     * @return
     */
    TeamEntity getTeamAndMemberUser(String teamName, String userName);

    /**
     * 列举出不在团队中的用户名称列表
     * @param teamName
     * @param userName
     * @return
     */
    List<String> getNotTeamAndUser(String teamName, String userName);
}
