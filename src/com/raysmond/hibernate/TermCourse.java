package com.raysmond.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

@Entity
public class TermCourse extends Course{
	
	private String address;			//上课地点
	private Integer studentsLimit;	//人数限制
	@ManyToOne
	private Teacher teacher;
	//学期
	@ManyToOne
	private Term term = new Term();
	//上课时间
	@OneToMany(mappedBy = "course", cascade = { CascadeType.ALL })
	private Collection<CourseSchedule> schedule = new ArrayList<CourseSchedule>();
	
	//上课学生
	@ManyToMany(
	           cascade={CascadeType.PERSIST,CascadeType.MERGE},
	           mappedBy="choosedCourses"
	    )
	private Collection<Student> students = new ArrayList<Student>();
	
	public TermCourse(){
		
	}
	
	public Collection<Student> getStudents() {
		return students;
	}

	public void setStudents(Collection<Student> students) {
		this.students = students;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Collection<CourseSchedule> getSchedule() {
		return schedule;
	}

	public void setSchedule(Collection<CourseSchedule> schedule) {
		this.schedule = schedule;
	}

	public static TermCourse create(Term term, String name,String address,
			Integer studentsLimit, IPersistenceManager pm) {
		TermCourse result = new TermCourse();
		result.setTerm(term);
		result.setName(name);
		result.setAddress(address);
		result.setStudentsLimit(studentsLimit);
		pm.save(result);
		return result;
	}
	
	/**
	 * Whether a student has already chosen the course
	 * @param student
	 * @return 
	 */
	public boolean isStudentInCourse(Student student){
		Iterator<Student> iter = students.iterator();
		boolean hasChosen = false;
		while(iter.hasNext()){
			if(iter.next().getId()==student.getId()){
				hasChosen = true;
				break;
			}
		}
		return hasChosen;
	}
	
	public boolean removeAstudent(Student student){
		Iterator<Student> iter = students.iterator();
		while(iter.hasNext()){
			Student curStudent = iter.next();
			if(curStudent.getId()==student.getId()){
				return students.remove(curStudent);
			}
		}
		return false;
	}
	
	public Term getTerm() {
		return term;
	}
	public void setTerm(Term term) {
		this.term = term;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getStudentsLimit() {
		return studentsLimit;
	}
	public void setStudentsLimit(Integer studentsLimit) {
		this.studentsLimit = studentsLimit;
	}
}
