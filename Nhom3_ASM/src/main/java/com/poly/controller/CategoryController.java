package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.poly.DAO.LoaiHangDAO;
import com.poly.DAO.SanPhamDAO;
import com.poly.model.LoaiHang;
import com.poly.model.SanPham;

@Controller
public class CategoryController {
	@Autowired
	LoaiHangDAO categoryDAO;
	@Autowired
	SanPhamDAO sanPhamDAO;

	@GetMapping("/category")
	public String category(Model model) {
		List<LoaiHang> categories = categoryDAO.findAll();
		model.addAttribute("categories", categories);
		return "category";
	}

	@GetMapping("/category/{id}")
	public String categoryNo(@PathVariable Integer id, Model model) {
		List<SanPham> sanPhams = sanPhamDAO.getSanPhamByCategoryId(id);
		model.addAttribute("products", sanPhams);
		return "product";
	}
}
