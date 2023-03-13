package com.juaracoding.mafjavaweb.controller;

import com.juaracoding.mafjavaweb.model.Student;
import com.juaracoding.mafjavaweb.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/school")
public class StudentController {
	
	private StudentService studentService;

	@Autowired
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}
	
	// handler method to handle list students and return mode and view
	@GetMapping("/v1/students")
	public String listStudents(Model model) {
		model.addAttribute("students", studentService.getAllStudents());
		return "students";
	}
	
	@GetMapping("/v1/students/new")
	public String createStudentForm(Model model) {
		
		// create student object to hold student form data
		Student student = new Student();
		model.addAttribute("student", student);
		return "create_student";
		
	}
	
	@PostMapping("/v1/students")
	public String saveStudent(@ModelAttribute("student") Student student) {
		studentService.saveStudent(student);
		return "redirect:/api/school/v1/students";
	}
	
	@GetMapping("/v1/students/edit/{id}")
	public String editStudentForm(@PathVariable Long id, Model model) {
		model.addAttribute("student", studentService.getStudentById(id));
		return "edit_student";
	}

	@PostMapping("/v1/students/{id}")
	public String updateStudent(@PathVariable Long id,
			@ModelAttribute("student") Student student,
			Model model) {
		
		// get student from database by id
		Student existingStudent = studentService.getStudentById(id);
		existingStudent.setId(id);
		existingStudent.setFirstName(student.getFirstName());
		existingStudent.setLastName(student.getLastName());
		existingStudent.setEmail(student.getEmail());
		
		// save updated student object
		studentService.updateStudent(existingStudent);
		return "redirect:/api/school/v1/students";
	}
	
	// handler method to handle delete student request
	
	@GetMapping("/v1/students/{id}")
	public String deleteStudent(@PathVariable Long id) {
		studentService.deleteStudentById(id);
		return "redirect:/students";
	}
	
}
