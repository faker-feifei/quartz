package com.itstyle.quartz.listener;

import com.alibaba.fastjson.JSON;
import com.dexcoder.dal.build.Criteria;
import com.dexcoder.dal.spring.JdbcDaoImpl;
import com.itstyle.quartz.comet4j.Comet;
import com.itstyle.quartz.comet4j.CometUtil;
import com.itstyle.quartz.config.ApplicationContextUtil;
import com.itstyle.quartz.entity.TaskFireLog;
import com.itstyle.quartz.util.MyDateUtils;
import com.itstyle.quartz.util.NativeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**.
 * @author 百果园
 * @date 2018端午节
 */

public class MyJobListener implements JobListener {
    private static Logger logger = LogManager.getLogger(MyJobListener.class);
    /** 通用dao  坑 这个是注入不进来的*/
   // @Autowired
   // private JdbcDaoImpl jdbcDao;



    private static String JOB_LOG;
    private static ExecutorService executorService;

    static {
        executorService = new ThreadPoolExecutor(2, 10, 5L, TimeUnit.SECONDS, new ArrayBlockingQueue(20), new ThreadPoolExecutor.DiscardOldestPolicy());
        JOB_LOG = "jobLog";
    }

    @Override
    public String getName() {
        return "taskListener";
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String targetObject = jobDataMap.getString("groupName");
        String targetMethod = jobDataMap.getString("taskName");
        logger.info("定时任务jobExecutionVetoed：{}.{}", targetObject, targetMethod);
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String targetObject = jobDataMap.getString("groupName");
        String targetMethod = jobDataMap.getString("taskName");
        if (logger.isInfoEnabled()) {
            logger.info("定时任务开始执行：{}.{}", targetObject, targetMethod);
        }

        TaskFireLog log = new TaskFireLog();
        log.setId(UUID.randomUUID().toString().replace("-", ""));
        log.setStartTime(context.getFireTime());
        log.setGroupName(targetObject);
        log.setTaskName(targetMethod);
        log.setStatus("I");
        log.setServerHost(NativeUtil.getHostName());
        log.setServerDuid(NativeUtil.getDUID());
        TaskFireLog taskFireLog = this.updateLog(log);
        jobDataMap.put(JOB_LOG, taskFireLog);
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException exp) {
        Timestamp end = new Timestamp(System.currentTimeMillis());
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String targetObject = jobDataMap.getString("groupName");
        String targetMethod = jobDataMap.getString("taskName");
        if (logger.isInfoEnabled()) {
            logger.info("定时任务执行结束：{}.{}", targetObject, targetMethod);
        }

        final TaskFireLog log = (TaskFireLog)jobDataMap.get(JOB_LOG);
        if (log != null) {
            log.setEndTime(end);
            if (exp != null) {
                logger.error("定时任务失败: [" + targetObject + "." + targetMethod + "]", exp);

                log.setStatus("E");
                log.setFireInfo(exp.getMessage());
            } else if (log.getStatus().equals("I")) {
                log.setStatus("S");
            }
        }

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                if (log != null) {
                    try {
                        MyJobListener.this.updateLog(log);

                        Comet comet = new Comet();
                        //comet.setUserId("1");

                        ArrayList<Map> list = new ArrayList<Map>();
                        HashMap<String, Object> hashMap = new HashMap<String, Object>(3);
                        hashMap.put("endTime", MyDateUtils.format(log.getEndTime(), MyDateUtils.DATE_PATTERN[9]));
                        hashMap.put("code", "S".equals(log.getStatus()) ? 1 : 0);
                        hashMap.put("text", "S".equals(log.getStatus()) ? "执行成功！" : "执行失败！");
                        hashMap.put("errorId", log.getId());

                        list.add(hashMap);
                        comet.setMsgData(list);
                        comet.setMsgCount(String.valueOf(list.size()));
                        MyJobListener.this.getCometUtil().pushToAll(comet);

                    } catch (Exception var2) {
                        MyJobListener.logger.error("Update TaskRunLog cause error. The log object is : " + JSON.toJSONString(log), var2);
                    }
                }

            }
        });
    }
    @Transactional
    public TaskFireLog updateLog(TaskFireLog record) {
        JdbcDaoImpl jdbcDao = this.getJdbcDao();

        if (record.getEndTime() == null) {

            jdbcDao.save(record);
        } else {
            Criteria criteria = Criteria.update(TaskFireLog.class).set("endTime", record.getEndTime()).set("status",record.getStatus()).set("fireInfo",record.getFireInfo())
                    .where("id", new String[]{record.getId()});
            jdbcDao.update(criteria);
        }

        return record;
    }

    private JdbcDaoImpl getJdbcDao() {
        return (JdbcDaoImpl) ApplicationContextUtil.getBean("jdbcDaoImpl");
    }
    private CometUtil getCometUtil() {
        return (CometUtil) ApplicationContextUtil.getBean("cometUtil");
    }


}
