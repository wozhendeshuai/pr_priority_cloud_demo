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
    boolean newPR(String userName, String repoName, String ownerName, String baseBranch, String compareBranch, String prTitle, String prContent);

    /**
     * 2.合入PR
     *
     * @param userName
     * @param prNumber
     * @param ownerName
     * @param repoName
     * @param commitTitle
     * @param commitMessage
     * @param mergeMethod
     * @return
     */
    boolean mergePR(String userName, Integer prNumber, String ownerName, String repoName, String commitTitle, String commitMessage, String mergeMethod);

    /**
     * 更新PR信息，哪个不空更新哪个
     *
     * @param userName
     * @param repoName
     * @param ownerName
     * @param prNumber
     * @param title
     * @param body
     * @param state
     * @return
     */
    boolean updatePR(String userName,
                     String repoName,
                     String ownerName,
                     Integer prNumber,
                     String title,
                     String body,
                     String state);


    //4.查看PR详情
    PRDetail prDetail(String prNumber, String repoName);

    //5.评论PR
    boolean commentPR(String userName, String prNumber, String repoName, String commentContent);

    //5.评审PR
    boolean reviewPR(String userName, String prNumber, String repoName, String reviewContent);

    List<String> listPRFileList(String repoName, String prNumber);

    PRFileDetail getPRFileDetail(String repoName, String prNumber, String fileName);
}
