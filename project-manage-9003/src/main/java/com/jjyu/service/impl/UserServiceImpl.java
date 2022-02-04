package com.jjyu.service.impl;

import com.jjyu.mapper.UserBaseDao;
import com.jjyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserBaseDao userBaseDao;
    @Override
    public boolean hasUserByUserName(String userName) {
        if(userBaseDao.selectUserByUserName(userName)==null)
            return false;
        return true;
    }
}
