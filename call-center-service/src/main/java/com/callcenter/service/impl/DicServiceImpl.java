
package com.callcenter.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.callcenter.domain.Dic;
import com.callcenter.dao.base.BaseDao;
import com.callcenter.dao.DicDao;
import com.callcenter.service.base.BaseServiceImpl;
import com.callcenter.service.DicService;

/**
 * DicService 实现类
 * @author wangyue-ds6
 * @since 2014-02-28
 */
@Service("dicService")
public class DicServiceImpl extends BaseServiceImpl<Dic,Long> implements DicService {
	
	@Resource private DicDao dicDao;
	
	public BaseDao<Dic,Long> getDao() {
		return dicDao;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int insertEntryCreateId(Dic dic) {
		return super.insertEntry(dic);
	}
}