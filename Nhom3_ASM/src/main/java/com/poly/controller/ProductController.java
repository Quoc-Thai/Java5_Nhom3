package com.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.poly.DAO.SanPhamDAO;
import com.poly.model.SanPham;

@Controller
public class ProductController {
	@Autowired
	SanPhamDAO sanPhamDAO;

	@GetMapping("/product/{id}")
	public String display(Model model, @PathVariable Integer id) {
		SanPham sanPham = sanPhamDAO.findById(id).get();
		model.addAttribute("product", sanPham);
		return "productDetail";
	}
}
