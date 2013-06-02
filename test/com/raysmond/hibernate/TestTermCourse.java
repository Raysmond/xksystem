package com.raysmond.hibernate;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

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

		String hql = "from Term t where t.id=:id";
		Query query = getPersistenceManager().createQuery(hql).setParameter(
				"id", term.getId());
		List<Term> terms = query.list();
		assertEquals(1, terms.size());
		Iterator<TermCourse> iter = terms.get(0).getCourses().iterator();
		boolean result = false;
		while (iter.hasNext()) {
			if (iter.next().getId().compareTo(course.getId()) == 0) {
				result = true;
				break;
			}
		}
		assertTrue(result);
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

}
