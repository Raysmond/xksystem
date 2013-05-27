package com.raysmond.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.Query;

import edu.fudan.ss.persistence.hibernate.common.BaseModelObject;
import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

@Entity
public class Term extends BaseModelObject {
	
	private Integer year;
	
	public Collection<TermCourse> getCourses() {
		return courses;
	}

	public void setCourses(Collection<TermCourse> courses) {
		this.courses = courses;
	}
	private ConcreteTerm concreteTerm;
	
	@OneToMany(mappedBy = "term", cascade = { CascadeType.ALL })
	private Collection<TermCourse> courses = new ArrayList<TermCourse>();
	
	
	public Term findTerm(Integer year,ConcreteTerm concreteTerm,IPersistenceManager pm){
		String hql = "from Term t where t.year = :year " 
				+ "and t.concreteTerm = :concreteTerm ";
		Query query = pm.createQuery(hql)
				.setParameter("year", year)
				.setParameter("concreteTerm", concreteTerm);
		List<Term> result = query.list();
		if(result==null||result.size()==0) return null;
		return result.get(0);
	}
	
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	
	public ConcreteTerm getConcreteTerm() {
		return concreteTerm;
	}
	public void setConcreteTerm(ConcreteTerm concreteTerm) {
		this.concreteTerm = concreteTerm;
	}
	
}
