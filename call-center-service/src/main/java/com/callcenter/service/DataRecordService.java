
package com.callcenter.service;

import com.callcenter.api.domain.DataContent;
import com.callcenter.domain.DataRecord;
import com.callcenter.service.base.BaseService;

/**
 * DataRecordService接口
 * @author wangyue-ds6
 * @since 2014-02-28
 */
public interface DataRecordService extends BaseService<DataRecord,Long> {
    /**
     * 保存记录日志方法
     * @param dataContent 记录日志信息实体类
     * @return   成功或者失败
     */
    public boolean writeDataRecord(DataContent dataContent);

    /**
     * 修改记录日志方法
     * @param dataContent 记录日志信息实体类
     * @return   成功或者失败
     */
    public boolean updateDataRecord(DataContent dataContent);
	
}