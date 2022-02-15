package com.jjyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjyu.entity.FeatureFilePathEntity;
import com.jjyu.entity.PRUserEntity;

public interface FeatureFilePathService extends IService<FeatureFilePathEntity> {

    boolean createFeatureFile(String repoName, String fileToAlgName);
}
