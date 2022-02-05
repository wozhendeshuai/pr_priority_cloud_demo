package com.jjyu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjyu.entity.PRFileEntity;
import com.jjyu.entity.PRUserEntity;
import com.jjyu.mapper.PRFileMapper;
import com.jjyu.mapper.PRUserMapper;
import com.jjyu.service.PRFileService;
import com.jjyu.service.PRUserService;
import org.springframework.stereotype.Service;

@Service
public class PRFileServiceImpl extends ServiceImpl<PRFileMapper, PRFileEntity> implements PRFileService {


}
