package com.raysmond.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

@Entity
public class Teacher extends Account {
	// ΩÃ ¶π§∫≈
	private String jobNumber;
	
	// term courses thought by the teacher
	@OneToMany(mappedBy = "teacher")
	private Collection<TermCourse> courses = new ArrayList<TermCourse>();
	
	public static Teacher create(String name,String jobNumber,IPersistenceManager pm){
		Teacher teacher = new Teacher();
		teacher.setName(name);
		teacher.setJobNumber(jobNumber);
		pm.save(teacher);
		return teacher;
	}
	
	public Collection<TermCourse> getCourses() {
		return courses;
	}

	public void setCourses(Collection<TermCourse> courses) {
		this.courses = courses;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}
}
