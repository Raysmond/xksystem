package com.raysmond.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.raysmond.hibernate.ChooseCourseStatus;
import com.raysmond.hibernate.ConcreteTerm;
import com.raysmond.hibernate.CourseSchedule;
import com.raysmond.hibernate.Term;
import com.raysmond.hibernate.TermCourse;
import com.raysmond.hibernate.WeekDay;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class TestTermCourse extends HibernateBaseTest {

	@Test
	// @Rollback(false)
	public void testCreateTermCourse() {
		// create a term
		Term term = Term.create(2013, ChooseCourseStatus.NOT_STARTED,
				ConcreteTerm.FIRST_TERM, getPersistenceManager());
		TermCourse course = TermCourse.create(term, "OOT", "Z2207", 50,
				getPersistenceManager());
		this.assertObjectPersisted(course);

		TermCourse savedTermCourse = getPersistenceManager().get(
				TermCourse.class, course.getId());
		assertNotNull(savedTermCourse);
		assertEquals(savedTermCourse, course);
		assertEquals(savedTermCourse.getTerm(), course.getTerm());

		Term savedTerm = getPersistenceManager().get(Term.class, term.getId());
		assertTrue(savedTerm.getCourses().contains(course));
	}

	@Test
	public void testConflictCourse() {
		Term term = Term.create(2013, ChooseCourseStatus.NOT_STARTED,
				ConcreteTerm.FIRST_TERM, getPersistenceManager());
		TermCourse course0 = TermCourse.create(term, "OOT_0", "Z2207", 50,
				getPersistenceManager());
		TermCourse course1 = TermCourse.create(term, "Software Engineering",
				"Z2207", 50, getPersistenceManager());
		TermCourse course2 = TermCourse.create(term, "Computer Architecture",
				"Z2208", 50, getPersistenceManager());

		// schedule0 and schedule1 have no conflict
		CourseSchedule schedule0 = CourseSchedule.create(WeekDay.Monday, 3, 4,
				getPersistenceManager());
		CourseSchedule schedule1 = CourseSchedule.create(WeekDay.Monday, 5, 6,
				getPersistenceManager());

		course0.getSchedule().add(schedule0);
		course2.getSchedule().add(schedule0);
		course1.getSchedule().add(schedule1);

		assertFalse(course0.isCourseConflict(course1));
		assertFalse(course1.isCourseConflict(course0));

		CourseSchedule schedule2 = CourseSchedule.create(WeekDay.Friday, 1, 3,
				getPersistenceManager());
		course1.getSchedule().add(schedule2);

		assertFalse(course0.isCourseConflict(course1));
		assertFalse(course1.isCourseConflict(course0));

		CourseSchedule schedule3 = CourseSchedule.create(WeekDay.Monday, 1, 3,
				getPersistenceManager());
		course1.getSchedule().add(schedule3);

		// course0 and course2 have conflict both in schedules and address
		assertTrue(course0.isCourseConflict(course1));
		assertTrue(course1.isCourseConflict(course0));
		// course0 and course2 have conflict in schedules but on conflict in
		// address
		assertFalse(course2.isCourseConflict(course0));
		assertFalse(course0.isCourseConflict(course2));
	}

	@Test
	@Rollback(false)
	public void testAddNewScheduleToCourse() {
		Term term = Term.create(2013, ChooseCourseStatus.NOT_STARTED,
				ConcreteTerm.FIRST_TERM, getPersistenceManager());
		TermCourse course0 = TermCourse.create(term, "OOT_0", "Z2207", 50,
				getPersistenceManager());
		TermCourse course1 = TermCourse.create(term, "OOT_1", "Z2207", 50,
				getPersistenceManager());

		CourseSchedule schedule0 = new CourseSchedule(WeekDay.Friday, 3, 4);

		CourseSchedule schedule1 = new CourseSchedule(WeekDay.Friday, 5, 6);

		CourseSchedule schedule2 = new CourseSchedule(WeekDay.Friday, 5, 6);

		CourseSchedule schedule3 = new CourseSchedule(WeekDay.Friday, 3, 5);

		this.assertObjectPersisted(course0);
		this.assertObjectPersisted(course1);
		assertTrue(course0
				.addCourseSchedule(schedule0, getPersistenceManager()));
		assertTrue(course1
				.addCourseSchedule(schedule1, getPersistenceManager()));
		assertFalse(course0.addCourseSchedule(schedule2,
				getPersistenceManager()));
		assertFalse(course0.addCourseSchedule(schedule3,
				getPersistenceManager()));

		String hql = "from CourseSchedule s where s.course=:course";
		Query query = getPersistenceManager().createQuery(hql).setParameter(
				"course", course0);
		List<CourseSchedule> _schedules0 = query.list();
		assertEquals(1, _schedules0.size());
		CourseSchedule _schedule0 = _schedules0.get(0);
		assertEquals(_schedule0.getId(), schedule0.getId());

		Query query1 = getPersistenceManager().createQuery(hql).setParameter(
				"course", course1);
		List<CourseSchedule> _schedules1 = query1.list();
		assertEquals(1, _schedules1.size());
		CourseSchedule _schedule1 = _schedules1.get(0);
		assertEquals(_schedule1.getId(), schedule1.getId());
	}

}
