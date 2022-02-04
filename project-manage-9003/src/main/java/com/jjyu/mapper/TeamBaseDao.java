package com.jjyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jjyu.entity.TeamEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface TeamBaseDao extends BaseMapper<TeamEntity> {
    public List<TeamEntity> selectAllTeam();

    public List<TeamEntity> selectAllTeamAndUser();

    public TeamEntity selectTeamByTeamName(@Param("teamName") String teamName);

    public void addMember(@Param("teamId")int teamId, @Param("teamName")String teamName, @Param("userId")int userId, @Param("userName")String userName,
                        @Param("userRoleInTeam")String userRoleInTeam);

    public void deleteMember(@Param("teamName")String teamName, @Param("userName")String userName);

    public void updateMember(@Param("teamName")String teamName, @Param("userName")String userName, @Param("userRoleInTeam")String userRoleInTeam);
}
