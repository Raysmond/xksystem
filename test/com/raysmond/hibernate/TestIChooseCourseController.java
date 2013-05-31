package com.raysmond.hibernate;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.raysmond.hibernate.controller.IChooseCourseController;
import com.raysmond.hibernate.controller.ICourseController;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class TestIChooseCourseController extends HibernateBaseTest{

	@Autowired
	IChooseCourseController controller;
	
	@Test
	public void testChooseCourse() {
		fail("not implemented yet");
	}
	
	@Test
	public void testDropCourse(){
		fail("not implemented yet");
	}
	
	@Test
	public void testGetAvailableCourses(){
		fail("not implemented yet");
	}
	
}
