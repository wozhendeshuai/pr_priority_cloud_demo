package com.jjyu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjyu.entity.RepoDayEntity;
import com.jjyu.mapper.RepoBaseDao;
import com.jjyu.entity.RepoBaseEntity;
import com.jjyu.service.RepoBaseService;

import com.jjyu.utils.ResultForFront;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepoBaseServiceImpl extends ServiceImpl<RepoBaseDao, RepoBaseEntity> implements RepoBaseService {
    @Autowired
    private RepoBaseDao repoBaseDao;

    @Override
    public List<RepoBaseEntity> getAllRepo() {
        return repoBaseDao.selectAllRepo();
    }

    @Override
    public RepoDayEntity listRepoData(String repoName) {
        return null;
    }

    @Override
    public ResultForFront reSynRepoData(String repoName, String algName, String userName) {
        return null;
    }

    @Override
    public ResultForFront getRepoDataSynTask(String repoName) {
        return null;
    }

    @Override
    public ResultForFront setRepoDataSynTask(String repoName, String time, String userName) {
        return null;
    }
}
