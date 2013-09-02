package cn.edu.sdu.drs.dao.bean;

/**
 * 
 * @author join
 *
 */

public class PageIndex {
	private long startindex;
	private long endindex;
	
	public PageIndex(long startindex, long endindex) {
		this.startindex = startindex;
		if(endindex <= 0){
			this.endindex = 1;
		}else{
			this.endindex = endindex;
		}
	}
	public long getStartindex() {
		return startindex;
	}
	public void setStartindex(long startindex) {
		this.startindex = startindex;
	}
	public long getEndindex() {
		return endindex;
	}
	public void setEndindex(long endindex) {
		this.endindex = endindex;
	}
	
}
