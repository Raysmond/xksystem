package com.raysmond.hibernate;

import javax.persistence.Entity;

import edu.fudan.ss.persistence.hibernate.common.BaseModelObject;

@Entity
public class Student extends BaseModelObject {
	//ѧ��
	private String studentNumber;

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}
}
