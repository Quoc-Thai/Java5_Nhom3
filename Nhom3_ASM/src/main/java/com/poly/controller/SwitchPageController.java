package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwitchPageController {
	
	
	@GetMapping("/about")
	public String about() {
		return "about";
	}

	@GetMapping("/feature")
	public String feature() {
		return "feature";
	}

	@GetMapping("/contact")
	public String contact() {
		return "contact";
	}

	@GetMapping("/404")
	public String Error() {
		return "404";
	}
	
}
