package com.raysmond.test;

import org.junit.Test;

import com.raysmond.hibernate.ChooseCourseStatus;
import com.raysmond.hibernate.ClassHour;
import com.raysmond.hibernate.ConcreteTerm;
import com.raysmond.hibernate.CourseSchedule;
import com.raysmond.hibernate.DropCourseRecord;
import com.raysmond.hibernate.Student;
import com.raysmond.hibernate.Term;
import com.raysmond.hibernate.TermCourse;
import com.raysmond.hibernate.WeekDay;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class TestSimpleCreate extends HibernateBaseTest {

	@Test
	public void testCreateDropCourseRecrod() {
		// create a new term
		Term term = Term.create(2013, ChooseCourseStatus.STARTED,
				ConcreteTerm.SECOND_TERM, getPersistenceManager());

		// create a new course
		TermCourse course = TermCourse.create(term, "OOT", "Z2207", 50,
				getPersistenceManager());

		Student student = Student.create("student0", "1024065",
				getPersistenceManager());

		DropCourseRecord record = DropCourseRecord.create(student, course,
				getPersistenceManager());

		assertObjectPersisted(record);

		DropCourseRecord savedRecord = getPersistenceManager().get(
				DropCourseRecord.class, record.getId());

		assertEquals(savedRecord, record);
		assertEquals(savedRecord.getCourse(), course);
		assertEquals(savedRecord.getStudent(), student);

		assertEquals(student.getDropCourseRecord().size(), 1);
		assertEquals(course.getDropCourseRecord().size(), 1);

		assertEquals(student.getDropCourseRecord().iterator().next(), record);
		assertEquals(course.getDropCourseRecord().iterator().next(), record);
	}

	@Test
	public void testCreateCourseSchedule() {
		CourseSchedule schedule = CourseSchedule.create(WeekDay.Monday,
				new ClassHour(3, 4), getPersistenceManager());
		assertObjectPersisted(schedule);
	}
}
