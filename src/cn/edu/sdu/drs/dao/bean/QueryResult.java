package cn.edu.sdu.drs.dao.bean;

import java.util.List;

/**
 * 保存查询结果
 * @author join
 *
 */

public class QueryResult<T> {
	/** 结果集 **/
	private List<T>  resultList;
	/** 结果的数量 **/
	private Long totleResult;
	
	public List<T> getResultList() {
		return resultList;
	}
	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
	public long getTotleResult() {
		return totleResult;
	}
	public void setTotleResult(long totleResult) {
		this.totleResult = totleResult;
	}

}
