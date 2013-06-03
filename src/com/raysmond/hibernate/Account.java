package com.raysmond.hibernate;

import javax.persistence.Entity;

import edu.fudan.ss.persistence.hibernate.common.BaseModelObject;

/**
 * The base class for all persons in the system
 * 
 * @author raysmond
 * 
 */
@Entity
public abstract class Account extends BaseModelObject {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
