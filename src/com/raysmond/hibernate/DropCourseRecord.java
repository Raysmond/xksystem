package com.raysmond.hibernate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.fudan.ss.persistence.hibernate.common.BaseModelObject;
import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

@Entity
public class DropCourseRecord extends BaseModelObject {
	@Temporal(TemporalType.TIMESTAMP)
	// @Column(name = "Drop_Course_Time")
	private Date dropTime;

	@ManyToOne
	private TermCourse course;

	@ManyToOne
	private Student student;

	/**
	 * Create a new record for dropping course
	 * @param student
	 * @param course
	 * @param pm
	 * @return
	 */
	public static DropCourseRecord create(Student student, TermCourse course,
			IPersistenceManager pm) {
		DropCourseRecord record = new DropCourseRecord();
		record.setCourse(course);
		record.setStudent(student);
		record.setDropTime(new Date());

		// build associations
		student.getDropCourseRecord().add(record);
		course.getDropCourseRecord().add(record);

		pm.save(record);
		pm.save(student);
		pm.save(course);

		return record;
	}

	public Date getDropTime() {
		return dropTime;
	}

	public void setDropTime(Date dropTime) {
		this.dropTime = dropTime;
	}

	public TermCourse getCourse() {
		return course;
	}

	public void setCourse(TermCourse course) {
		this.course = course;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	
}
