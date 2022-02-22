package com.lsy.test.timerweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

/**
 * @author lsy
 */
@Configuration
public class QuartzConfiguration {

    /**
     * 创建job对象
     * @return
     */
    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean(){
        JobDetailFactoryBean factory=new JobDetailFactoryBean();
        return factory;
    }

    /**
     * 创建Trigger对象
     * @return
     */
    @Bean
    public SimpleTriggerFactoryBean simpleTriggerFactoryBean(){
        SimpleTriggerFactoryBean factory=new SimpleTriggerFactoryBean();
        factory.setJobDetail(jobDetailFactoryBean().getObject());
        return factory;
    }

    /**
     * 创建Cron Trigger
     * @return
     */
    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean(){
        CronTriggerFactoryBean factory=new CronTriggerFactoryBean();
        factory.setJobDetail(jobDetailFactoryBean().getObject());
        return factory;
    }
    @Bean
    public SchedulerFactoryBean schedulerFactory(){
        SchedulerFactoryBean bean=new SchedulerFactoryBean();
        return bean;
    }
}
