package com.raysmond.hibernate.controller;

import java.util.List;

import com.raysmond.hibernate.Student;
import com.raysmond.hibernate.Term;
import com.raysmond.hibernate.TermCourse;

/**
 * ѡ�ο������ӿ�
 * 
 * @author Raysmond
 * 
 */
public interface IChooseCourseController {
	
	// ѡ��
	public boolean chooseCourse(Student student, TermCourse course);

	// �˿�
	public boolean dropCourse(Student student, TermCourse course);

	// ��ע�γ�
	public boolean followCourse(Student student, TermCourse course);

	// �����ע�γ�
	public boolean defollowCourse(Student student, TermCourse course);

	// ��ȡ��ѡ�γ�
	public List<TermCourse> getAvailableCourses(Term term);
}
