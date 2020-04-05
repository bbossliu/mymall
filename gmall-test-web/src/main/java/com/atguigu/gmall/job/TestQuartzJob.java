package com.atguigu.gmall.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author dalaoban
 * @create 2020-04-05-20:59
 */
public class TestQuartzJob implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("我正执行定时任务");
    }
}
