package com.jspiders.smswithspringmvc.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jspiders.smswithspringmvc.pojo.AdminPOJO;
import com.jspiders.smswithspringmvc.service.AdminService;

@Controller
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(path = "/sign_up", method = RequestMethod.GET)
	public String addAdmin() {
		return "sign_up";
	}	
	
	@RequestMapping(path = "/sign_up",method = RequestMethod.POST)
	public String addAdmin(@RequestParam String username, @RequestParam String email, 
			@RequestParam String password, ModelMap modelMap) {
		adminService.addAdmin(username,email,password);
		modelMap.addAttribute("message", "signed up");
		return "log_in";
	}
	
	@RequestMapping(path = "/log_in", method = RequestMethod.GET)
	public String getLogInPage() {
		return "log_in";
	}
	@RequestMapping(path = "/log_in", method = RequestMethod.POST)
	public String logIn(@RequestParam String email, 
			@RequestParam String password, ModelMap modelMap, HttpSession httpSession) {
		
		AdminPOJO admin = adminService.logIn(email, password);
		if (admin != null) {
			httpSession.setAttribute("admin", admin);
			httpSession.setMaxInactiveInterval(30000);
			modelMap.addAttribute("message", "Logged in");
			modelMap.addAttribute("admin", email);
			return "home_page";
		} else {
			modelMap.addAttribute("message", "Invalid Email or Password");
			return "log_in";
		}
	}
	@RequestMapping(path = "/log_out", method = RequestMethod.GET)
	public String logOut(HttpSession httpSession) {
		httpSession.invalidate();
		return "log_in";
	}
	
//	@RequestMapping(path = "/delete_admin", method = RequestMethod.GET)
//	public String deleteAdmin(@RequestParam String email, ModelMap modelMap, HttpSession httpSession) {
//		AdminPOJO admin = (AdminPOJO) httpSession.getAttribute("admin");
//		if (admin != null) {
//			adminService.deleteAdmin(email);
//			modelMap.addAttribute("message", "Admin deleted");
//		}
//		return "log_in";
//	}
}
