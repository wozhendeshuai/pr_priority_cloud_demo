package com.jjyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jjyu.entity.UserBaseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserBaseDao extends BaseMapper<UserBaseEntity> {
    public UserBaseEntity selectUserByUserName(@Param("userName") String userName);
}
