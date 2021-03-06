package com.raysmond.hibernate;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import edu.fudan.ss.persistence.hibernate.common.BaseModelObject;
import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

@Entity
public class CourseSchedule extends BaseModelObject {
	// 上课周时，表示星期几上课
	private WeekDay weekday;
	// 上课课时
	@Embedded
	private ClassHour classhour;
	@ManyToOne
	private TermCourse course;

	public CourseSchedule(){
		
	}
	public CourseSchedule(WeekDay weekday, int begin, int end) {
		this.setWeekday(weekday);
		this.setClasshour(new ClassHour(begin,end));
	}

	public static CourseSchedule create(WeekDay day, ClassHour hour,
			IPersistenceManager pm) {
		CourseSchedule schedule = new CourseSchedule();
		schedule.setWeekday(day);
		schedule.setClasshour(hour);
		pm.save(schedule);
		return schedule;
	}

	public static CourseSchedule create(WeekDay day, Integer hourBegin,
			Integer hourEnd, IPersistenceManager pm) {
		CourseSchedule schedule = new CourseSchedule();
		schedule.setWeekday(day);
		ClassHour hour = new ClassHour(hourBegin,hourEnd);
		schedule.setClasshour(hour);
		pm.save(schedule);
		return schedule;
	}

	/**
	 * To check whether two course schedules have overlaps
	 * 
	 * @param schedule
	 * @return
	 */
	public boolean isOverlapped(CourseSchedule schedule) {
		if (schedule.getWeekday() == weekday
				&& schedule.getClasshour().isOverlapped(classhour)) {
			return true;
		}
		return false;
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

	public TermCourse getCourse() {
		return course;
	}

	public void setCourse(TermCourse course) {
		this.course = course;
	}
	
	
}
