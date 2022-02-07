package com.jjyu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjyu.entity.UserOperationEntity;
import com.jjyu.mapper.UserOperationMapper;
import com.jjyu.service.UserOperationService;
import org.springframework.stereotype.Service;

@Service
public class UserOperationServiceImpl extends ServiceImpl<UserOperationMapper, UserOperationEntity> implements UserOperationService {

}
