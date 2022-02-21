package com.jjyu.service;


import com.jjyu.entity.PRDetail;
import com.jjyu.entity.PRFileDetail;

import java.util.List;

public interface PRBaseService {
    /**
     * 获取所有PR的编号和标题
     *
     * @param repoName
     * @return
     */
    List<String> listPRNumberAndTitle(String repoName);

    /**
     * 获取某个PR的详细信息
     *
     * @param repoName
     * @param prNumber
     * @return
     */
    PRDetail getOnePRDetail(String repoName, Integer prNumber);

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

    List<String> listPRFileList(String repoName, String prNumber);

    PRFileDetail getPRFileDetail(String repoName, String prNumber,String fileName);
}
