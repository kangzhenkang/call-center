
package com.callcenter.domain;

import java.util.Date;
import com.callcenter.domain.base.BaseDomain;

/**
 * dic
 * @author wangyue-ds6
 * @since 2014-02-28
 */
public class Dic extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String dicKey;
	private String dicValue;
	private String dicType;
	private Integer dicSort;
	private String dicDesc;
	private Date createTime;
	private Date updateTime;
	private Integer yn;

	public Dic(){
		//默认无参构造方法
	}

	/**
	 * 获取 dicKey
	 * @return
	 */
	public String getDicKey(){
		return dicKey;
	}
	
	/**
	 * 设置 dicKey
	 * @param dicKey
	 */
	public void setDicKey(String dicKey){
		this.dicKey = dicKey;
	}

	/**
	 * 获取 dicValue
	 * @return
	 */
	public String getDicValue(){
		return dicValue;
	}
	
	/**
	 * 设置 dicValue
	 * @param dicValue
	 */
	public void setDicValue(String dicValue){
		this.dicValue = dicValue;
	}

	/**
	 * 获取 dicType
	 * @return
	 */
	public String getDicType(){
		return dicType;
	}
	
	/**
	 * 设置 dicType
	 * @param dicType
	 */
	public void setDicType(String dicType){
		this.dicType = dicType;
	}

	/**
	 * 获取 dicSort
	 * @return
	 */
	public Integer getDicSort(){
		return dicSort;
	}
	
	/**
	 * 设置 dicSort
	 * @param dicSort
	 */
	public void setDicSort(Integer dicSort){
		this.dicSort = dicSort;
	}

	/**
	 * 获取 dicDesc
	 * @return
	 */
	public String getDicDesc(){
		return dicDesc;
	}
	
	/**
	 * 设置 dicDesc
	 * @param dicDesc
	 */
	public void setDicDesc(String dicDesc){
		this.dicDesc = dicDesc;
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