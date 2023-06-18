package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

	@GetMapping("/admin")
	public String admin() {
		return "admin_html/dashboard";
	}

	@GetMapping("/admin/table")
	public String Table() {
		return "admin_html/basic-table";
	}

	@GetMapping("/admin/blank")
	public String Blank() {
		return "admin_html/blank";
	}

	@GetMapping("/admin/icon")
	public String Icon() {
		return "admin_html/fontawesome";
	}

	@GetMapping("/admin/profile")
	public String Profile() {
		return "admin_html/profile";
	}

	@GetMapping("/admin/product/add")
	public String add() {
		return "admin_html/addProduct";
	}

	@PostMapping("/admin/product/add/submit")
	public String addSp(Model model) {
		
		return null;
	}

}
