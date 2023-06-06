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
	SanPhamDAO spdao;

	@GetMapping("/sp")
	public String index(Model model) {
		var items = spdao.findAll();
		for (SanPham sanPham : items) {
			System.out.println(sanPham.getGiaSP());
		}
		model.addAttribute("items", items);
		return "sanpham";
	}
}
