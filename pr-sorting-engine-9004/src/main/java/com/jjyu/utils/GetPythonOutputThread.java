package com.jjyu.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class GetPythonOutputThread {

    public static Thread printMessage(final InputStream out, final InputStream err) {
        Thread thread = new Thread(() -> {
            Reader outReader = null;
            try {
                outReader = new InputStreamReader(out,"GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            BufferedReader outBf = new BufferedReader(outReader);

            Reader errReader = null;
            try {
                errReader = new InputStreamReader(err,"GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            BufferedReader errBf = new BufferedReader(errReader);

            String outLine = null, errLine = null;
            try {
                while ((outLine = outBf.readLine()) != null || (errLine = errBf.readLine()) != null) {
                    if (outLine != null) {
                        Date date = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
                        log.info(dateFormat.format(date) + "-----[OUT]------>>" + outLine + "\n");
                    }

                    if (errLine != null) {
                        Date date = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
                        log.info(dateFormat.format(date) + "-----[ERR]------>>" + errLine + "\n");
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
