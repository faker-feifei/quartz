package com.itstyle.quartz.entity;


import com.dexcoder.commons.pager.Pageable;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 计划任务信息
 *
 * @author: 百果园
 * Date: 2018端午
 * Time: 上午10:24
 */

public class TaskFireLog extends Pageable {
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String id;
    private String groupName;
    private String taskName;
    private Date startTime;
    private Date endTime;

    private String status;
    private String serverHost;
    private String serverDuid;
    private String fireInfo;

    public TaskFireLog() {
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


    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that == null) {
            return false;
        } else if (this.getClass() != that.getClass()) {
            return false;
        } else {
            boolean var10000;
            label105: {
                label97: {
                    TaskFireLog other = (TaskFireLog)that;
                    if (this.getId() == null) {
                        if (other.getId() != null) {
                            break label97;
                        }
                    } else if (!this.getId().equals(other.getId())) {
                        break label97;
                    }

                    if (this.getGroupName() == null) {
                        if (other.getGroupName() != null) {
                            break label97;
                        }
                    } else if (!this.getGroupName().equals(other.getGroupName())) {
                        break label97;
                    }

                    if (this.getTaskName() == null) {
                        if (other.getTaskName() != null) {
                            break label97;
                        }
                    } else if (!this.getTaskName().equals(other.getTaskName())) {
                        break label97;
                    }

                    if (this.getStartTime() == null) {
                        if (other.getStartTime() != null) {
                            break label97;
                        }
                    } else if (!this.getStartTime().equals(other.getStartTime())) {
                        break label97;
                    }

                    if (this.getEndTime() == null) {
                        if (other.getEndTime() != null) {
                            break label97;
                        }
                    } else if (!this.getEndTime().equals(other.getEndTime())) {
                        break label97;
                    }

                    if (this.getStatus() == null) {
                        if (other.getStatus() != null) {
                            break label97;
                        }
                    } else if (!this.getStatus().equals(other.getStatus())) {
                        break label97;
                    }

                    if (this.getServerHost() == null) {
                        if (other.getServerHost() != null) {
                            break label97;
                        }
                    } else if (!this.getServerHost().equals(other.getServerHost())) {
                        break label97;
                    }

                    if (this.getServerDuid() == null) {
                        if (other.getServerDuid() != null) {
                            break label97;
                        }
                    } else if (!this.getServerDuid().equals(other.getServerDuid())) {
                        break label97;
                    }

                    if (this.getFireInfo() == null) {
                        if (other.getFireInfo() == null) {
                            break label105;
                        }
                    } else if (this.getFireInfo().equals(other.getFireInfo())) {
                        break label105;
                    }
                }

                var10000 = false;
                return var10000;
            }

            var10000 = true;
            return var10000;
        }
    }

    @Override
    public int hashCode() {

        int result = 1;
        result = 31 * result + (this.getId() == null ? 0 : this.getId().hashCode());
        result = 31 * result + (this.getGroupName() == null ? 0 : this.getGroupName().hashCode());
        result = 31 * result + (this.getTaskName() == null ? 0 : this.getTaskName().hashCode());
        result = 31 * result + (this.getStartTime() == null ? 0 : this.getStartTime().hashCode());
        result = 31 * result + (this.getEndTime() == null ? 0 : this.getEndTime().hashCode());
        result = 31 * result + (this.getStatus() == null ? 0 : this.getStatus().hashCode());
        result = 31 * result + (this.getServerHost() == null ? 0 : this.getServerHost().hashCode());
        result = 31 * result + (this.getServerDuid() == null ? 0 : this.getServerDuid().hashCode());
        result = 31 * result + (this.getFireInfo() == null ? 0 : this.getFireInfo().hashCode());
        return result;
    }
}
