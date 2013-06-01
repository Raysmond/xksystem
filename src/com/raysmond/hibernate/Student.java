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
	// student number
	private String studentNumber;
	//Courses chosen by the student
	@ManyToMany(
            targetEntity=com.raysmond.hibernate.TermCourse.class,
            cascade ={CascadeType.PERSIST,CascadeType.MERGE},
            fetch=FetchType.LAZY
    )
	private Collection<TermCourse> choosedCourses = new ArrayList<TermCourse>();
	
	//Courses followed by the student
	@ManyToMany(
            targetEntity=com.raysmond.hibernate.TermCourse.class,
            cascade ={CascadeType.PERSIST,CascadeType.MERGE},
            fetch=FetchType.LAZY
    )
	private Collection<TermCourse> followedCourses = new ArrayList<TermCourse>();

	
	public static Student create(String name,String studentNumber,
			IPersistenceManager pm){
		Student student = new Student();
		student.setName(name);
		student.setStudentNumber(studentNumber);
		pm.save(student);
		return student;
	}
	
	public Collection<TermCourse> getFollowedCourses() {
		return followedCourses;
	}

	public void setFollowedCourses(Collection<TermCourse> followedCourses) {
		this.followedCourses = followedCourses;
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
