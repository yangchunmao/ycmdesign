package com.haier.service;

import com.haier.domain.TaskInfo;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2018/1/29.
 */
@Service
public class TaskService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    public List<TaskInfo> taskList() {

        List<TaskInfo> list = new ArrayList<>();
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            for(String groupJob: scheduler.getJobGroupNames()){
               for (JobKey jobKey: scheduler.getJobKeys(GroupMatcher.groupEquals(groupJob))){

                    List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                    for(Trigger trigger: triggers){
                        Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                        JobDetail jobDetail = scheduler.getJobDetail(jobKey);

                        String cronExpression = "", createTime = "";
                        if(trigger instanceof CronTrigger){
                            CronTrigger cronTrigger = (CronTrigger) trigger;
                            cronExpression = cronTrigger.getCronExpression();
                            createTime = cronTrigger.getDescription();
                        }else if(trigger instanceof SimpleTrigger){
                            SimpleTrigger simpleTrigger = (SimpleTrigger) trigger;
                            cronExpression = simpleTrigger.getRepeatInterval() + "";
                            createTime = simpleTrigger.getDescription();
                        }
                        TaskInfo info = new TaskInfo();
                        info.setJobName(jobKey.getName());
                        info.setJobGroup(jobKey.getGroup());
                        info.setJobDescription(jobDetail.getDescription());
                        info.setJobStatus(triggerState.name());
                        info.setCronExpression(cronExpression);
                        info.setCreateTime(createTime);
                        list.add(info);
                    }
               }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return list;
    }
}
