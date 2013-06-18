package com.raysmond.hibernate;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

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
	@JoinTable(name = "Student_Choose_Course") 
	private Collection<TermCourse> choosedCourses = new ArrayList<TermCourse>();

	//Courses followed by the student
	@ManyToMany(
            targetEntity=com.raysmond.hibernate.TermCourse.class,
            cascade ={CascadeType.PERSIST,CascadeType.MERGE},
            fetch=FetchType.LAZY
    )
	@JoinTable(name = "Student_Follow_Course") 
	private Collection<TermCourse> followedCourses = new ArrayList<TermCourse>();

	@OneToMany(mappedBy = "student")
	private Collection<DropCourseRecord> dropCourseRecord =  new ArrayList<DropCourseRecord>();

	public static Student create(String name,String studentNumber,
			IPersistenceManager pm){
		Student student = new Student();
		student.setName(name);
		student.setStudentNumber(studentNumber);
		pm.save(student);
		return student;
	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public Collection<TermCourse> getChoosedCourses() {
		return choosedCourses;
	}

	public void setChoosedCourses(Collection<TermCourse> choosedCourses) {
		this.choosedCourses = choosedCourses;
	}

	public Collection<TermCourse> getFollowedCourses() {
		return followedCourses;
	}

	public void setFollowedCourses(Collection<TermCourse> followedCourses) {
		this.followedCourses = followedCourses;
	}

	public Collection<DropCourseRecord> getDropCourseRecord() {
		return dropCourseRecord;
	}

	public void setDropCourseRecord(Collection<DropCourseRecord> dropCourseRecord) {
		this.dropCourseRecord = dropCourseRecord;
	}
	
}
