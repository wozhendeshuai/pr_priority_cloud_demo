package com.jjyu.service.impl;

import com.jjyu.dao.RepoBaseDao;
import com.jjyu.entity.RepoBaseEntity;
import com.jjyu.service.RepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RepoServiceImpl implements RepoService {
    @Autowired
    private RepoBaseDao repoBaseDao;
    @Override
    public List<RepoBaseEntity> getAllRepo() {
        return repoBaseDao.selectAllRepo();
    }
}
