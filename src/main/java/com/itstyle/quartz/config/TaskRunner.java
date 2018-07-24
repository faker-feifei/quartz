package com.itstyle.quartz.config;

import com.itstyle.quartz.listener.MyJobListener;
import org.quartz.*;
import org.quartz.impl.matchers.EverythingMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.itstyle.quartz.entity.QuartzEntity;
import com.itstyle.quartz.service.IJobService;
/**
 * 初始化一个测试Demo任务
 * 创建者 百果园
 * 创建时间	2018年端午节
 */
@Component
public class TaskRunner implements ApplicationRunner{
    
	private final static Logger LOGGER = LoggerFactory.getLogger(TaskRunner.class);
	
	@Autowired
    private IJobService jobService;
	@Autowired @Qualifier("Scheduler")
    private Scheduler scheduler;
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public void run(ApplicationArguments var) throws Exception{
    	Long count = jobService.listQuartzEntity(null);
    	if(count==0){
    		LOGGER.info("初始化测试任务");
    		QuartzEntity quartz = new QuartzEntity();
    		quartz.setJobName("test01");
    		quartz.setJobGroup("test");
    		quartz.setDescription("测试任务");
    		quartz.setJobClassName("com.itstyle.quartz.job.ChickenJob");
    		quartz.setCronExpression("0/20 * * * * ?");
   	        Class cls = Class.forName(quartz.getJobClassName()) ;
   	        cls.newInstance();
   	        //构建job信息
   	        JobDetail job = JobBuilder.newJob(cls).withIdentity(quartz.getJobName(),
   	        		quartz.getJobGroup())
   	        		.withDescription(quartz.getDescription()).build();


			JobDataMap jobDataMap = job.getJobDataMap();
			jobDataMap.put(QuartzEntity.JSON_PARAM_KEY, quartz.getJsonParam());

			jobDataMap.put("groupName", quartz.getJobGroup());
			jobDataMap.put("taskName", quartz.getJobName());
			jobDataMap.put("taskType", "sysn");
   	        // 触发时间点
   	        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(quartz.getCronExpression());
   	        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger"+quartz.getJobName(), quartz.getJobGroup())
   	                .startNow().withSchedule(cronScheduleBuilder).build();	
   	        //交由Scheduler安排触发
   	        scheduler.scheduleJob(job, trigger);
    	}

		scheduler.getListenerManager().addJobListener(new MyJobListener(), (Matcher)EverythingMatcher.allJobs());
    }

}