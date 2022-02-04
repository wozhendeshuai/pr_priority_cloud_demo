package com.jjyu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjyu.entity.UserBaseEntity;
import com.jjyu.entity.UserTeamEntity;
import com.jjyu.mapper.UserBaseDao;
import com.jjyu.mapper.UserTeamDao;
import com.jjyu.service.UserService;
import com.jjyu.service.UserTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamDao, UserTeamEntity> implements UserTeamService {

}
