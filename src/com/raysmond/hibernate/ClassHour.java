package com.raysmond.hibernate;

import javax.persistence.Embeddable;

/**
 * Course hours
 * @author Raysmond
 *
 */
@Embeddable
public class ClassHour {
	private Integer begin;
	private Integer end;
	
	/**
	 * To check whether two class hours have overlaps 
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
