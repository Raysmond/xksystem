package com.raysmond.hibernate;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

@Entity
public class Student extends Account {
	//学号
	private String studentNumber;
	//所选课程
	//-------------------------
	@ManyToMany(
            targetEntity=com.raysmond.hibernate.TermCourse.class,
            cascade ={CascadeType.PERSIST,CascadeType.MERGE},
            fetch=FetchType.LAZY
    )
	private Collection<TermCourse> choosedCourses = new ArrayList<TermCourse>();

	public static Student create(String name,String studentNumber,
			IPersistenceManager pm){
		Student student = new Student();
		student.setName(name);
		student.setStudentNumber(studentNumber);
		pm.save(student);
		return student;
		
	}
	
	public Collection<TermCourse> getChoosedCourses() {
		return choosedCourses;
	}

	public void setChoosedCourses(Collection<TermCourse> choosedCourses) {
		this.choosedCourses = choosedCourses;
	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}
}
