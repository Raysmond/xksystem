package com.raysmond.hibernate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

@Entity
public class TermCourse extends Course{
	
	private String address;			//�Ͽεص�
	private Integer studentsLimit;	//��������
	@ManyToOne
	private Term term = new Term();
	
	public static TermCourse create(Term term, String name,String address,
			Integer studentsLimit, IPersistenceManager pm) {
		TermCourse result = new TermCourse();
		result.setTerm(term);
		result.setName(name);
		result.setAddress(address);
		result.setStudentsLimit(studentsLimit);
		pm.save(result);
		return result;
	}
	
	public Term getTerm() {
		return term;
	}
	public void setTerm(Term term) {
		this.term = term;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	public Integer getStudentsLimit() {
		return studentsLimit;
	}
	public void setStudentsLimit(Integer studentsLimit) {
		this.studentsLimit = studentsLimit;
	}
	
}
