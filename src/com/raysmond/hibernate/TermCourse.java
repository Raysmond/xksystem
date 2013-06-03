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
public class TermCourse extends Course {

	private String address;

	private Integer studentsLimit;

	@ManyToOne
	private Teacher teacher;

	// course term
	@ManyToOne
	private Term term = new Term();

	// course schedule
	@OneToMany(mappedBy = "course", cascade = { CascadeType.ALL })
	private Collection<CourseSchedule> schedule = new ArrayList<CourseSchedule>();

	// the students in the course
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "choosedCourses")
	private Collection<Student> courseStudents = new ArrayList<Student>();

	// the students following the course
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "followedCourses")
	private Collection<Student> followStudents = new ArrayList<Student>();

	public static TermCourse create(Term term, String name, String address,
			Integer studentsLimit, IPersistenceManager pm) {
		TermCourse result = new TermCourse();
		result.setTerm(term);
		result.setName(name);
		result.setAddress(address);
		result.setStudentsLimit(studentsLimit);
		term.getCourses().add(result);
		pm.save(result);
		return result;
	}

	/**
	 * To check whether two courses have conflict in address and schedule at the
	 * same time
	 * 
	 * @param course
	 * @return
	 */
	public boolean isCourseConflict(TermCourse course) {
		boolean conflict = false;
		boolean addressConflict = course.getAddress().equals(getAddress());
		Iterator<CourseSchedule> schedules1 = course.getSchedule().iterator();
		while (schedules1.hasNext()) {
			CourseSchedule _schedule1 = schedules1.next();
			Iterator<CourseSchedule> schedules0 = this.getSchedule().iterator();
			while (schedules0.hasNext()) {
				// Check every two schedules in the two course whether there are
				// conflicts both in address and schedule
				CourseSchedule _schedule0 = schedules0.next();
				if (addressConflict && _schedule0.isOverlapped(_schedule1)) {
					// detect one conflict both in address and schedule
					conflict = true;
					break;
				}
			}
		}
		return conflict;
	}

	/**
	 * Add new schedule to the course
	 * 
	 * @param schedule
	 * @param pm
	 * @return
	 */
	public boolean addCourseSchedule(CourseSchedule schedule,
			IPersistenceManager pm) {
		Iterator<CourseSchedule> iter = getSchedule().iterator();
		while (iter.hasNext()) {
			// the new schedule cannot have overlaps within the schedules of the
			// course itself
			if (iter.next().isOverlapped(schedule))
				return false;
		}
		this.getSchedule().add(schedule);
		// after adding new schedule, the course cannot have overlaps with
		// others courses
		if (!getTerm().isConflictCourse(this)) {
			schedule.setCourse(this);
			pm.save(schedule);
			pm.save(this);
			return true;
		} else {
			this.getSchedule().remove(schedule);
			return false;
		}
	}

	//-------------------
	//getters and setters
	
	public Collection<Student> getFollowStudents() {
		return followStudents;
	}

	public void setFollowStudents(Collection<Student> followStudents) {
		this.followStudents = followStudents;
	}

	public Collection<Student> getStudents() {
		return courseStudents;
	}

	public void setStudents(Collection<Student> courseStudents) {
		this.courseStudents = courseStudents;
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
