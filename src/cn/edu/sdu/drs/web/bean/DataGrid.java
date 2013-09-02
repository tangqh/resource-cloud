package cn.edu.sdu.drs.web.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author join
 *
 */
public class DataGrid <T>{

	/* 数据总数 */
	private Long total = 0L;
	/* 返回给前台的数据 */
	private List<T> rows = new ArrayList<T>();

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
