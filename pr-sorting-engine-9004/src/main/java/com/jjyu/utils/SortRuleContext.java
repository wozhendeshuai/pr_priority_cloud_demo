package com.jjyu.utils;

import com.jjyu.entity.PRSelfEntity;

import java.util.List;

public class SortRuleContext {
    SortRuleStrategy sortRuleStrategy;

    public  SortRuleContext(SortRuleStrategy sortRuleStrategy) {
        this.sortRuleStrategy = sortRuleStrategy;
    }

    public List<PRSelfEntity> executeStrategy(List<PRSelfEntity> prSelfEntityList) {
        return sortRuleStrategy.sortByRule(prSelfEntityList);
    }
}
