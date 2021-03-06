package com.jjyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jjyu.entity.RepoBaseEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RepoBaseDao extends BaseMapper<RepoBaseEntity> {
    public List<RepoBaseEntity> selectAllRepo();
}
