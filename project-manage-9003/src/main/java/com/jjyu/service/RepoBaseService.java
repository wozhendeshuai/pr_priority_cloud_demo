package com.jjyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjyu.entity.RepoBaseEntity;

import java.util.List;

public interface RepoBaseService extends IService<RepoBaseEntity> {
    public List<RepoBaseEntity> getAllRepo();
}
