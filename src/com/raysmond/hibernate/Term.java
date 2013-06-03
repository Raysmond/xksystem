package com.raysmond.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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

	// 学年
	private Integer year;
	// 选课状态
	private ChooseCourseStatus status = ChooseCourseStatus.NOT_STARTED;
	// 期，如第一学期，第二学期
	// @Enumerated(EnumType.STRING)
	private ConcreteTerm concreteTerm;
	// 学期课程
	@OneToMany(mappedBy = "term", cascade = { CascadeType.ALL })
	private Collection<TermCourse> courses = new ArrayList<TermCourse>();

	public static Term create(Integer year, ChooseCourseStatus status,
			ConcreteTerm concreteTerm, IPersistenceManager pm) {
		Term term = new Term();
		term.setYear(year);
		term.setStatus(status);
		term.setConcreteTerm(concreteTerm);
		pm.save(term);
		return term;
	}

	/**
	 * Check whether the schedule and address of a course has conflict with
	 * other courses in the term
	 * 
	 * @param termCourse
	 * @return
	 */
	public boolean isConflictCourse(TermCourse termCourse) {
		Iterator<TermCourse> coursesIter = courses.iterator();
		while (coursesIter.hasNext()) {
			TermCourse course = coursesIter.next();
			if (course.equals(termCourse))
				continue;
			if (course.isCourseConflict(termCourse)) {
				return true;
			}
		}
		return false;
	}

	public ChooseCourseStatus getStatus() {
		return status;
	}

	public void setStatus(ChooseCourseStatus status) {
		this.status = status;
	}

	public boolean canChooseCourse() {
		return status == ChooseCourseStatus.STARTED;
	}

	public Collection<TermCourse> getCourses() {
		return courses;
	}

	public void setCourses(Collection<TermCourse> courses) {
		this.courses = courses;
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
