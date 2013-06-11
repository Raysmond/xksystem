package com.raysmond.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.raysmond.hibernate.ChooseCourseStatus;
import com.raysmond.hibernate.ConcreteTerm;
import com.raysmond.hibernate.Student;
import com.raysmond.hibernate.Term;
import com.raysmond.hibernate.TermCourse;
import com.raysmond.hibernate.controller.IChooseCourseController;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

/**
 * Test IChooseCourseController obviously. Functionalities to be tested:
 * choose/drop course, follow/defollow course, get available courses
 * 
 * @author raysmond
 * 
 */
public class TestIChooseCourseController extends HibernateBaseTest {

	@Autowired
	IChooseCourseController controller;

	@Test
	 @Rollback(false)
	public void testChooseCourse() {
		// create a new term
		Term term = Term.create(2013, ChooseCourseStatus.STARTED,
				ConcreteTerm.SECOND_TERM, getPersistenceManager());

		// create a new course with 5 students limit
		TermCourse course = TermCourse.create(term, "OOT", "Z2207", 5,
				getPersistenceManager());

		// create 5 students and let them choose the course above
		List<Student> students = new ArrayList<Student>();
		for (int i = 0; i < 5; ++i) {
			Student student = Student.create("student_" + i, "1030024_" + i,
					getPersistenceManager());
			students.add(student);
			assertTrue(controller.chooseCourse(student, course));
		}

		// create the 6th student
		Student student = Student.create("student_6", "1030024_6",
				getPersistenceManager());
		// let the 6th student choose the course
		assertFalse(controller.chooseCourse(student, course));
		assertEquals(5, course.getStudents().size());

		TermCourse savedCourse = getPersistenceManager().get(TermCourse.class,
				course.getId());
		assertEquals(5, savedCourse.getStudents().size());

		for (int i = 0; i < 5; ++i) {
			Student studentTmp = getPersistenceManager().get(Student.class,
					students.get(i).getId());
			assertEquals(1, studentTmp.getChoosedCourses().size());
			assertEquals(course, studentTmp.getChoosedCourses().iterator()
					.next());
		}
	}

	@Test
	public void testDropCourse() {
		// create a new term
		Term term = Term.create(2013, ChooseCourseStatus.STARTED,
				ConcreteTerm.SECOND_TERM, getPersistenceManager());

		// create a new course
		TermCourse course = TermCourse.create(term, "OOT", "Z2207", 50,
				getPersistenceManager());

		// create a list of students with size 5 and let them choose the course
		List<Student> students = new ArrayList<Student>();
		for (int i = 0; i < 5; i++) {
			Student student = Student.create("student_" + i, "1030024_" + i,
					getPersistenceManager());
			students.add(student);
			assertTrue(controller.chooseCourse(student, course));
		}

		// before dropping course, there are 5 students in the course
		TermCourse savedCourse = getPersistenceManager().get(TermCourse.class,
				course.getId());
		assertEquals(5, savedCourse.getStudents().size());

		// let the first student drop the course
		controller.dropCourse(students.get(0), course);

		// after one student dropping course, there are 4 students in the course
		TermCourse savedCourseAfterDropping = getPersistenceManager().get(
				TermCourse.class, course.getId());
		assertEquals(4, savedCourseAfterDropping.getStudents().size());
		assertFalse(savedCourseAfterDropping.getStudents().contains(
				students.get(0)));

		// check all boundaries
		// let other students drop the course
		for (int i = 1; i < 5; ++i) {
			controller.dropCourse(students.get(i), course);
			// after one student dropping course, there are 4 students in the
			// course
			TermCourse savedCourseAfterDroppingTmp = getPersistenceManager()
					.get(TermCourse.class, course.getId());
			assertEquals(4 - i, savedCourseAfterDroppingTmp.getStudents()
					.size());
			assertFalse(savedCourseAfterDroppingTmp.getStudents().contains(
					students.get(i)));
		}
	}

	@Test
	// @Rollback(false)
	public void testGetAvailableCourses() {
		Term term = Term.create(2013, ChooseCourseStatus.STARTED,
				ConcreteTerm.SECOND_TERM, getPersistenceManager());
		TermCourse course0 = TermCourse.create(term, "OOT", "Z2207", 5,
				getPersistenceManager());
		TermCourse course1 = TermCourse.create(term, "OOT", "Z2208", 7,
				getPersistenceManager());
		TermCourse course2 = TermCourse.create(term, "OOT", "Z2209", 9,
				getPersistenceManager());

		this.assertObjectPersisted(course0);
		this.assertObjectPersisted(course1);
		this.assertObjectPersisted(course2);
		assertEquals(3, term.getCourses().size());

		for (int i = 0; i < 5; i++) {
			Student student = Student.create("student_" + i, "1030024_" + i,
					getPersistenceManager());
			assertTrue(controller.chooseCourse(student, course0));
			assertTrue(controller.chooseCourse(student, course1));
			assertTrue(controller.chooseCourse(student, course2));
		}

		List<TermCourse> courses = controller.getAvailableCourses(term);
		assertNotNull(courses);
		assertEquals(2, courses.size());
		assertNotSame(course0, courses.get(0));
		assertNotSame(course0, courses.get(1));
	}

	@Test
	// @Rollback(false)
	public void testStudentFollowTermCourse() {
		// create a new term
		Term term = Term.create(2013, ChooseCourseStatus.STARTED,
				ConcreteTerm.SECOND_TERM, getPersistenceManager());

		// create a new course
		TermCourse course = TermCourse.create(term, "OOT", "Z2207", 5,
				getPersistenceManager());

		// create students
		Student student0 = Student.create("student0", "1024065",
				getPersistenceManager());
		Student student1 = Student.create("student1", "1024060",
				getPersistenceManager());

		controller.followCourse(student0, course);
		controller.followCourse(student1, course);

		// saved course should has two students following it
		TermCourse savedCourse = getPersistenceManager().get(TermCourse.class,
				course.getId());
		assertEquals(2, savedCourse.getFollowStudents().size());
		Iterator<Student> students = savedCourse.getFollowStudents().iterator();
		assertEquals(students.next(), student0);
		assertEquals(students.next(), student1);

		// student0 saved should has the course above
		TermCourse courseFollowedByStudent0 = getPersistenceManager()
				.get(Student.class, student0.getId()).getFollowedCourses()
				.iterator().next();
		assertEquals(course, courseFollowedByStudent0);

		// student1 saved should has the course above
		TermCourse courseFollowedByStudent1 = getPersistenceManager()
				.get(Student.class, student1.getId()).getFollowedCourses()
				.iterator().next();
		assertEquals(course, courseFollowedByStudent1);

	}

	@Test
	public void testStudentDefollowCourse() {
		// create a new term
		Term term = Term.create(2013, ChooseCourseStatus.STARTED,
				ConcreteTerm.SECOND_TERM, getPersistenceManager());

		// create a new course
		TermCourse course = TermCourse.create(term, "OOT", "Z2207", 5,
				getPersistenceManager());

		// create students
		Student student0 = Student.create("student0", "1024065",
				getPersistenceManager());
		Student student1 = Student.create("student1", "1024060",
				getPersistenceManager());

		controller.followCourse(student0, course);
		controller.followCourse(student1, course);

		// saved course should has two students following it
		TermCourse savedCourse = getPersistenceManager().get(TermCourse.class,
				course.getId());
		assertEquals(2, savedCourse.getFollowStudents().size());

		controller.defollowCourse(student1, savedCourse);

		// after student1 drop the course, the course should only have one
		// following student and he should be student0
		TermCourse savedCourseAfterDefollow = getPersistenceManager().get(
				TermCourse.class, course.getId());
		assertEquals(1, savedCourseAfterDefollow.getFollowStudents().size());
		assertEquals(student0, savedCourseAfterDefollow.getFollowStudents()
				.iterator().next());
	}

}
