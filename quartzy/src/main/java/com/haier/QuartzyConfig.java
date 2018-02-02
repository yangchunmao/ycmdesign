package com.haier;

import com.haier.spring.AutowiringSpringBeanJobFactory;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Admin on 2018/1/26.
 */
@Configuration
@ConditionalOnProperty(name = "quartz.enabled")
public class QuartzyConfig {

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext){
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }
    /**
     * @param jobFactory
     * @return
     * @throws IOException
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource, JobFactory jobFactory) throws IOException {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setOverwriteExistingJobs(true);
        factoryBean.setAutoStartup(true);

        factoryBean.setJobFactory(jobFactory);
        factoryBean.setDataSource(dataSource);
        factoryBean.setQuartzProperties(quartzProperties());
        // 添加所有的triggers
//        if(!BaseUtil.isObjectEmpty(listOfTrigger)){
//            factoryBean.setTriggers(listOfTrigger.toArray(new Trigger[listOfTrigger.size()]));
//        }
        return factoryBean;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean properties = new PropertiesFactoryBean();
        properties.setLocation(new ClassPathResource("/quartz.properties"));
        properties.afterPropertiesSet();
        return properties.getObject();
    }

    public static SimpleTriggerFactoryBean simpleTrigger(JobDetail jobDetail, long pollFrequencyMs){
        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        simpleTriggerFactoryBean.setJobDetail(jobDetail);
        simpleTriggerFactoryBean.setStartDelay(0L);
        simpleTriggerFactoryBean.setRepeatInterval(pollFrequencyMs);
        simpleTriggerFactoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        // 失火时, 忽略所有的触发器
        simpleTriggerFactoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
        return simpleTriggerFactoryBean;
    }

    public static CronTriggerFactoryBean cronTrigger(JobDetail jobDetail, String cronExpression){
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(jobDetail);
        cronTriggerFactoryBean.setCronExpression(cronExpression);
        cronTriggerFactoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        return cronTriggerFactoryBean;
    }

    public static JobDetailFactoryBean jobDetail(Class jobClazz, String name, String group){
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(jobClazz);
        jobDetailFactoryBean.setName(name);
        jobDetailFactoryBean.setGroup(group);
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }

}
