
package com.callcenter.service.base;

import java.io.Serializable;
import java.util.List;
import com.callcenter.domain.common.Page;

/**
 * service基类<实体,主键>
 * @author wangyue-ds6
 * @since 2014-02-28
 * @param <T> 实体
 * @param <KEY> 主键
 */
public interface BaseService<T,KEY extends Serializable> {

	/**
	 * 添加对象
	 * @param t
	 * @return
	 */
	int insertEntry(T...t);
	
	/**
	 * 添加对象并且设置主键ID值(需要事务支持)
	 * @param t
	 * @return
	 */
	int insertEntryCreateId(T t);
	
	/**
	 * 删除对象,主键
	 * @param key 主键数组
	 * @return 影响条数
	 */
	int deleteByKey(KEY...key);
	
	/**
	 * 按条件删除对象
	 * @param condition
	 * @return 影响条数
	 */
	int deleteByCondition(T condition);
	
	/**
	 * 更新对象,条件主键Id
	 * @param condition 更新对象
	 * @return 影响条数
	 */
	int updateByKey(T condition);
	
	/**
	 * 保存或更新对象(条件主键Id)
	 * @param t 需更新的对象
	 * @return 影响条数
	 */
	int saveOrUpdate(T t);
	
	/**
	 * 查询对象,条件主键
	 * @param key
	 * @return 实体对象
	 */
	T selectEntry(KEY key);
	
	/**
	 * 查询对象列表,主键数组
	 * @param key
	 * @return 对象列表
	 */
	List<T> selectEntryList(KEY...key);
	
	/**
	 * 查询对象,只要不为NULL与空则为条件
	 * @param condition 查询条件
	 * @return 对象列表
	 */
	List<T> selectEntryList(T condition);
	
	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @return 分页对象
	 */
	Page<T> selectPage(T condition, Page<T> page);
}
