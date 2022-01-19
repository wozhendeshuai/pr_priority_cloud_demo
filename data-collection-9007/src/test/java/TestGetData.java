import com.jjyu.utils.GetPythonOutputThread;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class TestGetData {
//
//    public static void main(String[] args) {
//        try {
//            //String a=getPara("car").substring(1),b="D34567",c="LJeff34",d="iqngfao";
//            //String[] args1=new String[]{ "python", "D:\\pyworkpeace\\9_30_1.py", a, b, c, d };
//            //Process pr=Runtime.getRuntime().exec(args1);
//            String url = "http";
//            String num = "asd123123";
//            int a = 1239999;
////            System.out.println("start;  \n" + url);
//            String args1 = "python -u E:\\pythonProject\\nju_pr_project\\test\\java_python_test.py" + " " + url + " " + num + " 123123";
//            System.out.println(args1);
//            String[] args2 = new String[]{"python", "E:/pythonProject/nju_pr_project/test/java_python_test.py", url, num, "1235555"};
//
//            /**
//             *   Process pr = Runtime.getRuntime().exec(args2);
//             * BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
//             *             String line;
//             *             while ((line = in.readLine()) != null) {
//             *                 System.out.println(line);
//             *             }
//             *             in.close();
//             pr.waitFor();
//             System.out.println("end ");
//             */
//
//            try {
//                Process pr = Runtime.getRuntime().exec(args2);
//                // 防止缓冲区满, 导致卡住
//                new Thread() {
//                    @Override
//                    public void run() {
//                        super.run();
//                        String line;
//                        try {
//                            BufferedReader stderr = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
//                            while ((line = stderr.readLine()) != null) {
//                                System.out.println("stderr:" + line);
//                            }
//                        } catch (Exception e) {
//
//                        }
//
//                    }
//                }.start();
//                new Thread() {
//                    @Override
//                    public void run() {
//                        super.run();
//                        String line;
//                        try {
//                            BufferedReader stdout = new BufferedReader(new InputStreamReader(pr.getInputStream()));
//                            while ((line = stdout.readLine()) != null) {
//                                System.out.println("stdout:" + line);
//                            }
//                        } catch (Exception e) {
//
//                        }
//                    }
//                }.start();
//                int exitVal = pr.waitFor();
//                if (0 != exitVal) {
//                    System.out.println("执行脚本失败");
//                }
//                System.out.println("执行脚本成功");
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void executeCMD(final String cmdStrArr) {
        Runtime rt = Runtime.getRuntime();
        System.out.println("开始执行脚本");
        System.out.println("脚本内容为:" + cmdStrArr);

        try {
            Process p = rt.exec(cmdStrArr);
            // 防止缓冲区满, 导致卡住
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    String line;
                    try {
                        BufferedReader stderr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                        while ((line = stderr.readLine()) != null) {
                            System.out.println("stderr:" + line);
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
                        BufferedReader stdout = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        while ((line = stdout.readLine()) != null) {
                            System.out.println("stdout:" + line);
                        }
                    } catch (Exception e) {

                    }
                }
            }.start();


            int exitVal = p.waitFor();
            if (0 != exitVal) {
                System.out.println("执行脚本失败");
            }
            System.out.println("执行脚本成功");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        Process pro = null;
        ArrayList<String> cmd = new ArrayList<String>();
        cmd.add("python");
        cmd.add("-u"); //!!!==加上参数u让脚本实时输出==!!!
        cmd.add("E:\\pythonProject\\nju_pr_project\\test\\java_python_test.py");
        cmd.add("asdpoi123");
        cmd.add("poi123");
        cmd.add("123");
        String[] tempArray=cmd.toArray(new String[0]);
        pro = runtime.exec(tempArray);

        Thread oThread = printMessage(pro.getInputStream(), pro.getErrorStream());
        oThread.join();
        int status = pro.waitFor();
        System.out.println("Script exit code is:" + status);
    }
    public static Thread printMessage(final InputStream out, final InputStream err) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                Reader outReader = new InputStreamReader(out);
                BufferedReader outBf = new BufferedReader(outReader);

                Reader errReader = new InputStreamReader(err);
                BufferedReader errBf = new BufferedReader(errReader);

                String outLine = null, errLine = null;
                try {
                    while ((outLine = outBf.readLine()) != null || (errLine = errBf.readLine()) != null) {
                        if (outLine != null) {
                            //outLine = new SimpleFormatter("yyyy-MM-dd HH:mm:ss.S", new Date()) + ">>" + outLine + "\n";
                            System.out.print("[OUT]------>>" + outLine+"\n");
                        }

                        if (errLine != null) {
                            //  errLine = OmpUtils.dateAsString("yyyy-MM-dd HH:mm:ss.S", new Date()) + ">>" + errLine + "\n";
                            System.out.print("[ERR]------>>" + errLine+"\n");
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
            }
        });

        thread.start();

        return thread;
    }



}
