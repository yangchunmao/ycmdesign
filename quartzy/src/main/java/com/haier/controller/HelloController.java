package com.haier.controller;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Admin on 2018/1/23.
 */
@RestController
public class HelloController {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    private JobDetailFactoryBean simpleJobDetail;
    @Autowired
    private SimpleTriggerFactoryBean simpleJobTrigger;

    @RequestMapping("/hello")
    public String name(){
        return "Hello Quartzy!";
    }


    @RequestMapping("/job")
    public String testJob() throws SchedulerException {
        schedulerFactoryBean.getScheduler().scheduleJob(simpleJobDetail.getObject(), simpleJobTrigger.getObject());
        return "success";
    }
}
