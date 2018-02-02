package com.haier.Scheduler;

import org.quartz.*;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Admin on 2018/1/23.
 */
@DisallowConcurrentExecution
public class MyJob implements Job {

    String jobSays;
    float myFloatValue;
    ArrayList state = new ArrayList();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobKey key = jobExecutionContext.getJobDetail().getKey();
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        state.add(new Date());
        System.err.println("Hello World!  MyJob is executing.Instance "+ key + " of DumbJob Says: " +jobSays + ", and Val is: "+ myFloatValue );
    }

    public void setJobSays(String jobSays) {
        this.jobSays = jobSays;
    }

    public void setMyFloatValue(float myFloatValue) {
        this.myFloatValue = myFloatValue;
    }

    public void setState(ArrayList state) {
        this.state = state;
    }
}
