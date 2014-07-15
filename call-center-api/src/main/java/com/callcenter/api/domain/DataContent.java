
package com.callcenter.api.domain;

import java.io.Serializable;

/**
 * DataContent
 * @author wangyue-ds6 wangyueinfo
 * @since 2014-02-28
 */
public class DataContent implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * 任务ID，关联任务表（sync_task）的主键
     */
    private Long taskId;
    /**
     * 业务类型 (定义自己的业务类型)
     */
    private String bizType;
    /**
     * 业务数据-XML/JSON 如果是synchType=Interface，要这样格式信息
     "com.jd.josl.domain.base.User#_#{"id":1,"name:":"张三
     "}_#_com.jd.josl.domain.base.Order#_#{"orderId":"111","artNo":"12893"}" ，
     _#_用于分割多个参数，#_#用于分割参数中类名和参数Json 串
     */
    private String bizData;
    /**
     * 业务状态，S:成功，F：失败
     */
    private String bizStatus;

    /**
     * 调用业务对象名称如：”userService” 就是你spring 注入的ID
     */
    private String callServiceName;
    /**
     * 调用业务方法名称如：”addUser”
     */
    private String callMethodName;

    /**
     * 同步状态 0. 初始化 1. 未同步  2. 同步成功 3. 同步中   4. 同步失败 5：已经转历史，转历史就代表该数据已经不存在于该表中
     */
    private Integer syncStatus;
    /**
     * 任务类型 WORKER ,MQ
     */
    private String taskType;
    /**
     * 同步类型 CYCLE，SINGLE
     */
    private String syncType;
    /**
     * 执行计划
     */
    private String syncPlan;
    /**
     * mq发送队列
     */
    private String mqDestSysId;
    /**
     * 排序号，用来串行执行时的顺序 ， 默认为1
     */
    private int  orderNo;
    /**
     *  执行次数
     */
    private Integer syncCount;
    /**
     *  执行失败次数
     */
    private Integer failCount;
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


    public DataContent(){
		//默认无参构造方法
	}

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBizData() {
        return bizData;
    }

    public void setBizData(String bizData) {
        this.bizData = bizData;
    }

    public String getCallServiceName() {
        return callServiceName;
    }

    public void setCallServiceName(String callServiceName) {
        this.callServiceName = callServiceName;
    }

    public String getCallMethodName() {
        return callMethodName;
    }

    public void setCallMethodName(String callMethodName) {
        this.callMethodName = callMethodName;
    }

    public Integer getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getSyncType() {
        return syncType;
    }

    public void setSyncType(String syncType) {
        this.syncType = syncType;
    }

    public String getSyncPlan() {
        return syncPlan;
    }

    public void setSyncPlan(String syncPlan) {
        this.syncPlan = syncPlan;
    }

    public String getMqDestSysId() {
        return mqDestSysId;
    }

    public void setMqDestSysId(String mqDestSysId) {
        this.mqDestSysId = mqDestSysId;
    }

    public String getBizStatus() {
        return bizStatus;
    }

    public void setBizStatus(String bizStatus) {
        this.bizStatus = bizStatus;
    }

    public Integer getSyncCount() {
        return syncCount;
    }

    public void setSyncCount(Integer syncCount) {
        this.syncCount = syncCount;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
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
}