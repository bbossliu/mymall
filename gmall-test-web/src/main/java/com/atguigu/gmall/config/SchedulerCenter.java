package com.atguigu.gmall.config;

import com.atguigu.gmall.job.TestQuartzJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author dalaoban
 * @create 2020-04-05-20:44
 */
/*任务分配中心*/
@Configuration
public class SchedulerCenter {

    @Autowired
    private SpringJobFactory springJobFactory;



    @Bean(name = "schedulerFactoryBean")
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(springJobFactory);
        return schedulerFactoryBean;
    }

    /*配置各种各样的任务*/
    public void TestQuartzPrint(Scheduler scheduler, int i) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(TestQuartzJob.class).withIdentity("test"+i,"test"+i).build();
        /*定义cron*/
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/1 * * * * ? *");
        /*定义trigger*/
        CronTrigger trigger = TriggerBuilder.newTrigger().withSchedule(cronScheduleBuilder).withIdentity("trigger" + i, "group" + i).build();
        scheduler.scheduleJob(jobDetail,trigger);
    }


}
