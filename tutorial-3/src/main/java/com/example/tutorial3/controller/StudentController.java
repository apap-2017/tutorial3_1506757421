package com.example.tutorial3.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tutorial3.model.StudentModel;
import com.example.tutorial3.service.InMemoryStudentService;
import com.example.tutorial3.service.StudentService;

@Controller
public class StudentController {
	private final StudentService studentService;

	public StudentController() {
		studentService = new InMemoryStudentService();
	}
	
	@RequestMapping("/student/add")
	public String add(@RequestParam(value = "npm", required = true) String npm, @RequestParam(value = "name", required = true) String name, @RequestParam(value = "gpa", required = true) double gpa) {
		StudentModel student = new StudentModel(name, npm, gpa);
		studentService.addStudent(student);
		return "add";
	}
	
//	@RequestMapping("/student/view")
//	public String view(Model model, @RequestParam(value = "npm", required = false) String npm) {
//		StudentModel student = studentService.selectStudent(npm);
//		model.addAttribute("student", student);
//		return "view";
//	}
	
	@RequestMapping("/student/viewall")
	public String viewAll(Model model) {
		List<StudentModel> students = studentService.selectAllStudents();
		model.addAttribute("students", students);
		return "viewall";
	}
	
	@RequestMapping(value = {"student/view", "student/view/{npm}"})
	public String viewPath(@PathVariable Optional<String> npm, Model model) {
		if(npm.isPresent()) {
			StudentModel student = studentService.selectStudent(npm.get());
			if(student == null) {
				model.addAttribute("message", "NPM " + npm.get() + " does not exist");
				model.addAttribute("student", new StudentModel("", "", 0));
			} else {
				model.addAttribute("message", "Student Data");
				model.addAttribute("student", student);
			}
		} else {
			model.addAttribute("message", "Insert NPM!");
			model.addAttribute("student", new StudentModel("", "", 0));
		}
		return "view";
	}
	
	@RequestMapping(value = {"student/delete", "student/delete/{npm}"})
	public String deletePath(@PathVariable Optional<String> npm, Model model) {
		if(npm.isPresent()) {
			StudentModel student = studentService.selectStudent(npm.get());
			if(student == null) {
				model.addAttribute("message", "Delete canceled. NPM " + npm.get() + " does not exist");
			} else {
				studentService.deleteStudent(npm.get());
				model.addAttribute("message", "The deletion of student of NPM " + npm.get() + " is successful");
			}
		} else {
			model.addAttribute("message", "Delete canceled. You did not fill the NPM field");
		}
		return "delete";
	}
}
