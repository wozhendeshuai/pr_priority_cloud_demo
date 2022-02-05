package com.jjyu.service;


import com.jjyu.entity.PRDetail;

public interface PRBaseService {
    //1.新建PR
    boolean newPR(String userName, String repoName);

    //2.合入PR
    boolean mergePR(String userName, String prNumber, String repoName);

    //3.关闭PR
    boolean closePR(String userName, String prNumber, String repoName);

    //4.查看PR详情
    PRDetail prDetail(String prNumber, String repoName);

    //5.评论PR
    boolean commentPR(String userName, String prNumber, String repoName, String commentContent);

    //5.评审PR
    boolean reviewPR(String userName, String prNumber, String repoName, String reviewContent);
}
