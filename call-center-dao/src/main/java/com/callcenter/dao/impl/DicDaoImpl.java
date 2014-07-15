
package com.callcenter.dao.impl;

import org.springframework.stereotype.Repository;
import com.callcenter.domain.Dic;
import com.callcenter.dao.base.BaseDaoImpl;
import com.callcenter.dao.DicDao;

/**
 * DicDao 实现类
 * @author wangyue-ds6
 * @since 2014-02-28
 */
@Repository("dicDao")
public class DicDaoImpl extends BaseDaoImpl<Dic,Long> implements DicDao {
	private final static String NAMESPACE = "com.callcenter.dao.DicDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
		
}