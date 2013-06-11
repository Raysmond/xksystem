package com.raysmond.hibernate.controller;

import java.util.List;

import com.raysmond.hibernate.Student;
import com.raysmond.hibernate.Term;
import com.raysmond.hibernate.TermCourse;

/**
 * 选课控制器接口
 * 
 * @author Raysmond
 * 
 */
public interface IChooseCourseController {
	
	// 选课
	public boolean chooseCourse(Student student, TermCourse course);

	// 退课
	public boolean dropCourse(Student student, TermCourse course);

	// 关注课程
	public boolean followCourse(Student student, TermCourse course);

	// 解除关注课程
	public boolean defollowCourse(Student student, TermCourse course);

	// 获取可选课程
	public List<TermCourse> getAvailableCourses(Term term);
}
