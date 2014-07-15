
package com.callcenter.dao.impl;

import org.springframework.stereotype.Repository;
import com.callcenter.domain.DataRecord;
import com.callcenter.dao.base.BaseDaoImpl;
import com.callcenter.dao.DataRecordDao;

/**
 * DataRecordDao 实现类
 * @author wangyue-ds6
 * @since 2014-02-28
 */
@Repository("dataRecordDao")
public class DataRecordDaoImpl extends BaseDaoImpl<DataRecord,Long> implements DataRecordDao {
	private final static String NAMESPACE = "com.callcenter.dao.DataRecordDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
		
}