package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

	@GetMapping("/admin/index")
	public String admin() {
		return "admin/index";
	}

	@GetMapping("/tables")
	public String Table() {
		return "admin/tables";
	}

}
