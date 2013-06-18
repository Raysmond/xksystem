package com.raysmond.hibernate.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raysmond.hibernate.DropCourseRecord;
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
			if (course.getCourseStudents().size() < course.getStudentsLimit()
					&& !course.getCourseStudents().contains(student)
					&& !student.getChoosedCourses().contains(course)) {
				course.getCourseStudents().add(student);
				student.getChoosedCourses().add(course);
				persistenceManager.save(course);
				persistenceManager.save(student);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean dropCourse(Student student, TermCourse course) {
		if (course.getTerm().canChooseCourse()) {
			// the student already chosen the course
			if (course.getCourseStudents().contains(student)) {
				if (course.getCourseStudents().remove(student)
						&& student.getChoosedCourses().remove(course)) {
					persistenceManager.save(course);
					persistenceManager.save(student);
					// add drop course record
					DropCourseRecord.create(student, course, persistenceManager);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean followCourse(Student student, TermCourse course) {
		if (course.getTerm().canChooseCourse()) {
			// If the student hasn't followed the course
			if (!course.getFollowStudents().contains(student)
					&& !student.getFollowedCourses().contains(course)) {
				course.getFollowStudents().add(student);
				student.getFollowedCourses().add(course);
				persistenceManager.save(course);
				persistenceManager.save(student);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean defollowCourse(Student student, TermCourse course) {
		if (course.getTerm().canChooseCourse()) {
			// If the student has followed the course
			if (course.getFollowStudents().contains(student)) {
				course.getFollowStudents().remove(student);
				student.getFollowedCourses().remove(course);
				persistenceManager.save(course);
				persistenceManager.save(student);
				return true;
			}
		}
		return false;
	}

	@Override
	public List<TermCourse> getAvailableCourses(Term term) {
		if (term.canChooseCourse()) {
			List<TermCourse> courses = new ArrayList<TermCourse>();
			Iterator<TermCourse> allCourses = term.getCourses().iterator();
			while (allCourses.hasNext()) {
				TermCourse course = allCourses.next();
				if (course.getCourseStudents().size() < course.getStudentsLimit()) {
					courses.add(course);
				}
			}
			return courses;
		}
		return null;
	}
}
