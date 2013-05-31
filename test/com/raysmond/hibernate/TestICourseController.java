package com.raysmond.hibernate;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.raysmond.hibernate.controller.ICourseController;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class TestICourseController extends HibernateBaseTest {

	@Autowired
	ICourseController courseController;
	
	@Test
	public void testRegisterTermCourse(){
		//create a term
		Term term = Term.create(2012, ChooseCourseStatus.NOT_STARTED,
				ConcreteTerm.ONE, getPersistenceManager());
		
		//create a teacher
		Teacher teacher = Teacher.create("OOTeacher","1111", getPersistenceManager());
		
		TermCourse course = courseController.registerTermCourse(
				"OOT_2012", "Z2207", 50, teacher, term);
		this.assertObjectPersisted(course);
	}
	
	@Test
	public void testStartChoosingCourse(){
		fail("not implemented yet");
	} 
	
	@Test
	public void testEndChoosingCourse(){
		fail("not implemented yet");
	}
	
	@Test
	public void testPrepareChooseCourse(){
		fail("not implemented yet");
	}
	
	@Test
	public void testAjustCourseStudentLimit(){
		fail("not implemented yet");
	}
}
