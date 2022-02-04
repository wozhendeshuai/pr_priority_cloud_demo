package com.jjyu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjyu.entity.RepoBaseEntity;
import com.jjyu.entity.RepoDayEntity;
import com.jjyu.mapper.RepoBaseDao;
import com.jjyu.mapper.RepoDayDao;
import com.jjyu.service.RepoBaseService;
import com.jjyu.service.RepoDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepoDayServiceImpl extends ServiceImpl<RepoDayDao, RepoDayEntity> implements RepoDayService {

}
