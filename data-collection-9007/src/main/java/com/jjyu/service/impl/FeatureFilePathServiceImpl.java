package com.jjyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjyu.entity.FeatureFilePathEntity;
import com.jjyu.mapper.FeatureFilePathMapper;
import com.jjyu.service.FeatureFilePathService;
import com.jjyu.utils.DateTimeUtil;
import com.jjyu.utils.GetPythonOutputThread;
import com.jjyu.utils.PythonFilePath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class FeatureFilePathServiceImpl extends ServiceImpl<FeatureFilePathMapper, FeatureFilePathEntity> implements FeatureFilePathService {

    @Resource
    private FeatureFilePathMapper featureFilePathMapper;

    @Override
    @Async("taskExecutor")
    public void createFeatureFile(String repoName, String fileToAlgName) {
        String feature_args = "";
        String open_feature_args = "";
        String today = DateTimeUtil.getDate();
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("repo_name",repoName);
        queryWrapper.eq("create_time",today);
        if (fileToAlgName.equals("bayesnet")) {
           // fileToAlgName = "rank_lib";
            //测试多种条件下不同的输出情况 !!!==加上参数u让脚本实时输出==!!!
            queryWrapper.eq("file_to_alg_name", fileToAlgName);

            feature_args = "python  -u " + PythonFilePath.bayesnet_feature_file_path + " " + repoName;
            open_feature_args = "python  -u " + PythonFilePath.bayesnet_open_feature_file_path + " " + repoName;
        } else {
            queryWrapper.eq("file_to_alg_name", "rank_lib");
            feature_args = "python  -u " + PythonFilePath.rank_lib_feature_file_path + " " + repoName;
            open_feature_args = "python  -u " + PythonFilePath.rank_lib_open_feature_file_path + " " + repoName;
        }
        featureFilePathMapper.delete(queryWrapper);
        //初始化PR特征文件
        log.info("===================createFeatureFile 现在的命令是feature_args：" + feature_args);
        try {
            //执行命令
            Process process = Runtime.getRuntime().exec(feature_args);
            Thread oThread = GetPythonOutputThread.printMessage(process.getInputStream(), process.getErrorStream());
//            oThread.start();
            oThread.join();
            int exitVal = process.waitFor();
            if (0 != exitVal) {
                log.info("===================执行feature_args失败：" + feature_args);
            }
            log.info("===================执行feature_args成功：" + feature_args);
            int status = process.waitFor();
            log.info("===================Script exit code is:" + status);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //执行初始化打开状态PR的特征文件
        log.info("===================createFeatureFile 现在的命令是open_feature_args：" + open_feature_args);
        try {
            //执行命令
            Process process = Runtime.getRuntime().exec(open_feature_args);
            Thread oThread = GetPythonOutputThread.printMessage(process.getInputStream(), process.getErrorStream());

            oThread.join();
            int exitVal = process.waitFor();
            if (0 != exitVal) {
                log.info("===================执行feature_args失败：" + open_feature_args);
            }
            log.info("===================执行feature_args成功：" + open_feature_args);
            int status = process.waitFor();
            log.info("===================Script exit code is:" + status);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
