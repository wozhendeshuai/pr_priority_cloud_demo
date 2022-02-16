package com.jjyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjyu.entity.PRSelfEntity;
import com.jjyu.entity.SortResult;
import com.jjyu.utils.ResultForFront;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

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
    public List<PRSelfEntity> getPRDataFromDataCollection(String repoName);

    /**
     * 根据排序规则以及项目名称，对项目处于Open状态的进行排序
     *
     * @param repoName
     * @param sortRule
     * @return
     */
    List<SortResult> getPRDataBySortRule(String repoName, String sortRule);

    /**
     * 重新训练模型
     *
     * @param repoName
     * @param algName
     * @param algParam
     * @return
     */
    public Future<String> reTrainAlg(String repoName, String algName, String algParam);

    /**
     * 重新利用模型计算排序列表
     *
     * @param repoName
     * @param algName
     * @return
     */
    public List<SortResult> reCalResult(String repoName, String algName);
}
