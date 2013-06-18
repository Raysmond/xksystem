package com.raysmond.hibernate.controller;

import java.util.Collection;
import java.util.List;

import com.raysmond.hibernate.ConcreteTerm;
import com.raysmond.hibernate.CourseSchedule;
import com.raysmond.hibernate.Teacher;
import com.raysmond.hibernate.Term;
import com.raysmond.hibernate.TermCourse;

/**
 * Term Courses controller
 * 
 * @author Raysmond
 * 
 */
public interface ICourseController {

	/**
	 * Set up a concrete term course
	 * @param name the name of the course
	 * @param address the place where the course takes place
	 * @param studentsLimit 
	 * @param schedules time schedules for the course. It can be set up later.
	 * @param teacher 
	 * @param term 
	 * @return the course created
	 */
	public TermCourse registerTermCourse(String courseId,String name, String address,
			Integer studentsLimit,Collection<CourseSchedule> schedules, Teacher teacher, Term term);

	/**
	 * Start choosing course for a term
	 * @param term
	 */
	public void startChoosingCourse(Term term);

	/**
	 * End choosing course for a term
	 * @param term
	 */
	public void endChoosingCourse(Term term);

	/**
	 * Prepare a term. The default choosing course status is not started yet.
	 * @param year
	 * @param cterm
	 * @return the new term created
	 */
	public Term prepareTermCourse(Integer year,ConcreteTerm cterm);

	/**
	 * Ajust the students limit of a term course
	 * @param course
	 * @param studentsLimit
	 * @return
	 */
	public boolean ajustCourseStudentLimit(TermCourse course,
			Integer studentsLimit);
}
