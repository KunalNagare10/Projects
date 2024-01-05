package com.jspiders.smswithspringmvc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jspiders.smswithspringmvc.pojo.AdminPOJO;
import com.jspiders.smswithspringmvc.pojo.StudentPOJO;
import com.jspiders.smswithspringmvc.service.StudentService;

@Controller
public class StudentController {
	@Autowired
	private StudentService studentService;
	
	@RequestMapping(path = "/add_student", method = RequestMethod.GET)
	public String getPageToAddStudent(HttpSession httpSession) {
		AdminPOJO admin = (AdminPOJO) httpSession.getAttribute("admin");
		if (admin != null) {
			return "add_student";
		} else {
			return "log_in";
		}
	}
	
	@RequestMapping(path = "/add_student", method = RequestMethod.POST)
	public String addStudent(@RequestParam String name, @RequestParam String email, @RequestParam long mobile,
			@RequestParam byte age, ModelMap modelMap) {
		System.out.println("controller");
		studentService.addStudent(name, email, mobile, age);
		modelMap.addAttribute("message", "Student added.");
		return "add_student";
	}
	
	@RequestMapping(path="/get_students",method = RequestMethod.GET)
	public String getAllStudents(ModelMap modelMap,HttpSession httpSession) {
		AdminPOJO admin = (AdminPOJO) httpSession.getAttribute("admin");
		if (admin != null) {
			List<StudentPOJO> students = studentService.getAllStudents();
			modelMap.addAttribute("students", students);
			return "get_students";
		} else {
			return "log_in";
		}
	}
	
	@RequestMapping(path="/delete_student",method = RequestMethod.GET)
	public String getPageToDeleteStudent(ModelMap modelMap,HttpSession httpSession) {
		AdminPOJO admin = (AdminPOJO) httpSession.getAttribute("admin");
		if (admin != null) {
			List<StudentPOJO> students = studentService.getAllStudents();
			modelMap.addAttribute("students", students);
			return "delete_student";
		}else {
			return "log_in";
		}
	}
	
	@RequestMapping(path = "/delete_student", method = RequestMethod.POST)
	public String deleteStudent(@RequestParam long id, ModelMap modelMap) {
		String message = studentService.deleteStudent(id);
		modelMap.addAttribute("message", message);
		List<StudentPOJO> students = studentService.getAllStudents();
		modelMap.addAttribute("students", students);
		return "delete_student";
	}
	
	@RequestMapping(path = "/edit_student",method = RequestMethod.GET)
	public String getPageToEditStudent(ModelMap modelMap,HttpSession httpSession) {
		AdminPOJO admin = (AdminPOJO) httpSession.getAttribute("admin");
		if (admin != null) {
			List<StudentPOJO> students = studentService.getAllStudents();
			modelMap.addAttribute("students", students);
			return "edit_student";
		} else {
			return "log_in";
		}
	}
	
	@RequestMapping(path = "/edit_student",method = RequestMethod.POST)
	public String editStudent(@RequestParam int id, ModelMap modelMap) {
		StudentPOJO student = studentService.editStudent(id);
		modelMap.addAttribute("student", student);
		return "update_student";
	}
	@RequestMapping(path="/update_student",method = RequestMethod.POST)
	public String updateStudent(@RequestParam int id,@RequestParam String name,
			@RequestParam String email,@RequestParam long mobile,
			@RequestParam byte age,ModelMap modelMap ) {
		studentService.updateStudent(id,name,email,mobile,age);
		List<StudentPOJO> students = studentService.getAllStudents();
		modelMap.addAttribute("students", students);
		modelMap.addAttribute("message", "Student Updated");
		return "edit_student";
	}
	
	@RequestMapping(path = "/home_page",method = RequestMethod.GET)
	public String getHomePage(HttpSession httpSession) {
		AdminPOJO admin = (AdminPOJO) httpSession.getAttribute("admin");
		if (admin != null) {
			return "home_page";
		} else {
			return "log_in";
		}
	}
	
	@RequestMapping(path="/search", method = RequestMethod.GET)
	public String getPageToSearchStudent(HttpSession httpSession) {
		AdminPOJO admin = (AdminPOJO) httpSession.getAttribute("admin");
		if (admin != null) {
			return "search";
		} else {
			return "log_in";
		}
	}
	
	@RequestMapping(path="/search", method = RequestMethod.POST)
	public String searchStudentByPattern(@RequestParam String pattern, ModelMap modelMap) {
		List<StudentPOJO> studList = studentService.searchStudentByPattern(pattern);
		modelMap.addAttribute("students", studList);
		return "search";
	}
	
	@RequestMapping(path="/order",method = RequestMethod.GET)
	public String getPageOrder(ModelMap modelMap, HttpSession httpSession) {
		AdminPOJO admin = (AdminPOJO) httpSession.getAttribute("admin");
		if (admin != null) {
			List<StudentPOJO> students = studentService.getAllStudentsInOrder();
			modelMap.addAttribute("students", students);
			return "order";
		} else {
			return "log_in";
		}
	}
}
