package com.jjyu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjyu.entity.FeatureFilePathEntity;
import com.jjyu.mapper.FeatureFilePathMapper;
import com.jjyu.service.FeatureFilePathService;
import com.jjyu.utils.GetPythonOutputThread;
import com.jjyu.utils.PythonFilePath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FeatureFilePathServiceImpl extends ServiceImpl<FeatureFilePathMapper, FeatureFilePathEntity> implements FeatureFilePathService {


    @Override
    public boolean createFeatureFile(String repoName, String fileToAlgName) {
        String feature_args = "";
        String open_feature_args = "";
        if (fileToAlgName.equals("bayesnet")) {
           // fileToAlgName = "rank_lib";
            //测试多种条件下不同的输出情况 !!!==加上参数u让脚本实时输出==!!!
            feature_args = "python  -u " + PythonFilePath.bayesnet_feature_file_path + " " + repoName;
            open_feature_args = "python  -u " + PythonFilePath.bayesnet_open_feature_file_path + " " + repoName;
        } else {
            feature_args = "python  -u " + PythonFilePath.rank_lib_feature_file_path + " " + repoName;
            open_feature_args = "python  -u " + PythonFilePath.rank_lib_open_feature_file_path + " " + repoName;
        }
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
        return true;

    }
}
