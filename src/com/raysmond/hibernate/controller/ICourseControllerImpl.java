package com.raysmond.hibernate.controller;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raysmond.hibernate.ChooseCourseStatus;
import com.raysmond.hibernate.ConcreteTerm;
import com.raysmond.hibernate.CourseSchedule;
import com.raysmond.hibernate.Teacher;
import com.raysmond.hibernate.Term;
import com.raysmond.hibernate.TermCourse;

import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

@Service
@Transactional
public class ICourseControllerImpl implements ICourseController {
	@Autowired
	private IPersistenceManager persistenceManager;

	@Override
	public void startChoosingCourse(Term term) {
		if (term.getStatus() != ChooseCourseStatus.STARTED) {
			term.setStatus(ChooseCourseStatus.STARTED);
			persistenceManager.save(term);
		}
	}

	@Override
	public void endChoosingCourse(Term term) {
		if (term.getStatus() != ChooseCourseStatus.END) {
			term.setStatus(ChooseCourseStatus.END);
			persistenceManager.save(term);
		}
	}

	@Override
	public Term prepareTermCourse(Integer year, ConcreteTerm cterm) {
		return Term.create(year, ChooseCourseStatus.NOT_STARTED, cterm,
				persistenceManager);
	}

	@Override
	public boolean ajustCourseStudentLimit(TermCourse course,
			Integer studentsLimit) {
		// students limit cannot be smaller than 0
		if (studentsLimit < 0) {
			return false;
		}
		// students limit cannot be smaller than the students
		// count who already chosen the course
		if (studentsLimit < course.getStudents().size()) {
			return false;
		}
		course.setStudentsLimit(studentsLimit);
		persistenceManager.save(course);
		return true;
	}

	@Override
	public TermCourse registerTermCourse(String name, String address,
			Integer studentsLimit, Collection<CourseSchedule> schedules,
			Teacher teacher, Term term) {
		// create a course instance
		TermCourse course = new TermCourse();
		course.setName(name);
		course.setAddress(address);
		course.setStudentsLimit(studentsLimit);
		course.setTeacher(teacher);
		course.setTerm(term);
		course.setSchedule(schedules);

		// check whether the course is a conflict course in the term
		if (!term.isConflictCourse(course)) {
			// add the course to the term
			term.getCourses().add(course);
			// build relations
			term.getCourses().add(course);
			teacher.getCourses().add(course);

			Iterator<CourseSchedule> iter = schedules.iterator();
			while (iter.hasNext()) {
				iter.next().setCourse(course);
			}

			persistenceManager.save(course);
		}

		return course;
	}
}
