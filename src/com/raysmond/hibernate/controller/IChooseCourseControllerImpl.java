package com.raysmond.hibernate.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raysmond.hibernate.Student;
import com.raysmond.hibernate.Term;
import com.raysmond.hibernate.TermCourse;

import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

@Service
@Transactional
public class IChooseCourseControllerImpl implements IChooseCourseController {
	@Autowired
	private IPersistenceManager persistenceManager;

	@Override
	public boolean chooseCourse(Student student, TermCourse course) {
		if (course.getTerm().canChooseCourse()) {
			// if current students count is less than the students limit
			// and the student has not chosen the course
			// then precede choosing course
			if (course.getStudents().size() < course.getStudentsLimit()
					&& !course.isStudentInCourse(student)) {
				course.getStudents().add(student);
				persistenceManager.save(course);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean dropCourse(Student student, TermCourse course) {
		if (course.getTerm().canChooseCourse()) {
			//the student already chosen the course
			if(course.isStudentInCourse(student)){
				if(course.removeAstudent(student)){
					persistenceManager.save(course);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean followCourse(Student student, TermCourse course) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean defollowCourse(Student student, TermCourse course) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<TermCourse> getAvailableCourses(Term term) {
		if (term.canChooseCourse()) {
			List<TermCourse> courses = new ArrayList<TermCourse>();
			Iterator<TermCourse> allCourses = term.getCourses().iterator();
			while(allCourses.hasNext()){
				TermCourse course = allCourses.next();
				if(course.getStudents().size()<course.getStudentsLimit()){
					courses.add(course);
				}
			}
			return courses;
		}
		return null;
	}
}
