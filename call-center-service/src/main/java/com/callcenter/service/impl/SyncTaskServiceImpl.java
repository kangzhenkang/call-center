
package com.callcenter.service.impl;

import com.callcenter.dao.DataRecordDao;
import com.callcenter.dao.SyncTaskDao;
import com.callcenter.dao.base.BaseDao;
import com.callcenter.domain.DataRecord;
import com.callcenter.domain.SyncTask;
import com.callcenter.service.SyncTaskService;
import com.callcenter.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * SyncTaskService 实现类
 * @author wangyue-ds6
 * @since 2014-02-28
 */
@Service("syncTaskService")
public class SyncTaskServiceImpl extends BaseServiceImpl<SyncTask,Long> implements SyncTaskService {
	
	@Resource private SyncTaskDao syncTaskDao;
	@Resource private DataRecordDao dataRecordDao;

	public BaseDao<SyncTask,Long> getDao() {
		return syncTaskDao;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int insertEntryCreateId(SyncTask SyncTask) {
		return super.insertEntryCreateId(SyncTask);
	}

    @Transactional(rollbackFor=Exception.class)
    @Override
    public int deleteSyncTaskAndDataById(Long id) {
        if(syncTaskDao.deleteByKey(id) >0 ){
            DataRecord  dr4Update = new DataRecord();
            dr4Update.setTaskId(id);
            return  dataRecordDao.deleteByKey(dr4Update);
        }
        return 0;
    }

    @Override
    public int resetStatusAndFailCountById(Long id) {
        return syncTaskDao.resetStatusAndFailCountById(id);
    }
}