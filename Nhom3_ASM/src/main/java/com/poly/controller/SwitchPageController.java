package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwitchPageController {
	
	
	@GetMapping("/about")
	public String about() {
		return "about";
	}

	@GetMapping("/product")
	public String product() {
		return "product";
	}

	@GetMapping("/blog")
	public String blog() {
		return "blog";
	}

	@GetMapping("/feature")
	public String feature() {
		return "feature";
	}

	@GetMapping("/testimonial")
	public String testimonial() {
		return "testimonial";
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
