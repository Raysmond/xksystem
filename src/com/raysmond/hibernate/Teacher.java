package com.raysmond.hibernate;

import javax.persistence.Entity;

import edu.fudan.ss.persistence.hibernate.common.BaseModelObject;

@Entity
public class Teacher extends BaseModelObject {
	private String jobNumber;

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}
}
