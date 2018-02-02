package com.haier.Scheduler;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by Admin on 2018/1/23.
 */
public class TestScheduler {


    @Test
    public void testS() throws SchedulerException {

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();



        scheduler.start();

        JobDetail job = newJob(MyJob.class)
                .withIdentity("job1", "group1")
                .usingJobData("jobSays", "Hello world!")
                .usingJobData("myFloatValue", 3.145f)
                .build();

        Trigger trigger = newTrigger().withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(10))
                .forJob(job)
                .build();

        scheduler.scheduleJob(job, trigger);


        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scheduler.shutdown();
    }

}
