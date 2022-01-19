package com.jjyu.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class GetPythonOutputThread {

    public static Thread printMessage(final InputStream out, final InputStream err) {
        Thread thread = new Thread(() -> {
            Reader outReader = new InputStreamReader(out);
            BufferedReader outBf = new BufferedReader(outReader);

            Reader errReader = new InputStreamReader(err);
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
