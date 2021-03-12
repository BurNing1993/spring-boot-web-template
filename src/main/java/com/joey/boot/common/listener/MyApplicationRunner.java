package com.joey.boot.common.listener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 应用启动做一个一次性事情
 * 创建静态文件目录
 * @author Joey
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {
    @Value("${app.static-location}")
    private String staticLocation;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        File dir = new File(staticLocation);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
