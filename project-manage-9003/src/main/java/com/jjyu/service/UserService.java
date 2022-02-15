package com.jjyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjyu.entity.RepoBaseEntity;
import com.jjyu.entity.UserBaseEntity;

import java.util.List;

public interface UserService extends IService<UserBaseEntity> {
    public boolean hasUserByUserName(String userName);

    /**
     * 获取从数据处理得到的用户列表，将其进行插入
     * @param userBaseEntityList
     * @return
     */
    boolean synUserList(List<UserBaseEntity> userBaseEntityList, Integer teamId, String teamName);


}
