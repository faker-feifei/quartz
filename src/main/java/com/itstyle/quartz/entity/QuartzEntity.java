package com.itstyle.quartz.entity;

/**
 * 任务类
 * 创建者	张志朋
 * 创建时间	2018年3月28日
 */
public class QuartzEntity {

	/** 任务调度的参数key */
	public static final String JSON_PARAM_KEY    = "jsonParam";

	private String jobName;//任务名称
	private String jobGroup;//任务分组
	private String description;//任务描述
	private String jobClassName;//执行类
	private String cronExpression;//执行时间
	private String triggerName;//执行时间
	private String triggerState;//任务状态

	private String previousFireTime;

	private String nextFireTime;
	
	private String oldJobName;//任务名称 用于修改
	private String oldJobGroup;//任务分组 用于修改

	private String jsonParam;//参数json格式

	public QuartzEntity() {
		super();
	}
	public QuartzEntity(String jobName, String jobGroup, String description, String jobClassName, String cronExpression, String triggerName) {
		super();
		this.jobName = jobName;
		this.jobGroup = jobGroup;
		this.description = description;
		this.jobClassName = jobClassName;
		this.cronExpression = cronExpression;
		this.triggerName = triggerName;
	}

	public String getPreviousFireTime() {
		return previousFireTime;
	}

	public void setPreviousFireTime(String previousFireTime) {
		this.previousFireTime = previousFireTime;
	}
	public String getNextFireTime() {
		return nextFireTime;
	}
	public void setNextFireTime(String nextFireTime) {
		this.nextFireTime = nextFireTime;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getJobClassName() {
		return jobClassName;
	}
	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getTriggerName() {
		return triggerName;
	}
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	public String getTriggerState() {
		return triggerState;
	}
	public void setTriggerState(String triggerState) {
		this.triggerState = triggerState;
	}
	public String getOldJobName() {
		return oldJobName;
	}
	public void setOldJobName(String oldJobName) {
		this.oldJobName = oldJobName;
	}
	public String getOldJobGroup() {
		return oldJobGroup;
	}
	public void setOldJobGroup(String oldJobGroup) {
		this.oldJobGroup = oldJobGroup;
	}

	public String getJsonParam() {
		return jsonParam;
	}

	public void setJsonParam(String jsonParam) {
		this.jsonParam = jsonParam;
	}
}
