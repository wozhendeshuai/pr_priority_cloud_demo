package com.jjyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjyu.entity.PRSelfEntity;

import java.util.List;

public interface PRSelfService extends IService<PRSelfEntity> {
    public List<PRSelfEntity> getAllOpenPRFromRepoName(String repoName);
}
