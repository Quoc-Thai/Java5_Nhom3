package com.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.DAO.GioHangChiTietDAO;
import com.poly.DAO.GioHangDAO;
import com.poly.DAO.SanPhamDAO;
import com.poly.model.GioHang;
import com.poly.model.GioHangChiTiet;
import com.poly.model.SanPham;

@Controller
public class CartController {
	@Autowired
	GioHangDAO gioHangDAO;
	@Autowired
	SanPhamDAO sanPhamDAO;
	@Autowired
	GioHangChiTietDAO gioHangChiTietDAO;

	@PostMapping("/product/addToCart/{id}")
	public String addToCart(@PathVariable Integer id, Model model, @RequestParam("quantity") Integer quantity,
			@CookieValue(name = "rememberUser", defaultValue = "", required = true) String cookie) {
		SanPham sanPham = sanPhamDAO.findById(id).get();
		GioHang gioHang = gioHangDAO.getGioHangByUserId(cookie);
		GioHangChiTiet exist = gioHangChiTietDAO.getGioHangChiTietByMaSP(id);
		if (exist != null) {
			exist.setSoLuong(exist.getSoLuong() + quantity);
			gioHangChiTietDAO.save(exist);
		} else {
			GioHangChiTiet ghct = new GioHangChiTiet(null, quantity, sanPham, gioHang);
			gioHangChiTietDAO.save(ghct);
		}
		return "redirect:/cart";
	}

	@GetMapping("/cart")
	public String getCart(Model model,
			@CookieValue(name = "rememberUser", defaultValue = "", required = true) String cookie) {
		GioHang gh = gioHangDAO.getGioHangByUserId(cookie);
		Double totalCash = gioHangChiTietDAO.TotalCashHoaDon(cookie);
		model.addAttribute("total", totalCash);
		model.addAttribute("cart", gh);
		return "cart";
	}
}
