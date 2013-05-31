package com.raysmond.hibernate;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
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
		//create a term
		Term term = Term.create(2013, ChooseCourseStatus.NOT_STARTED,
				ConcreteTerm.ONE, getPersistenceManager());
		
		//create a teacher
		Teacher teacher = Teacher.create("OOTeacher","1102", getPersistenceManager());
		
		//create course schedules
		Collection<CourseSchedule> schedules = new ArrayList<CourseSchedule>();
		CourseSchedule schedule1 = new CourseSchedule();
		schedule1.setWeekday(WeekDay.Monday);
		ClassHour hour = new ClassHour();
		hour.setBegin(3);
		hour.setEnd(4);
		schedule1.setClasshour(hour);
		CourseSchedule schedule2 = new CourseSchedule();
		schedule2.setWeekday(WeekDay.Wednesday);
		ClassHour hour1 = new ClassHour();
		hour1.setBegin(6);
		hour1.setEnd(7);
		schedule2.setClasshour(hour1);
		schedules.add(schedule1);
		schedules.add(schedule2);
		//这里不用save，在TermCourse save的时候会自动save
		//this.getPersistenceManager().save(schedule1);
		//this.assertObjectPersisted(schedule2);
		//this.getPersistenceManager().save(schedule1);
		//this.assertObjectPersisted(schedule2);
		
		//create course students
		Collection<Student> students = new ArrayList<Student>();
		Student student1 = new Student();
		student1.setStudentNumber("110");
		Student student2 = new Student();
		student2.setStudentNumber("111");
		students.add(student1);
		students.add(student2);
		this.getPersistenceManager().save(student1);
		this.assertObjectPersisted(student1);
		this.getPersistenceManager().save(student2);
		this.assertObjectPersisted(student2);
		
		//create a term course
		TermCourse course = new TermCourse();
		course.setAddress("Z2207");
		course.setName("OOT_2013");
		course.setStudentsLimit(50);
		course.setSchedule(schedules);
		schedule1.setCourse(course);
		schedule2.setCourse(course);
		course.setTeacher(teacher);
		teacher.getCourses().add(course);
		course.setStudents(students);
		student1.getChoosedCourses().add(course);
		student2.getChoosedCourses().add(course);
		course.setTerm(term);
		term.getCourses().add(course);
		//save course
		getPersistenceManager().save(course);
		this.assertObjectPersisted(course);
		
		String hql = "from TermCourse tc where tc.name = :name";
		Query query = getPersistenceManager().createQuery(hql)
				.setParameter("name", "OOT_2013");
		List<TermCourse> courses = query.list();
		assertEquals(1,courses.size());
		assertEquals("OOT_2013",courses.get(0).getName());
	}
	
}
