package com.lsy.test.controller;

import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lsy
 */
@Component
public class JobScheduler {

    @Resource
    SchedulerFactoryBean schedulerFactoryBean;

    Scheduler scheduler;

    public void scheduleJobs() throws SchedulerException {
        scheduler = schedulerFactoryBean.getScheduler();
        start();
    }
    public void start() throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(DemoJob.class).withIdentity("", "").build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("");
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("", "").withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,trigger);
    }
}
