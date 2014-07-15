
package com.callcenter.dao;

import com.callcenter.dao.base.BaseDao;
import com.callcenter.domain.SyncTask;

/**
 * call-centerDao 接口
 * @author wangyue-ds6
 * @since 2014-02-28
 */
public interface SyncTaskDao extends BaseDao<SyncTask,Long>{
	//自定义扩展
    public int resetStatusAndFailCountById(Long id);
	
}