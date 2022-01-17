package com.jjyu.dao;

import com.jjyu.entity.RepoBaseEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RepoBaseDao {
    public List<RepoBaseEntity> selectAllRepo();
}
