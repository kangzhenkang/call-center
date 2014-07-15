
package com.callcenter.service;


import com.callcenter.domain.DataContent;

/**
 * DataRecordService接口
 * @author wangyue-ds6 wangyueinfo
 * @since 2014-02-28
 */
public interface DataContentService {
    /**
     * 保持记录日志方法
     * @param dataContent 记录日志信息实体类
     * @return   成功或者失败
     */
    public boolean writeData(DataContent dataContent);
	
}