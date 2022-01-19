package com.jjyu.service.impl;

import com.jjyu.service.DataService;
import com.jjyu.utils.GetPythonOutputThread;
import com.jjyu.utils.PythonFilePath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;

@Async("taskExecutor")
@Service
@Slf4j
public class DataServiceImpl implements DataService {
    @Override
    public void synData(String maxPRNum, String ownerName, String repoName) {
        String maxPRNumStr = maxPRNum.toString();
        //测试多种条件下不同的输出情况 !!!==加上参数u让脚本实时输出==!!!
        String args1 = "python  -u " + PythonFilePath.testPath + " " + maxPRNumStr + " " + ownerName + " " + repoName;

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

        }


       /* try {
            Process process = Runtime.getRuntime().exec(args1);
            // 防止缓冲区满, 导致卡住
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    String line;
                    try {
                        BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                        while ((line = stderr.readLine()) != null) {
                            log.info("stderr:" + line);
                        }
                    } catch (Exception e) {
                    }

                }
            }.start();


            new Thread() {
                @Override
                public void run() {
                    super.run();
                    String line;
                    try {
                        BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        while ((line = stdout.readLine()) != null) {
                            log.info("stdout:" + line);
                        }
                    } catch (Exception e) {

                    }
                }
            }.start();


            int exitVal = process.waitFor();
            if (0 != exitVal) {
                System.out.println("执行脚本失败");
            }
            System.out.println("执行脚本成功");
            int status = process.waitFor();
            System.out.println("Script exit code is:" + status);

//            Runtime runtime = Runtime.getRuntime();
//            Process pro = null;
//            ArrayList<String> cmd = new ArrayList<String>();
//            cmd.add("python");
//            cmd.add("-u"); //!!!==加上参数u让脚本实时输出==!!!
//            cmd.add("E:\\pythonProject\\nju_pr_project\\test\\java_python_test.py");
//            cmd.add("asdpoi123");
//            cmd.add("poi123");
//            cmd.add("123");
//            String[] tempArray=cmd.toArray(new String[0]);
//            pro = runtime.exec(tempArray);
            log.info("===================DataServiceImpl end");
        } */ catch (Exception e) {
            e.printStackTrace();
        }


    }

    public Thread printMessage(final InputStream out, final InputStream err) {
        Thread thread = new Thread(() -> {
            Reader outReader = new InputStreamReader(out);
            BufferedReader outBf = new BufferedReader(outReader);

            Reader errReader = new InputStreamReader(err);
            BufferedReader errBf = new BufferedReader(errReader);

            String outLine = null, errLine = null;
            try {
                while ((outLine = outBf.readLine()) != null || (errLine = errBf.readLine()) != null) {
                    if (outLine != null) {
                        //outLine = new SimpleFormatter("yyyy-MM-dd HH:mm:ss.S", new Date()) + ">>" + outLine + "\n";
                        System.out.print("[OUT]------>>" + outLine + "\n");
                    }

                    if (errLine != null) {
                        //  errLine = OmpUtils.dateAsString("yyyy-MM-dd HH:mm:ss.S", new Date()) + ">>" + errLine + "\n";
                        System.out.print("[ERR]------>>" + errLine + "\n");
                    }
                    outLine = errLine = null;
                }
                outBf.close();
                errBf.close();
            } catch (IOException e) {
                //logger.error("Error when read", e);
                try {
                    outBf.close();
                    errBf.close();
                } catch (IOException e1) {
                    //logger.error("Error when close", e1);
                }
            }
        });

        thread.start();

        return thread;
    }
}
