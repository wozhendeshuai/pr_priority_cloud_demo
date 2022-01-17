package com.jjyu.dao;

import com.jjyu.entity.TeamEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamBaseDao {
    public List<TeamEntity> selectAllTeam();
}
