package com.raysmond.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

import org.hibernate.Query;

import edu.fudan.ss.persistence.hibernate.common.BaseModelObject;
import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

@Entity
public class Term extends BaseModelObject {

	//Ñ§Äê
	private Integer year;
	//Ñ¡¿Î×´Ì¬
	private ChooseCourseStatus status = ChooseCourseStatus.NOT_STARTED;
	// concrete term. For example FIRST_TERM or SECOND_TERM
	// @Enumerated(EnumType.STRING)
	private ConcreteTerm concreteTerm;
	// course list in the term
	@OneToMany(mappedBy = "term", cascade = { CascadeType.ALL })
	private Collection<TermCourse> courses = new ArrayList<TermCourse>();
	
	public static Term create(Integer year,ChooseCourseStatus status,
			ConcreteTerm concreteTerm,IPersistenceManager pm){
		Term term  = new Term();
		term.setYear(year);
		term.setStatus(status);
		term.setConcreteTerm(concreteTerm);
		pm.save(term);
		return term;
	}
	
	public ChooseCourseStatus getStatus() {
		return status;
	}

	public void setStatus(ChooseCourseStatus status) {
		this.status = status;
	}
	
	public boolean canChooseCourse(){
		return status==ChooseCourseStatus.STARTED;
	}

	public Collection<TermCourse> getCourses() {
		return courses;
	}

	public void setCourses(Collection<TermCourse> courses) {
		this.courses = courses;
	}
	
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
