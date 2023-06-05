package com.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poly.DAO.KhachHangDAO;
import com.poly.model.KhachHang;

@Controller
public class TestSQL {
	@Autowired
	KhachHangDAO khachHangDAO;

	@ResponseBody
	@GetMapping("/sanpham")
	public String index(Model model) {
		var sanphams = khachHangDAO.findAll();
		for (KhachHang sanPham : sanphams) {
			var products = sanPham.getTaiKhoan();
			System.out.println(products.getUsername());
		}
		return null;
	}
}
