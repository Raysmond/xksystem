package com.raysmond.hibernate;

import org.junit.Test;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class TestCourseSchedule extends HibernateBaseTest {

	@Test
	public void testCreateCourseSchedule() {
		CourseSchedule schedule = CourseSchedule.create(WeekDay.Monday, 3, 4,
				getPersistenceManager());
		this.assertObjectPersisted(schedule);
	}

	@Test
	public void testCourseScheduleOverlap() {
		CourseSchedule schedule0 = CourseSchedule.create(WeekDay.Monday, 3, 6,
				getPersistenceManager());
		CourseSchedule schedule1 = CourseSchedule.create(WeekDay.Monday, 1, 2,
				getPersistenceManager());
		assertFalse(schedule0.isOverlapped(schedule1));
		assertFalse(schedule1.isOverlapped(schedule0));

		CourseSchedule schedule2 = CourseSchedule.create(WeekDay.Monday, 1, 4,
				getPersistenceManager());
		CourseSchedule schedule3 = CourseSchedule.create(WeekDay.Monday, 4, 5,
				getPersistenceManager());

		// 1<3<4<6
		assertTrue(schedule2.isOverlapped(schedule0));
		// 1<=1<2<4
		assertTrue(schedule2.isOverlapped(schedule1));
		// 3<4<5<6
		assertTrue(schedule3.isOverlapped(schedule0));

		CourseSchedule schedule4 = CourseSchedule.create(WeekDay.Friday, 1, 4,
				getPersistenceManager());
		CourseSchedule schedule5 = CourseSchedule.create(WeekDay.Thursday, 4,
				5, getPersistenceManager());

		// three schedules which have overlaps in hour but no overlaps in week
		// day and all should assert false
		assertFalse(schedule4.isOverlapped(schedule0));
		assertFalse(schedule4.isOverlapped(schedule1));
		assertFalse(schedule5.isOverlapped(schedule0));
	}

}
