package com.atguigu.gmall.config;

import com.atguigu.gmall.job.AAScheduleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author dalaoban
 * @create 2020-04-05-20:45
 */
/*平常开发中有可能需要实现在项目启动后执行的功能*/
@Component
public class MyStartupRunner implements CommandLineRunner {

    /**/
    @Autowired
    AAScheduleUser scheduleUser;

    @Override
    public void run(String... args) throws Exception {
        scheduleUser.scheduleJobs();
        System.out.println(">>>>>>>>>>>>>>>定时任务开始执行<<<<<<<<<<<<<");
    }
}
