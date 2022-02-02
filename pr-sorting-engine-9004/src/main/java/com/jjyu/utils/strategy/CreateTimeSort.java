package com.jjyu.utils.strategy;

import com.jjyu.entity.PRSelfEntity;
import com.jjyu.utils.SortRuleStrategy;

import java.util.Comparator;
import java.util.List;

/**
 * 根据创建时间先后进行排序
 */
public class CreateTimeSort implements SortRuleStrategy {
    @Override
    public List<PRSelfEntity> sortByRule(List<PRSelfEntity> prSelfEntityList) {
        prSelfEntityList.sort((o1, o2) -> {
            if (o1.getCreatedAt().compareTo(o2.getCreatedAt()) >= 0) {
                return 1;
            }
            return -1;
        });
        return prSelfEntityList;
    }
}
