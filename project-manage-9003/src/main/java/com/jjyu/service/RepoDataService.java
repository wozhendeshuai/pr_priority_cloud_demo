package com.jjyu.service;

import com.jjyu.entity.RepoBaseEntity;

/**
 * 该Service负责所有repo级别的数据获取
 */
public interface RepoDataService {
    /**
     * 从datacollection微服务获取对应的数据信息
     * @param repoName
     * @return
     */
    public RepoBaseEntity getRepoBaseDataFromDataCollection(String repoName);

    /**
     * 要求数据微服务去同步数据
     * @param repoName
     */
    void synAllData(String repoName,Integer maxPRNum);

    void addNewRepo(String repoName, String ownerName, Integer maxPRNum,String userName);
}
