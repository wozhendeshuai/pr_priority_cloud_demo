package com.jjyu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjyu.entity.SortResult;
import com.jjyu.mapper.SortResultMapper;
import com.jjyu.service.SortResultService;
import org.springframework.stereotype.Service;

@Service
public class SortResultServiceImpl extends ServiceImpl<SortResultMapper, SortResult> implements SortResultService {
}
