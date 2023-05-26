package com.poly.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.poly.model.SanPham;

@Controller
public class SwitchPageController {

	@GetMapping("/index")
	public String login() {
		return "index";
	}

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

	@ModelAttribute("products")
	public List<SanPham> getPro() {
		List<SanPham> list = new ArrayList<SanPham>();
		SanPham sanPham1 = new SanPham(null, "Bông tai 1", 1000000.0, 800000.0, null, null,
				"img/product/BongTai/bt1.png", null, null);
		SanPham sanPham2 = new SanPham(null, "Bông tai 2", 2500000.0, 2000000.0, null, null,
				"img/product/BongTai/bt2.png", null, null);
		SanPham sanPham3 = new SanPham(null, "Bông tai 3", 2500000.0, 2000000.0, null, null,
				"img/product/BongTai/bt3.png", null, null);
		list.add(sanPham1);
		list.add(sanPham2);
		list.add(sanPham3);
		return list;
	}
}
