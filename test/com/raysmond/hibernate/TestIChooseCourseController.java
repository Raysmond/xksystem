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

import com.raysmond.hibernate.controller.IChooseCourseController;
import com.raysmond.hibernate.controller.ICourseController;
import com.raysmond.util.OutputObject;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class TestIChooseCourseController extends HibernateBaseTest {

	@Autowired
	IChooseCourseController controller;

	@Test
	// @Rollback(false)
	public void testChooseCourse() {
		Term term = Term.create(2013, ChooseCourseStatus.STARTED,
				ConcreteTerm.SECOND_TERM, getPersistenceManager());
		
		TermCourse course = TermCourse.create(term, "OOT", "Z2207", 5,
				getPersistenceManager());
		
		for(int i=0;i<5;i++){
			Student student = Student.create("student_" + i,"1030024_"+ i , getPersistenceManager());
			assertTrue(controller.chooseCourse(student, course));
		}
		
		Student student = Student.create("student_6","1030024_6" , getPersistenceManager());
		assertFalse(controller.chooseCourse(student, course));
		
		assertEquals(5,course.getStudents().size());
		
		String hql = "from TermCourse course where course.id=:id";
		Query query = getPersistenceManager().createQuery(hql).setParameter("id", course.getId());
		List<TermCourse> result = query.list();
		assertEquals(1,result.size());
		assertEquals(5,result.get(0).getStudents().size());
	}

	@Test
	public void testDropCourse() {
		Term term = Term.create(2013, ChooseCourseStatus.STARTED,
				ConcreteTerm.SECOND_TERM, getPersistenceManager());
		
		TermCourse course = TermCourse.create(term, "OOT", "Z2207", 50,
				getPersistenceManager());
		List<Student> students = new ArrayList<Student>();
		for(int i=0;i<5;i++){
			Student student = Student.create("student_" + i,"1030024_"+ i , getPersistenceManager());
			students.add(student);
			assertTrue(controller.chooseCourse(student, course));
		}
		
		controller.dropCourse(students.get(0), course);
		
		String hql = "from TermCourse course where course.id=:id";
		Query query = getPersistenceManager().createQuery(hql).setParameter("id", course.getId());
		List<TermCourse> result = query.list();
		assertEquals(1,result.size());
		assertEquals(4,result.get(0).getStudents().size());
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
		assertEquals(3,term.getCourses().size());
		
		for(int i=0;i<5;i++){
			Student student = Student.create("student_" + i,"1030024_"+ i , getPersistenceManager());
			assertTrue(controller.chooseCourse(student, course0));
			assertTrue(controller.chooseCourse(student, course1));
			assertTrue(controller.chooseCourse(student, course2));
		}

		List<TermCourse> courses = controller.getAvailableCourses(term);
		assertNotNull(courses);
		assertEquals(2,courses.size());
		assertNotSame(course0.getId().intValue(),courses.get(0).getId().intValue());
		assertNotSame(course0.getId().intValue(),courses.get(1).getId().intValue());
	}

}
