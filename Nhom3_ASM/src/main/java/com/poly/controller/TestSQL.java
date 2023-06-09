package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.poly.DAO.SanPhamDAO;
import com.poly.model.SanPham;

@Controller
public class TestSQL {
	@Autowired
	SanPhamDAO sanPhamDAO;

	@GetMapping("/sanpham")
	public String index(Model model) {
		List<SanPham> sanphams = sanPhamDAO.findAll();
		for (SanPham sanPham : sanphams) {
			var products = sanPham.getAvailable();
			System.out.println(products);
		}
		model.addAttribute("items", sanphams);
		return "sanpham";
	}
}
