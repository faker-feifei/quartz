package com.itstyle.quartz.factory;

import com.itstyle.quartz.model.ScheduleJob;
import com.itstyle.quartz.vo.ScheduleJobVo;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 任务工厂类, 中断job
 *
 * User: 百果园
 * Date: 20180615
 * Time: 上午10:11
 */
@DisallowConcurrentExecution
public class JobInterruptFactory implements InterruptableJob {

    /* 日志对象 */
    private static final Logger _log = LoggerFactory.getLogger(JobInterruptFactory.class);
    // logging services
   //private static Logger _log = LoggerFactory.getLogger(DumbInterruptableJob.class);

    // has the job been interrupted?
    private boolean _interrupted = false;

    // job name
    private JobKey _jobKey = null;

    private static int counts = 0;

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        try {
        _jobKey = context.getJobDetail().getKey();
        _log.error("任务Key: " + _jobKey + " 执行时间: " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));


            for (int i = 0; i < 4; i++) {
                try {
                    Thread.sleep(1000L);
                } catch (Exception ignore) {
                    ignore.printStackTrace();
                }

                if(_interrupted) {
                    _log.error("被外界因素停止了这个任务key: " + _jobKey + ",当前执行次数: " + counts);
                    return; // could also choose to throw a JobExecutionException
                    // if that made for sense based on the particular
                    // job's responsibilities/behaviors
                }
                //执行业务方法
                userManager();
            }

        } finally {
            _log.error("任务执行完成key: " + _jobKey + " 执行时间: " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        }
    }

    private void userManager() {
        _log.error("正在执行插入数据库的操作,次数: " + (++counts));
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        _log.info("外界正在调用调度器停止这个任务key: " + _jobKey);
        _interrupted = true;
    }
}
