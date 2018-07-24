package com.itstyle.quartz.web;


import com.dexcoder.dal.build.Criteria;
import com.dexcoder.dal.spring.JdbcDaoImpl;
import com.dexcoder.dal.spring.page.PageControl;
import com.itstyle.quartz.entity.Result;
import com.itstyle.quartz.entity.TaskFireLog;
import com.itstyle.quartz.vo.TaskFireLogVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/jobLog")
public class JobLogController {
	private final static Logger LOGGER = LoggerFactory.getLogger(JobLogController.class);

    @Autowired
    private JdbcDaoImpl jdbcDao;


	@PostMapping("/list")
	public Result list(TaskFireLogVo taskFireLogVo)  {
		LOGGER.info("任务log列表");
		PageControl.performPage(taskFireLogVo);
		Criteria criteria = Criteria.select(TaskFireLog.class)
				.desc("startTime");
		if(!StringUtils.isEmpty(taskFireLogVo.getTaskName()) ){
			criteria.where("taskName", new String[]{taskFireLogVo.getTaskName()});
			criteria.or("id", new String[]{taskFireLogVo.getId()});
		}
		//直接传入页码和每页条数
		//PageControl.performPage(1, 10);
		List<TaskFireLog> taskFireLogList = jdbcDao.queryList(criteria);
		//Pager pager = PageControl.getPager();
		//List<TaskFireLog> taskFireLogList = pager.getList(TaskFireLog.class);
		List<TaskFireLogVo> taskFireLogVoList = new ArrayList<TaskFireLogVo>();
		for(TaskFireLog taskFireLog : taskFireLogList){

			TaskFireLogVo taskFireLogVoNew = new TaskFireLogVo();
			BeanUtils.copyProperties(taskFireLog, taskFireLogVoNew);
			taskFireLogVoList.add(taskFireLogVoNew);
		}

		return Result.ok(taskFireLogVoList);
	}


}
