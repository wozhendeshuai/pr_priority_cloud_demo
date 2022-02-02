package com.jjyu.utils;

import com.jjyu.entity.PRSelfEntity;

import java.util.List;

public interface SortRuleStrategy {
    /**
     * 定义排序策略接口
     *
     * @param prSelfEntityList
     * @return
     */
    public List<PRSelfEntity> sortByRule(List<PRSelfEntity> prSelfEntityList);
}
