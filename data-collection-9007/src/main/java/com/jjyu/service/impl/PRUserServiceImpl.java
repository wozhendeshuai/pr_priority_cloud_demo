package com.jjyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjyu.entity.PRSelfEntity;
import com.jjyu.entity.PRUserEntity;
import com.jjyu.mapper.PRSelfMapper;
import com.jjyu.mapper.PRUserMapper;
import com.jjyu.service.PRSelfService;
import com.jjyu.service.PRUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PRUserServiceImpl extends ServiceImpl<PRUserMapper, PRUserEntity> implements PRUserService {


}
