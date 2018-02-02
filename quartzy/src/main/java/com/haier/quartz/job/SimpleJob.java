package com.haier.quartz.job;

import com.haier.QuartzyConfig;
import com.haier.service.SimpleService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * Created by Admin on 2018/1/26.
 */
@Component
@DisallowConcurrentExecution
public class SimpleJob implements Job {

    @Autowired
    private SimpleService simpleService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        simpleService.say();
    }

    @Bean(name = "simpleJobDetail")
    public JobDetailFactoryBean simpleJob(){
        return QuartzyConfig.jobDetail(this.getClass(),"simpleJobDetail","test");
    }

    @Bean(name = "simpleJobTrigger")
    public SimpleTriggerFactoryBean simpleJobTrigger(@Qualifier("simpleJobDetail") JobDetail jobDetail){
        return QuartzyConfig.simpleTrigger(jobDetail, 10000);
    }

}
