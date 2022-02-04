package com.jjyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jjyu.entity.RepoBaseEntity;
import com.jjyu.entity.RepoDayEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RepoDayDao extends BaseMapper<RepoDayEntity> {

}
