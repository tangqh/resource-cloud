package cn.edu.sdu.drs.dao;

import java.util.LinkedHashMap;

import cn.edu.sdu.drs.dao.bean.QueryResult;


/**
 * DAO接口
 * @author join
 *
 */
public interface BaseDao {
	/**
	 * 保存一个实体
	 * @param entity
	 */
	public Object save(Object entity);

	/**
	 * 更新一个实体
	 * @param entity
	 */
	public Object update(Object entity);
	
	/**
	 * 删除一个实体
	 * @param entityClass 实体类型
	 * @param entityid 实体ID
	 */
	public <T> void delete(Class<T> entityClass, Object entityid);
	
	/**
	 * 删除实体
	 * @param entityClass 实体类型
	 * @param entityids 实体IDs
	 */
	public <T> void delete(Class<T> entityClass, Object[] entityids);
	
	/**
	 * 根据实体的ID获得实体
	 * @param <T>
	 * @param entityClass实体类型
	 * @param entityId实体id
	 * @return
	 */
	public <T> T find(Class<T> entityClass, Object entityId);
	
	/**
	 * 获取分页数据加排序加查询条件
	 * @param <T>
	 * @param entityClass实体类
	 * @param firstIndex开始索引
	 * @param maxResult最大返回量
	 * @param orderBy 第一个string存放实体属性，第二个string存放asc/desc升降序
	 * @param whereJpql 查询条件语句
	 * @param queryParams查询条件语句所需要的参数
	 * @return
	 */
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstIndex, int maxResult
			, LinkedHashMap<String, String> orderBy, String whereJpql, Object[] queryParams);
	
	/** 不要排序  **/
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstIndex, int maxResult
			, String whereJpql, Object[] queryParams);

	/** 不要查询条件 **/
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstIndex, int maxResult
			, LinkedHashMap<String, String> orderBy);
	
	/** 既不要排序也不要查询条件 **/
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstIndex, int maxResult);
	
	/** 查询所有可见的实体 **/
	public <T> QueryResult<T> getScrollData(Class<T> entityClass);
	
	/** 不要分页 **/
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, String whereJpql, Object[] queryParams);
	
	
}
