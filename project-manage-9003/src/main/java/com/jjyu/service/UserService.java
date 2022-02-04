package com.jjyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjyu.entity.RepoBaseEntity;
import com.jjyu.entity.UserBaseEntity;

public interface UserService extends IService<UserBaseEntity> {
    public boolean hasUserByUserName(String userName);
}
