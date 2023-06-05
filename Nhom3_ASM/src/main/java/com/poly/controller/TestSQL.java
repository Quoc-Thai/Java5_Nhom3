package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.DAO.SanPhamDAO;
import com.poly.model.SanPham;

@Controller
public class TestSQL {
	@Autowired
	SanPhamDAO spdao;

	
	@GetMapping("sp")
	public String sp() {
		return "sanpham";
		
	}
	
	
	@RequestMapping("sp")
	public String index(Model model) {
		SanPham item = new SanPham();
		model.addAttribute("item", item);
		List<SanPham> items = spdao.findAll();
		model.addAttribute("items", items);
		return "sanpham";
	}
	
}
