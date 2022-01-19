package com.jjyu.service;

import java.io.IOException;

public interface AllDataService {
    /**
     * 根据这些调用python去获取数据到数据库中
     * @param maxPRNum
     * @param ownerName
     * @param repoName
     */
    public void synData(String maxPRNum,
                        String ownerName,
                        String repoName);
}
