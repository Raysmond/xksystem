package com.raysmond.test;


import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.raysmond.hibernate.ChooseCourseStatus;
import com.raysmond.hibernate.ClassHour;
import com.raysmond.hibernate.ConcreteTerm;
import com.raysmond.hibernate.CourseSchedule;
import com.raysmond.hibernate.Teacher;
import com.raysmond.hibernate.Term;
import com.raysmond.hibernate.TermCourse;
import com.raysmond.hibernate.WeekDay;
import com.raysmond.hibernate.controller.ICourseController;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class TestTerm extends HibernateBaseTest {

	@Autowired
	ICourseController courseController;
	
	@Test
   // @Rollback(false)
	public void testCreateTerm() {
		Term term = new Term();
		term.setYear(2013);
		term.setConcreteTerm(ConcreteTerm.FIRST_TERM);
		this.getPersistenceManager().save(term);
		this.assertObjectPersisted(term);
	}
	
	@Test
	public void testIsConflictTermCourse(){
		// create a term
		Term term = Term.create(2012, ChooseCourseStatus.NOT_STARTED,
				ConcreteTerm.FIRST_TERM, getPersistenceManager());

		// create a teacher
		Teacher teacher = Teacher.create("OOTeacher", "1111",
				getPersistenceManager());
		
		// create schedules
		Collection<CourseSchedule> schedules0 = new ArrayList<CourseSchedule>();
		Collection<CourseSchedule> schedules2 = new ArrayList<CourseSchedule>();
		Collection<CourseSchedule> schedules1 = new ArrayList<CourseSchedule>();
		
		CourseSchedule schedule0 = new CourseSchedule();
		CourseSchedule schedule0_1 = new CourseSchedule();
		CourseSchedule schedule1 = new CourseSchedule();
		CourseSchedule schedule1_1 = new CourseSchedule();
		CourseSchedule schedule2 = new CourseSchedule();
		
		schedule0.setWeekday(WeekDay.Monday);
		schedule0_1.setWeekday(WeekDay.Monday);
		schedule0.setClasshour(new ClassHour(6,7));
		schedule0_1.setClasshour(new ClassHour(6,7));
		
		schedule1.setWeekday(WeekDay.Wednesday);
		schedule1.setClasshour(new ClassHour(8,9));
		schedule1_1.setWeekday(WeekDay.Wednesday);
		schedule1_1.setClasshour(new ClassHour(8,9));
		
		schedule2.setWeekday(WeekDay.Monday);
		schedule2.setClasshour(new ClassHour(6,8));
		
		schedules0.add(schedule0);
		schedules0.add(schedule1);
		schedules2.add(schedule0_1);
		schedules2.add(schedule1_1);
		schedules1.add(schedule2);

		// register courses
		TermCourse course0 = courseController.registerTermCourse("OOT_2012",
				"Z2207", 50,schedules0, teacher, term);
		TermCourse course1 = courseController.registerTermCourse("OOT_2012",
				"Z2208", 50,schedules2, teacher, term);	
		TermCourse course2 = courseController.registerTermCourse("OOT_2012",
				"Z2207", 50,schedules1, teacher, term);	
		
		assertObjectPersisted(course0);
		assertObjectPersisted(course1);
		assertNull(course2.getId());
	}
	
}
