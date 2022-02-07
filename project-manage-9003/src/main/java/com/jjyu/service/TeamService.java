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

    /**
     * 向团队中增加用户
     */
    public boolean addMember(String teamName, String userName, String userRoleInTeam);

    /**
     *从团队中删除用户
     * @author XJM
     * @date 2022/1/18
     * @param teamName
     * @param userName
     * @return void
     */
    public boolean deleteMember(String teamName, String userName);

    /**
     *权限管理
     * @author XJM
     * @date 2022/1/19
     * @param teamName
     * @param userName
     * @param userRoleInTeam
     * @return void
     */
    public boolean updateMember(String teamName, String userName, String userRoleInTeam);
}
