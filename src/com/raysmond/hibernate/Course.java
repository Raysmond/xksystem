package com.raysmond.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;

import edu.fudan.ss.persistence.hibernate.common.BaseModelObject;

@Entity
public abstract class Course extends BaseModelObject {
	
    @Column(name = "course_id", nullable = false)
	private String courseId;
	
    @Column(name = "course_name", nullable = false)
	private String name;

    /**
     * Check whether two course has the same courseId
     * @param course
     * @return
     */
    public boolean isEqualCourseId(Course course){
		return getCourseId().equals(course.getCourseId());
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    
}
