package com.itstyle.quartz.vo;


import com.dexcoder.commons.pager.Pageable;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 计划任务信息
 *
 * @author: 百果园
 * Date: 2018端午
 * Time: 上午10:24
 */
public class TaskFireLogVo extends Pageable {

    private String id;
    private String groupName;
    private String taskName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date endTime;

    private String status;
    private String serverHost;
    private String serverDuid;
    private String fireInfo;

    public TaskFireLogVo() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getServerHost() {
        return this.serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost == null ? null : serverHost.trim();
    }

    public String getServerDuid() {
        return this.serverDuid;
    }

    public void setServerDuid(String serverDuid) {
        this.serverDuid = serverDuid == null ? null : serverDuid.trim();
    }

    public String getFireInfo() {
        return this.fireInfo;
    }

    public void setFireInfo(String fireInfo) {
        this.fireInfo = fireInfo == null ? null : fireInfo.trim();
    }


}
