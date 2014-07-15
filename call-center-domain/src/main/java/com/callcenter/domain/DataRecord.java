
package com.callcenter.domain;

import com.callcenter.domain.base.BaseDomain;

import java.util.Date;

/**
 * dataRecord
 * @author wangyue-ds6
 * @since 2014-02-28
 */
public class DataRecord extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private Long taskId;
	private String bizType;
	private String bizData;
	private String bizStatus;
	private Integer orderNo;
	private Date createTime;
	private Date updateTime;
	private Integer yn;

	public DataRecord(){
		//默认无参构造方法
	}

	/**
	 * 获取 taskId
	 * @return
	 */
	public Long getTaskId(){
		return taskId;
	}
	
	/**
	 * 设置 taskId
	 * @param taskId
	 */
	public void setTaskId(Long taskId){
		this.taskId = taskId;
	}

	/**
	 * 获取 bizType
	 * @return
	 */
	public String getBizType(){
		return bizType;
	}
	
	/**
	 * 设置 bizType
	 * @param bizType
	 */
	public void setBizType(String bizType){
		this.bizType = bizType;
	}

	/**
	 * 获取 bizData
	 * @return
	 */
	public String getBizData(){
		return bizData;
	}
	
	/**
	 * 设置 bizData
	 * @param bizData
	 */
	public void setBizData(String bizData){
		this.bizData = bizData;
	}

	/**
	 * 获取 bizStatus
	 * @return
	 */
	public String getBizStatus(){
		return bizStatus;
	}
	
	/**
	 * 设置 bizStatus
	 * @param bizStatus
	 */
	public void setBizStatus(String bizStatus){
		this.bizStatus = bizStatus;
	}

	/**
	 * 获取 orderNo
	 * @return
	 */
	public Integer getOrderNo(){
		return orderNo;
	}
	
	/**
	 * 设置 orderNo
	 * @param orderNo
	 */
	public void setOrderNo(Integer orderNo){
		this.orderNo = orderNo;
	}

	/**
	 * 获取 createTime
	 * @return
	 */
	public Date getCreateTime(){
		return createTime;
	}
	
	/**
	 * 设置 createTime
	 * @param createTime
	 */
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	/**
	 * 获取 updateTime
	 * @return
	 */
	public Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	 * 设置 updateTime
	 * @param updateTime
	 */
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}

	/**
	 * 获取 yn
	 * @return
	 */
	public Integer getYn(){
		return yn;
	}
	
	/**
	 * 设置 yn
	 * @param yn
	 */
	public void setYn(Integer yn){
		this.yn = yn;
	}
}