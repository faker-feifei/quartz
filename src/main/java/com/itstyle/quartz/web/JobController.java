package com.itstyle.quartz.web;

import com.itstyle.quartz.util.MyDateUtils;
import com.itstyle.quartz.util.ScheduleUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itstyle.quartz.entity.QuartzEntity;
import com.itstyle.quartz.entity.Result;
import com.itstyle.quartz.service.IJobService;
@Api(tags ="Quartz任务")
@RestController
@RequestMapping("/job")
public class JobController {
	private final static Logger LOGGER = LoggerFactory.getLogger(JobController.class);
	

    @Autowired @Qualifier("Scheduler")
    private Scheduler scheduler;
    @Autowired
    private IJobService jobService;
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value="新建任务")
	@PostMapping("/add")
	public Result save(QuartzEntity quartz){
		LOGGER.info("新增任务");
		try {
			//获取Scheduler实例、废弃、使用自动注入的scheduler、否则spring的service将无法注入
	        //Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
	        //如果是修改  展示旧的 任务
	        if(quartz.getOldJobGroup()!=null){
	        	JobKey key = new JobKey(quartz.getOldJobName(),quartz.getOldJobGroup());
	        	scheduler.deleteJob(key);
	        }
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
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error();
		}
		return Result.ok();
	}
	@ApiOperation(value="任务列表")
	@PostMapping("/list")
	public Result list(QuartzEntity quartz,Integer pageNo,Integer pageSize) throws SchedulerException {
		LOGGER.info("任务列表");
		List<QuartzEntity> list = jobService.listQuartzEntity(quartz, pageNo, pageSize);

		for(QuartzEntity quartzEntity : list){
			JobKey jobKey = ScheduleUtils.getJobKey(quartzEntity.getJobName(), quartzEntity.getJobGroup());
			JobDetail jobDetail = scheduler.getJobDetail(jobKey);


			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
			if (CollectionUtils.isEmpty(triggers)) {
				continue;
			}

			//这里一个任务可以有多个触发器， 但是我们一个任务对应一个触发器，所以只取第一个即可，清晰明了
			Trigger trigger = triggers.iterator().next();
/*			//quartzEntity.setJobTrigger(trigger.getKey().getName());

			Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			//quartzEntity.setStatus(triggerState.name());

			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				quartzEntity.setCronExpression(cronExpression);
			}
			*/
			quartzEntity.setPreviousFireTime(MyDateUtils.formatDateTime(trigger.getPreviousFireTime()));
			quartzEntity.setNextFireTime(MyDateUtils.formatDateTime(trigger.getNextFireTime()));

			quartzEntity.setJsonParam(jobDetail.getJobDataMap().get(QuartzEntity.JSON_PARAM_KEY) + "");
		}

		return Result.ok(list);
	}
	@ApiOperation(value="触发任务")
	@PostMapping("/trigger")
	public  Result trigger(QuartzEntity quartz,HttpServletResponse response) {
		try {
		     JobKey key = new JobKey(quartz.getJobName(),quartz.getJobGroup());
		     scheduler.triggerJob(key);
		} catch (SchedulerException e) {
			 e.printStackTrace();
			 return Result.error();
		}
		return Result.ok();
	}
	@PostMapping("/pause")
	public  Result pause(QuartzEntity quartz,HttpServletResponse response) {
		LOGGER.info("停止任务");
		try {
		     JobKey key = new JobKey(quartz.getJobName(),quartz.getJobGroup());
		     scheduler.pauseJob(key);
		} catch (SchedulerException e) {
			 e.printStackTrace();
			 return Result.error();
		}
		return Result.ok();
	}
	@PostMapping("/resume")
	public  Result resume(QuartzEntity quartz,HttpServletResponse response) {
		LOGGER.info("恢复任务");
		try {
		     JobKey key = new JobKey(quartz.getJobName(),quartz.getJobGroup());
		     scheduler.resumeJob(key);
		} catch (SchedulerException e) {
			 e.printStackTrace();
			 return Result.error();
		}
		return Result.ok();
	}
	@ApiOperation(value="移除任务")
	@PostMapping("/remove")
	public  Result remove(QuartzEntity quartz,HttpServletResponse response) {
		try {  
			  
            TriggerKey triggerKey = TriggerKey.triggerKey(quartz.getJobName(), quartz.getJobGroup());  
            // 停止触发器  
            scheduler.pauseTrigger(triggerKey);  
            // 移除触发器  
            scheduler.unscheduleJob(triggerKey);  
            // 删除任务  
            scheduler.deleteJob(JobKey.jobKey(quartz.getJobName(), quartz.getJobGroup()));  
            System.out.println("removeJob:"+JobKey.jobKey(quartz.getJobName()));  
        } catch (Exception e) {  
        	e.printStackTrace();
            return Result.error();
        }  
		return Result.ok();
	}
}
