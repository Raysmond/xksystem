package com.raysmond.hibernate.controller;

import com.raysmond.hibernate.Teacher;
import com.raysmond.hibernate.Term;
import com.raysmond.hibernate.TermCourse;

/**
 * �γ̹���������ӿ�
 * 
 * @author Raysmond
 * 
 */
public interface ICourseController {

	// ����γ̣�ע��ѧ�ڿγ̵Ļ�����Ϣ
	public TermCourse registerTermCourse(String name, String address,
			Integer studentsLimit, Teacher teacher, Term term);

	// ѧ��ѡ�ο�ʼ
	public void startChoosingCourse(Term term);

	// ѧ��ѡ�ν���
	public void endChoosingCourse(Term term);

	// ׼��ѧ��ѡ��
	public void prepareTermCourse(Term term);

	// ����ѡ������
	public boolean ajustCourseStudentLimit(TermCourse course,
			Integer studentsLimit);
}
