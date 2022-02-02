package com.jjyu.utils.strategy;

import com.jjyu.entity.PRSelfEntity;
import com.jjyu.utils.SortRuleStrategy;

import java.util.List;

/**
 * 根据改变文档的大小来排序
 */
public class ChangeFileSort implements SortRuleStrategy {
    @Override
    public List<PRSelfEntity> sortByRule(List<PRSelfEntity> prSelfEntityList) {
        prSelfEntityList.sort((o1, o2) -> {
            if (o1.getChangedFileNum() > o2.getChangedFileNum()) {
                return -1;
            }
            return 1;
        });
        return prSelfEntityList;
    }
}
