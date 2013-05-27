package com.raysmond.hibernate;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Query;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class TestTermCourse extends HibernateBaseTest {

	@Test
	// @Rollback(false)
	public void testCreateTermCourse() {
		Term term = new Term();
		term.setYear(2018);
		term.setConcreteTerm(ConcreteTerm.ONE);
		this.getPersistenceManager().save(term);
		this.assertObjectPersisted(term);
		
		TermCourse course = TermCourse.create(term, 
				"OOT2010", "Z2207", 40, getPersistenceManager());
		
		this.assertObjectPersisted(course);
	}
}
