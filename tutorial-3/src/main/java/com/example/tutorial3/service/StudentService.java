package com.example.tutorial3.service;

import java.util.List;

import com.example.tutorial3.model.StudentModel;

public interface StudentService {
	StudentModel selectStudent(String model);
	
	List<StudentModel> selectAllStudents();
	
	void addStudent(StudentModel student);
	
	void deleteStudent (String model);
}
