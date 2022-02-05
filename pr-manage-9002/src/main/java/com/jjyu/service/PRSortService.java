package com.jjyu.service;


import com.jjyu.entity.PRDetail;

import java.util.List;

public interface PRSortService {
    //1.获取某一规则的排序列表
    List listRule(String userName, String repoName);

    //2.获取某一算法的排序列表
    List listAlg(String userName, String prNumber, String repoName);

    //3.获取某一算法在测试集效果
    List listAlgEval(String userName, String prNumber, String repoName);

    //4.手动重新训练排序模型
    List reTrainModel(String prNumber, String repoName);

    //5.查看排序定时任务
    boolean getSortTimeTask(String userName, String prNumber, String repoName, String commentContent);

    //5.设置排序定时任务
    boolean setSortTimeTask(String userName, String prNumber, String repoName, String reviewContent);
}
