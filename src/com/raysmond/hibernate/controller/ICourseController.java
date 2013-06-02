package com.raysmond.hibernate.controller;

import java.util.Collection;
import java.util.List;

import com.raysmond.hibernate.CourseSchedule;
import com.raysmond.hibernate.Teacher;
import com.raysmond.hibernate.Term;
import com.raysmond.hibernate.TermCourse;

/**
 * 课程管理控制器接口
 * 
 * @author Raysmond
 * 
 */
public interface ICourseController {

	// 开设课程，注册学期课程的基本信息
	public TermCourse registerTermCourse(String name, String address,
			Integer studentsLimit,Collection<CourseSchedule> schedules, Teacher teacher, Term term);

	// 学期选课开始
	public void startChoosingCourse(Term term);

	// 学期选课结束
	public void endChoosingCourse(Term term);

	// 准备学期选课
	public void prepareTermCourse(Term term);

	// 调整选课人数
	public boolean ajustCourseStudentLimit(TermCourse course,
			Integer studentsLimit);
}
