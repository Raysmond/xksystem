package com.raysmond.hibernate;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Query;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.raysmond.hibernate.controller.ICourseController;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class TestICourseController extends HibernateBaseTest {

	@Autowired
	ICourseController courseController;

	@Test
	public void testRegisterTermCourse() {
		// create a term
		Term term = Term.create(2012, ChooseCourseStatus.NOT_STARTED,
				ConcreteTerm.FIRST_TERM, getPersistenceManager());

		// create a teacher
		Teacher teacher = Teacher.create("OOTeacher", "1111",
				getPersistenceManager());

		TermCourse course = courseController.registerTermCourse("OOT_2012",
				"Z2207", 50, teacher, term);
		this.assertObjectPersisted(course);
	}

	@Test
	public void testStartChoosingCourse() {
		// create a term
		Term term = Term.create(2012, ChooseCourseStatus.NOT_STARTED,
				ConcreteTerm.FIRST_TERM, getPersistenceManager());
		this.assertObjectPersisted(term);
		
		courseController.startChoosingCourse(term);
		
		String hql = "from Term t where t.id = :id";
		Query query = getPersistenceManager().createQuery(hql)
				.setParameter("id", term.getId());
		List<Term> terms = query.list();
		assertEquals(1,terms.size());
		assertEquals(ChooseCourseStatus.STARTED,terms.get(0).getStatus());
	}

	@Test
	public void testEndChoosingCourse() {
		// create a term
		Term term = Term.create(2012, ChooseCourseStatus.NOT_STARTED,
				ConcreteTerm.FIRST_TERM, getPersistenceManager());
		this.assertObjectPersisted(term);
		
		courseController.endChoosingCourse(term);
		
		String hql = "from Term t where t.id = :id";
		Query query = getPersistenceManager().createQuery(hql)
				.setParameter("id", term.getId());
		List<Term> terms = query.list();
		assertEquals(1,terms.size());
		assertEquals(ChooseCourseStatus.END,terms.get(0).getStatus());		
	}

	@Test
	public void testPrepareChooseCourse() {
		fail("not implemented yet");
	}

	@Test
	public void testAjustCourseStudentLimit() {
		// create a term
		Term term = Term.create(2012, ChooseCourseStatus.NOT_STARTED,
				ConcreteTerm.FIRST_TERM, getPersistenceManager());

		// create a teacher
		Teacher teacher = Teacher.create("OOTeacher", "1111",
				getPersistenceManager());

		// save course
		TermCourse course = courseController.registerTermCourse("OOT_2012",
				"Z2207", 50, teacher, term);
		this.assertObjectPersisted(course);

		// ajust students limit from 50 to 60
		courseController.ajustCourseStudentLimit(course, 60);

		// find the course from DB and assert students limit
		String hql = "from Course c where c.id=:id";
		Query query = getPersistenceManager().createQuery(hql).setParameter(
				"id", course.getId());
		List<TermCourse> courses = query.list();
		
		assertEquals(1, courses.size());
		assertEquals(60, courses.get(0).getStudentsLimit().intValue());
	}
}
