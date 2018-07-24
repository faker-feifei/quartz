package com.itstyle.quartz.job;

import com.itstyle.quartz.entity.QuartzEntity;
import com.itstyle.quartz.util.MyDateUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;

/**
 * Job 的实例要到该执行它们的时候才会实例化出来。每次 Job 被执行，一个新的 Job 实例会被创建。
 * 其中暗含的意思就是你的 Job 不必担心线程安全性，因为同一时刻仅有一个线程去执行给定 Job 类的实例，甚至是并发执行同一 Job 也是如此。
 */
public class LolJob implements  Job,Serializable {
    
	private static final Logger logger = LoggerFactory.getLogger(LolJob.class);
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		 System.out.println("人在塔在-02-测试集群模式");
		
		 System.out.println("Hello!  NewJob is executing." + MyDateUtils.formatDateTime(new Date()) );
		 //取得job详情  
         JobDetail jobDetail = context.getJobDetail();
        try {
            logger.info("运行15秒: ");
            Thread.sleep(1000 * 15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         // 取得job名称  
         String jobName = jobDetail.getClass().getName();  
         logger.info("Name: " + jobDetail.getClass().getSimpleName());     
         //取得job的类  
         logger.info("Job Class: " + jobDetail.getJobClass());     
         //取得job开始时间  
         logger.info(jobName + " fired at " + MyDateUtils.formatDateTime(context.getFireTime()));
         //取得job下次触发时间  
         logger.info("Next fire time " + MyDateUtils.formatDateTime(context.getNextFireTime()));
         
         JobDataMap dataMap =  jobDetail.getJobDataMap();
        String jsonParam = dataMap.getString(QuartzEntity.JSON_PARAM_KEY);
        logger.info("jsonParam: " + jsonParam);
        //通过传参来控制 运行时间

	}
}
