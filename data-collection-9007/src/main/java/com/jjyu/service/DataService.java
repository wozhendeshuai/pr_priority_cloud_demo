package com.jjyu.service;

import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

public interface DataService {
    /**
     * 根据这些调用python去获取数据到数据库中
     * @param maxPRNum
     * @param ownerName
     * @param repoName
     */
    public void synData(String maxPRNum,
                        String ownerName,
                        String repoName) throws IOException, InterruptedException;
}
