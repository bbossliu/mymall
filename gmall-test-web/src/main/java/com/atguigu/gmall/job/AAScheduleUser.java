package com.atguigu.gmall.job;

import com.atguigu.gmall.config.SchedulerCenter;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author dalaoban
 * @create 2020-04-05-20:48
 */
@Component
public class AAScheduleUser {

    @Autowired
    SchedulerFactoryBean scheduler;

    /*配置任务中心*/
    @Autowired
    SchedulerCenter schedulerCenter;

    /*执行定时任务，*/
    public void scheduleJobs() throws SchedulerException {
        Scheduler scheduler = this.scheduler.getScheduler();

        schedulerCenter.TestQuartzPrint(scheduler,1);
    }

}
