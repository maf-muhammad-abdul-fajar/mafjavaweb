package com.juaracoding.mafjavaweb.service;

import com.juaracoding.mafjavaweb.model.Student;
import com.juaracoding.mafjavaweb.repo.StudentRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

	private StudentRepo studentRepository;

	public StudentService(StudentRepo studentRepository) {
		this.studentRepository = studentRepository;
	}

	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	public Student saveStudent(Student student) {
		return studentRepository.save(student);
	}

	public Student getStudentById(Long id) {
		return studentRepository.findById(id).get();
	}

	public Student updateStudent(Student student) {
		return studentRepository.save(student);
	}

	public void deleteStudentById(Long id) {
		studentRepository.deleteById(id);	
	}

}
