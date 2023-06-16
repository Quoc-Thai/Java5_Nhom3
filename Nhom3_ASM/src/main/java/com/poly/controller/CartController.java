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
import com.poly.model.TaiKhoan;

import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {
	@Autowired
	GioHangDAO gioHangDAO;
	@Autowired
	SanPhamDAO sanPhamDAO;
	@Autowired
	GioHangChiTietDAO gioHangChiTietDAO;
	@Autowired
	HttpSession session;

	@PostMapping("/account/addToCart/{id}")
	public String addToCart(@PathVariable Integer id, Model model, @RequestParam("quantity") Integer quantity,
			@CookieValue(name = "rememberUser", defaultValue = "", required = true) String cookie) {
		TaiKhoan user = (TaiKhoan) session.getAttribute("currentUser");
		SanPham sanPham = sanPhamDAO.findById(id).get();
		GioHang gioHang = gioHangDAO.getGioHangByUserId(user.getUsername());
		GioHangChiTiet exist = gioHangChiTietDAO.getGioHangChiTietByMaSP(id);
		if (exist != null) {
			Integer num = exist.getSoLuong() + quantity;
			if (num > sanPham.getTonKho()) {
				exist.setSoLuong(sanPham.getTonKho());
			} else {
				exist.setSoLuong(num);
			}
			gioHangChiTietDAO.save(exist);
		} else {
			GioHangChiTiet ghct = new GioHangChiTiet(null, quantity, sanPham, gioHang);
			gioHangChiTietDAO.save(ghct);
		}
		return "redirect:/category";
	}

	@GetMapping("/account/cart")
	public String getCart(Model model) {
		TaiKhoan user = (TaiKhoan) session.getAttribute("currentUser");
		GioHang gh = gioHangDAO.getGioHangByUserId(user.getUsername());
		Integer totalProduct = gh.getGioHangCT().size();
		Double totalCash = gioHangChiTietDAO.TotalCashHoaDon(user.getUsername());
		model.addAttribute("totalProduct", totalProduct);
		model.addAttribute("totalCash", totalCash);
		model.addAttribute("cart", gh);
		return "cart";
	}

	@PostMapping("/account/cart/edit/{id}")
	public String editCart(Model model, @PathVariable Integer id, @RequestParam("quantity") Integer quantity) {
		GioHangChiTiet ghct = gioHangChiTietDAO.findById(id).get();
		if (quantity > ghct.getSoLuong()) {
			ghct.setSoLuong(ghct.getSoLuong());
		} else {
			ghct.setSoLuong(quantity);
		}
		gioHangChiTietDAO.save(ghct);
		return "redirect:/account/cart";
	}

	@PostMapping("/account/cart/remove/{id}")
	public String deleteCart(Model model, @PathVariable Integer id) {
		TaiKhoan user = (TaiKhoan) session.getAttribute("currentUser");
		GioHang gh = gioHangDAO.getGioHangByUserId(user.getUsername());
		gioHangChiTietDAO.deleteItem(gh.getMaGH(), id);
		return "redirect:/account/cart";
	}
}
