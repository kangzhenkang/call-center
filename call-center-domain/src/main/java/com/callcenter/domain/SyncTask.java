
package com.callcenter.domain;

import com.callcenter.domain.base.BaseDomain;

import java.util.Date;

/**
 * SyncTask类
 * @author wangyue-ds6 wangyueinfo
 * @since 2014-02-28
 */
public class SyncTask extends BaseDomain {
    private static final long serialVersionUID = 1L;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 调用业务对象名称
     */
    private String callServiceName;

    /**
     * 调用业务方法名称
     */
    private String callMethodName;

    /**
     * 同步状态 1. 初始化 2. 未同步  3. 同步中 4. 同步成功    5. 同步失败 6：已经转历史，转历史就代表该数据已经不存在于该表中
     */
    private Integer syncStatus;

    /**
     * 任务类型 WORKER ,MQ
     */
    private String taskType;

    /**
     * 同步时间
     */
    private Date syncTime;

    /**
     * 同步类型 CYCLE，SINGLE
     */
    private String syncType;

    /**
     * 同步计划
     */
    private String syncPlan;

    /**
     * 同步次数
     */
    private Integer syncCount;
    /**
     * 失败次数
     */
    private Integer failCount;

    /**
     * 排序号，用来串行执行时的顺序 ， 默认为1
     */
    private Integer orderNo;

    /**
     * 任务片区，用于任务分配
     */
    private Integer regionNo;
    /**
     * mq发送队列
     */
    private String mqDestSysId;

    /**
     * 调用协议 ，默认为DUBBO,CXF RESTFUL,HESSIAN
     */
    private String protocol;
    /**
     * 手机号码，报警发短信
     */
    private String mobile;
    /**
     * 邮箱，报警发邮件
     */
    private String email;
    /**
     * 异常信息，系统之间调用的异常信息
     */
    private String errorMsg;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 0：删除 1：有效
     */
    private Integer yn;

    /**
     * 业务数据
     */
    private String bizDataStr;
    /**
     * 业务状态
     */
    private String bizStatus;

    /**
     * 上一个状态
     */
    private Integer previousSyncStatus;

    public SyncTask() {
        //默认无参构造方法
    }

    /**
     * 获取 bizType
     *
     * @return
     */
    public String getBizType() {
        return bizType;
    }

    /**
     * 设置 bizType
     *
     * @param bizType
     */
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    /**
     * 获取 callServiceName
     *
     * @return
     */
    public String getCallServiceName() {
        return callServiceName;
    }

    /**
     * 设置 callServiceName
     *
     * @param callServiceName
     */
    public void setCallServiceName(String callServiceName) {
        this.callServiceName = callServiceName;
    }

    /**
     * 获取 callMethodName
     *
     * @return
     */
    public String getCallMethodName() {
        return callMethodName;
    }

    /**
     * 设置 callMethodName
     *
     * @param callMethodName
     */
    public void setCallMethodName(String callMethodName) {
        this.callMethodName = callMethodName;
    }

    /**
     * 获取 syncStatus
     *
     * @return
     */
    public Integer getSyncStatus() {
        return syncStatus;
    }

    /**
     * 设置 syncStatus
     *
     * @param syncStatus
     */
    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    /**
     * 获取 taskType
     *
     * @return
     */
    public String getTaskType() {
        return taskType;
    }

    /**
     * 设置 taskType
     *
     * @param taskType
     */
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    /**
     * 获取 syncTime
     *
     * @return
     */
    public Date getSyncTime() {
        return syncTime;
    }

    /**
     * 设置 syncTime
     *
     * @param syncTime
     */
    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    /**
     * 获取 syncType
     *
     * @return
     */
    public String getSyncType() {
        return syncType;
    }

    /**
     * 设置 syncType
     *
     * @param syncType
     */
    public void setSyncType(String syncType) {
        this.syncType = syncType;
    }

    /**
     * 获取 syncPlan
     *
     * @return
     */
    public String getSyncPlan() {
        return syncPlan;
    }

    /**
     * 设置 syncPlan
     *
     * @param syncPlan
     */
    public void setSyncPlan(String syncPlan) {
        this.syncPlan = syncPlan;
    }

    /**
     * 获取 syncCount
     *
     * @return
     */
    public Integer getSyncCount() {
        return syncCount;
    }

    /**
     * 设置 syncCount
     *
     * @param syncCount
     */
    public void setSyncCount(Integer syncCount) {
        this.syncCount = syncCount;
    }

    /**
     * 获取 failCount
     *
     * @return
     */
    public Integer getFailCount() {
        return failCount;
    }

    /**
     * 设置 failCount
     *
     * @param failCount
     */
    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    /**
     * 获取 orderNo
     *
     * @return
     */
    public Integer getOrderNo() {
        return orderNo;
    }

    /**
     * 设置 orderNo
     *
     * @param orderNo
     */
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取 createTime
     *
     * @return
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置 createTime
     *
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取 updateTime
     *
     * @return
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置 updateTime
     *
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    /**
     * 获取 regionNo
     *
     * @return
     */
    public Integer getRegionNo() {
        return regionNo;
    }

    /**
     * 设置 regionNo
     *
     * @param regionNo
     */
    public void setRegionNo(Integer regionNo) {
        this.regionNo = regionNo;
    }

    public String getMqDestSysId() {
        return mqDestSysId;
    }

    public void setMqDestSysId(String mqDestSysId) {
        this.mqDestSysId = mqDestSysId;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * 获取 yn
     *
     * @return
     */
    public Integer getYn() {
        return yn;
    }

    /**
     * 设置 yn
     *
     * @param yn
     */
    public void setYn(Integer yn) {
        this.yn = yn;
    }


    public String getBizDataStr() {
        return bizDataStr;
    }

    public void setBizDataStr(String bizDataStr) {
        this.bizDataStr = bizDataStr;
    }

    public String getBizStatus() {
        return bizStatus;
    }

    public void setBizStatus(String bizStatus) {
        this.bizStatus = bizStatus;
    }

    public Integer getPreviousSyncStatus() {
        return previousSyncStatus;
    }

    public void setPreviousSyncStatus(Integer previousSyncStatus) {
        this.previousSyncStatus = previousSyncStatus;
    }

    @Override
    public String toString() {
        return "SyncTask{" +
                "bizType='" + bizType + '\'' +
                ", callServiceName='" + callServiceName + '\'' +
                ", callMethodName='" + callMethodName + '\'' +
                ", syncStatus=" + syncStatus +
                ", taskType='" + taskType + '\'' +
                ", syncTime=" + syncTime +
                ", syncType='" + syncType + '\'' +
                ", syncPlan='" + syncPlan + '\'' +
                ", syncCount=" + syncCount +
                ", failCount=" + failCount +
                ", orderNo=" + orderNo +
                ", regionNo=" + regionNo +
                ", mqDestSysId='" + mqDestSysId + '\'' +
                ", protocol='" + protocol + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", yn=" + yn +
                ", bizDataStr='" + bizDataStr + '\'' +
                ", bizStatus='" + bizStatus + '\'' +
                ", previousSyncStatus=" + previousSyncStatus +
                '}';
    }

    public int add(String target){

        System.out.println("running method add() :"+ target);
        return 1;
    }
}