package cn.edu.sdu.drs.dao;

import java.util.LinkedHashMap;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.sdu.drs.dao.bean.QueryResult;

/**
 * 对BaseDao的抽象实现
 * @author join
 *
 */

@Transactional
public abstract class BaseDaoSupport implements BaseDao {
	
	@PersistenceContext protected EntityManager em;
	
	public <T> void delete(Class<T> entityClass, Object entityid) {
		if(entityid != null && !"".equals(entityid)){
			delete(entityClass, new Object[]{entityid});
		}
	}
	
	public <T> void delete(Class<T> entityClass, Object[] entityids) {
		if(entityids != null && entityids.length>0){
			for(Object id : entityids){
				em.remove(em.getReference(entityClass, id));
			}
		}
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> T find(Class<T> entityClass, Object entityId) {
		return em.find(entityClass, entityId);
	}
	
	public Object save(Object entity) {
		try{
			em.persist(entity);
		}catch (Exception e) {
			return null;
		}
		return entity;
	}
	
	public Object update(Object entity) {
		return em.merge(entity);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstIndex, int maxResult, LinkedHashMap<String, String> orderBy, String whereJpql, Object[] queryParams) {
		QueryResult<T> queryResult = new QueryResult<T>();
		String entityName = getEntityName(entityClass);
		Query query = em.createQuery("select o from " + entityName + " o" + (whereJpql==null ? "" : " where " + whereJpql) + buildOrderBy(orderBy));
		setQueryParams(query, queryParams);
		query.setFirstResult(firstIndex).setMaxResults(maxResult);
		queryResult.setResultList(query.getResultList());
		//必须对query重新赋值
		query = em.createQuery("select count(o) from " + entityName + " o" + (whereJpql==null ? "" : " where " + whereJpql));
		setQueryParams(query, queryParams);
		Long tr = (Long)query.getSingleResult();
		queryResult.setTotleResult(tr);
		return queryResult;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstIndex, int maxResult, LinkedHashMap<String, String> orderBy) {
		return getScrollData(entityClass, firstIndex, maxResult, orderBy,null,null);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstIndex, int maxResult, String whereJpql, Object[] queryParams) {
		return getScrollData(entityClass, firstIndex, maxResult, null, whereJpql, queryParams);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstIndex, int maxResult) {
		return getScrollData(entityClass, firstIndex, maxResult, null);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entityClass) {
		return getScrollData(entityClass, null, null);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, String whereJpql, Object[] queryParams) {
		QueryResult<T> queryResult = new QueryResult<T>();
		String entityName = getEntityName(entityClass);
		String jpql = "select o from " + entityName + " o" + (whereJpql==null ? "" : " where " + whereJpql);
		Query query = em.createQuery(jpql);
		setQueryParams(query, queryParams);
		queryResult.setResultList(query.getResultList());
		//必须对query重新赋值
		query = em.createQuery("select count(o) from " + entityName + " o" + (whereJpql==null ? "" : " where " + whereJpql));
		setQueryParams(query, queryParams);
		Long tr = (Long)query.getSingleResult();
		queryResult.setTotleResult(tr);
		return queryResult;
	}
	
	/**
	 * 给查询条件设置参数
	 * @param query
	 * @param queryParams
	 */
	protected void setQueryParams(Query query, Object[] queryParams){
		if(queryParams!=null && queryParams.length>0){
			for(int i=0; i<queryParams.length; i++){
				query.setParameter(i+1, queryParams[i]);
			}
		}
	}
	
	/**
	 * 组装order by语句
	 * @param orderBy
	 * @return
	 */
	protected String buildOrderBy(LinkedHashMap<String, String> orderBy){
		StringBuffer orderByQL = new StringBuffer("");
		if(orderBy != null && orderBy.size() > 0){
			orderByQL.append(" order by ");
			for(String key : orderBy.keySet()){
				orderByQL.append("o.").append(key).append(" ").append(orderBy.get(key)).append(",");
			}
			orderByQL.deleteCharAt(orderByQL.length() - 1);
		}
		return orderByQL.toString();
	}
	
	/**
	 * 得到实体的名称
	 * @param <T>
	 * @param entityClass实体类型
	 * @return
	 */
	protected <T> String getEntityName(Class<T> entityClass) {
		String entityName = entityClass.getSimpleName();
		Entity entity = entityClass.getAnnotation(Entity.class);
		if(entity.name() != null && !"".equals(entity.name())){
			entityName = entity.name();
		}
		return entityName;
	}
}
