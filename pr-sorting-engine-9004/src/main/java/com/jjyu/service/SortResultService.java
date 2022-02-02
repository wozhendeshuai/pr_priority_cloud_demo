package com.jjyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjyu.entity.SortResult;

import java.util.List;
import java.util.Map;

public interface SortResultService extends IService<SortResult> {
    /**
     * 获取有序的PR排序列表
     *
     * @param repoName
     * @param dateTime
     * @param algName
     * @return
     */
    List<SortResult> getSortListByOrder(String repoName, String dateTime, String algName);

    /**
     * 从数据处理微服务获取PR数据
     *
     * @param repoName
     * @return
     */
    public List getPRDataFromDataCollection(String repoName);
}
