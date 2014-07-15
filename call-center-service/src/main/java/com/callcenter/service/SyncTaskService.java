
package com.callcenter.service;

import com.callcenter.domain.SyncTask;
import com.callcenter.service.base.BaseService;

/**
 * call-centerService接口
 * @author wangyue-ds6
 * @since 2014-02-28
 */
public interface SyncTaskService extends BaseService<SyncTask,Long> {

    public int deleteSyncTaskAndDataById(Long id);
    public int resetStatusAndFailCountById(Long id);

	
}