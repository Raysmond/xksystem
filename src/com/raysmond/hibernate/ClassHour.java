package com.raysmond.hibernate;

import javax.persistence.Embeddable;

/**
 * ÉÏ¿Î¿ÎÊ±
 * @author Raysmond
 *
 */
@Embeddable
public class ClassHour {
	private Integer begin;
	private Integer end;
	
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
