package com.raysmond.hibernate;

import javax.persistence.Embeddable;

/**
 * 课时类
 * @author Raysmond
 *
 */
@Embeddable
public class ClassHour {
	private Integer begin;
	private Integer end;
	
	
	public ClassHour(){
		
	}
	
	public ClassHour(int begin, int end) {
		setBegin(begin);
		setEnd(end);
	}

	/**
	 * 检测两个课时是否有重叠冲突 
	 * @param hours
	 * @return
	 */
	public boolean isOverlapped(ClassHour hours){
		if(begin>hours.getEnd()||end<hours.getBegin()){
			return false;
		}
		return true;
	}

	public Integer getBegin() {
		return begin;
	}

	public void setBegin(Integer begin) {
		this.begin = begin;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}
	
	
}
