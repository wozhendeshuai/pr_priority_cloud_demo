package com.jjyu.service.impl;

import com.jjyu.service.AllDataService;
import com.jjyu.service.DataService;
import com.jjyu.utils.GetPythonOutputThread;
import com.jjyu.utils.PythonFilePath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;

@Async("taskExecutor")
@Service
@Slf4j
public class AllDataServiceImpl implements AllDataService {
    @Override
    public void synData(String maxPRNum, String ownerName, String repoName) {
        String maxPRNumStr = maxPRNum.toString();
        //测试多种条件下不同的输出情况 !!!==加上参数u让脚本实时输出==!!!
        String args1 = "python  -u " + PythonFilePath.get_data_step_process_for_java + " " + maxPRNumStr + " " + ownerName + " " + repoName;

        log.info("===================DataServiceImpl 现在的命令是args1：" + args1);
        try {
            //执行命令
            Process process = Runtime.getRuntime().exec(args1);
            Thread oThread = GetPythonOutputThread.printMessage(process.getInputStream(), process.getErrorStream());
//            oThread.start();
            oThread.join();
            int exitVal = process.waitFor();
            if (0 != exitVal) {
                log.info("===================执行脚本失败");
            }
            log.info("===================执行脚本成功");
            int status = process.waitFor();
            log.info("===================Script exit code is:" + status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
