package com.jjyu.service.impl;

import com.jjyu.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public boolean hasUserByUserName(String userName) {
        return false;
    }
}
