package com.raysmond.hibernate;


import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class TestTerm extends HibernateBaseTest {

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
	public void testFindTerm(){
		Term term = new Term();
		term.setYear(2014);
		term.setConcreteTerm(ConcreteTerm.FIRST_TERM);
		getPersistenceManager().save(term);
		Term result = new Term().findTerm(2014, ConcreteTerm.FIRST_TERM, getPersistenceManager());
		if(result!=null) assertTrue(true);
		else fail("cannot find term");
	}
}
