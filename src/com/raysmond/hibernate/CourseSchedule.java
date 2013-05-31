package com.raysmond.hibernate;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import edu.fudan.ss.persistence.hibernate.common.BaseModelObject;

@Entity
public class CourseSchedule extends BaseModelObject {
	//�Ͽ���ʱ����ʾ���ڼ��Ͽ�
	private WeekDay weekday;
	//�Ͽο�ʱ
	@Embedded
	private ClassHour classhour;
	@ManyToOne
	private TermCourse course;
	
	public TermCourse getCourse() {
		return course;
	}
	public void setCourse(TermCourse course) {
		this.course = course;
	}
	public WeekDay getWeekday() {
		return weekday;
	}
	public void setWeekday(WeekDay weekday) {
		this.weekday = weekday;
	}
	public ClassHour getClasshour() {
		return classhour;
	}
	public void setClasshour(ClassHour classhour) {
		this.classhour = classhour;
	}
}
