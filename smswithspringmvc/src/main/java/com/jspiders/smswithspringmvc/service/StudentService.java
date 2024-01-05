package com.jspiders.smswithspringmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jspiders.smswithspringmvc.pojo.StudentPOJO;
import com.jspiders.smswithspringmvc.repository.StudentRepository;

@Component
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	public void addStudent(String name, String email, long mobile, byte age) {
		StudentPOJO studentPOJO = new StudentPOJO();
		studentPOJO.setName(name);
		studentPOJO.setEmail(email);
		studentPOJO.setMobile(mobile);
		studentPOJO.setAge(age);
		
		System.out.println("service");
		
		studentRepository.addStudent(studentPOJO);
	}

	public List<StudentPOJO> getAllStudents() {
		List<StudentPOJO> students = studentRepository.getAllStudents();
		return students;
	}

	public String deleteStudent(long id) {
		StudentPOJO studentToBeDeleted = null;
		List<StudentPOJO> students = studentRepository.getAllStudents();
		for (StudentPOJO student : students) {
			if (student.getId() == id) {
				studentToBeDeleted = student;
				break;
			}
		}
		if (studentToBeDeleted == null) {
			return "Student not found.";
		} else {
			studentRepository.deleteStudent(studentToBeDeleted);
			return "Student deleted.";
		}
	}

	public StudentPOJO editStudent(int id) {
		StudentPOJO studentToBeUpdated=null;
		List<StudentPOJO> students = studentRepository.getAllStudents();
		for (StudentPOJO student : students) {
			if (student.getId()==id) {
				studentToBeUpdated=student;
				break;
			}
		}
		return studentToBeUpdated;
	}

	public void updateStudent(int id, String name, String email, long mobile, byte age) {
		StudentPOJO student = studentRepository.getStudentById(id);
		student.setName(name);
		student.setEmail(email);
		student.setMobile(mobile);
		student.setAge(age);
		studentRepository.addStudent(student);
	}

	public List<StudentPOJO> searchStudentByPattern(String pattern) {
		return studentRepository.searchStudentByPattern(pattern);
	}

	public List<StudentPOJO> getAllStudentsInOrder() {
		List<StudentPOJO> students = studentRepository.getAllStudentsInOrder();
		return students;
	}

	
}
