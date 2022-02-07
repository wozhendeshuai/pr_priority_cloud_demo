package com.jjyu.service;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjyu.entity.PRTask;
import com.jjyu.entity.RepoBaseEntity;
import com.jjyu.entity.RepoDayEntity;
import com.jjyu.utils.ResultForFront;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface RepoBaseService extends IService<RepoBaseEntity> {
    public List<RepoBaseEntity> getAllRepo();

    //@RequestParam("prId") String prId,@RequestParam("fileId") String fileId
    @GetMapping("/listRepoData")
    public RepoDayEntity listRepoData(String repoName);


    /**
     * 手动同步项目以及项目所有数据
     *
     * @param repoName
     * @param algName
     * @param userName
     * @return
     */
    public Boolean reSynRepoData(String repoName, String algName, String userName);

    /**
     * 查看自动数据同步相关参数
     *
     * @param repoName
     * @return
     */
    public PRTask getRepoDataSynTask(String repoName);

    /**
     * 设置自动数据同步相关参数
     *
     * @return
     */
    public Boolean setRepoDataSynTask(PRTask prTaske);
}
