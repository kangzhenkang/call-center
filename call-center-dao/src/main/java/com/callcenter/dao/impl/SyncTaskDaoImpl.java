
package com.callcenter.dao.impl;

import org.springframework.stereotype.Repository;
import com.callcenter.domain.SyncTask;
import com.callcenter.dao.base.BaseDaoImpl;
import com.callcenter.dao.SyncTaskDao;

/**
 * SyncTaskDao 实现类
 * @author wangyue-ds6
 * @since 2014-02-28
 */
@Repository("syncTaskDao")
public class SyncTaskDaoImpl extends BaseDaoImpl<SyncTask,Long> implements SyncTaskDao {
	private final static String NAMESPACE = "com.callcenter.dao.SyncTaskDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

    @Override
    public int resetStatusAndFailCountById(Long id) {
        return this.update(NAMESPACE+"resetStatusAndFailCountById",id);
    }
}