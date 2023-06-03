package com.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poly.DAO.SanPhamDAO;
import com.poly.model.SanPham;

@Controller
public class TestSQL {
	@Autowired
	SanPhamDAO sanPhamDAO;

	@ResponseBody
	@GetMapping("/sanpham")
	public String index(Model model) {
		var sanphams = sanPhamDAO.findAll();
		for (SanPham sanPham : sanphams) {
			var products = sanPham.getThuongHieu();
			System.out.println(products.getTenTH());
		}
		return null;
	}
}
