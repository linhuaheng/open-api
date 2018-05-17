package com.wwwarehouse.xdw.openapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.URISyntaxException;

/**
 * 服务启动类
 * Created by zhigang.huang on 18/5/10.
 */
public class BizApplication {

    public static void main(String[] args) throws InterruptedException {
        setSysProp();

        Logger logger = LoggerFactory.getLogger(BizApplication.class);

        logger.info(">>>>> xdw-openapi-biz 正在启动 <<<<<");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
        logger.info(">>>>> xdw-openapi-biz 启动完成 <<<<<");

        Thread.sleep(Long.MAX_VALUE);
    }

    private static void setSysProp() {
        System.setProperty ("java.security.egd", "file:/dev/../dev/urandom");
        if (System.getProperty("workdir") == null) {
            String runningJarPath = getRootFolder();
            int lastIndexOf = runningJarPath.lastIndexOf("/", runningJarPath.length() - 2);
            // jar包上级目录
            String workdir = runningJarPath.substring(0, lastIndexOf);
            System.setProperty ("workdir", workdir);
        } else {
            String workdir = System.getProperty("workdir");
            if (workdir.endsWith("/")) {
                workdir = workdir.substring(0, workdir.length() -1);
                System.setProperty ("workdir", workdir);
            }
        }
        System.out.println("workdir:" + System.getProperty("workdir"));
    }

    private static String getRootFolder() {
        try {
            String runningJarPath = BizApplication.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().replaceAll("\\\\", "/");
            int last = runningJarPath.lastIndexOf("/target/");
            if (last > 0) {
                runningJarPath = runningJarPath.substring(0, last);
                return runningJarPath;
            }
            if (!runningJarPath.endsWith("/")) {
                int lastIndexOf = runningJarPath.lastIndexOf("/");
                runningJarPath = runningJarPath.substring(0, lastIndexOf + 1);
            }
            return runningJarPath;
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }
}
