package com.jjyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jjyu.entity.UserBaseEntity;
import com.jjyu.entity.UserTeamEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserTeamDao extends BaseMapper<UserTeamEntity> {

}
