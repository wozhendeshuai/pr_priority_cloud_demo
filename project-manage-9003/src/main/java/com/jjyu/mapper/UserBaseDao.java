package com.jjyu.mapper;

import com.jjyu.entity.UserBaseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author XJM
 * @title: UserBaseDao
 * @projectName pr_priority_cloud_demo
 * @description: TODO
 * @date 2022/1/1810:12
 */
@Mapper
public interface UserBaseDao {
    public UserBaseEntity selectUserByUserName(@Param("userName") String userName);
}
