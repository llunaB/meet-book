package com.ssafy.config.job;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;

@Configuration
public class JobConfig {

    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void start(){

        JobDetail jobDetailA = buildJobDetail(JobA.class, new HashMap());


        try{
            scheduler.scheduleJob(jobDetailA, buildJobTrigger("0 0 0/1 * * ?"));

        } catch(SchedulerException e){
            e.printStackTrace();
        }
    }

    public Trigger buildJobTrigger(String scheduleExp){
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)).build();
    }

    public JobDetail buildJobDetail(Class job, Map params){
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);

        return newJob(job).usingJobData(jobDataMap).build();
    }
}