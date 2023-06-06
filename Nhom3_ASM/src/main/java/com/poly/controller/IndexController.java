package com.poly.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.poly.model.SanPham;
import com.poly.service.SessionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {
	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpSession session;
	@Autowired
	SessionService sessionService;

	@GetMapping("/index")
	public String login(Model model, @CookieValue(name = "rememberUser", defaultValue = "") String cookie) {
		if (!cookie.isEmpty()) {
			sessionService.set("currentUser", cookie);
		}
		return "index";
	}

	@ModelAttribute("productsA")
	public List<SanPham> getBongTai() {
		List<SanPham> list = new ArrayList<SanPham>();
		SanPham sanPham1 = new SanPham(null, "Bông tai 1", 1000000.0, 800000.0, null, null,
				"img/product/BongTai/bt1.png", null, null, null, null, null);
		SanPham sanPham2 = new SanPham(null, "Bông tai 2", 2500000.0, 2000000.0, null, null,
				"img/product/BongTai/bt2.png", null, null, null, null, null);
		SanPham sanPham3 = new SanPham(null, "Bông tai 3", 2500000.0, 2000000.0, null, null,
				"img/product/BongTai/bt3.png", null, null, null, null, null);
		SanPham sanPham4 = new SanPham(null, "Bông tai 4", 2500000.0, 2000000.0, null, null,
				"img/product/BongTai/bt4.png", null, null, null, null, null);
		list.add(sanPham1);
		list.add(sanPham2);
		list.add(sanPham3);
		list.add(sanPham4);
		return list;
	}

	@ModelAttribute("productsB")
	public List<SanPham> getDongHo() {
		List<SanPham> list = new ArrayList<SanPham>();
		SanPham sanPham1 = new SanPham(null, "Đồng Hồ 1", 1000000.0, 800000.0, null, null,
				"img/product/DongHo/CalvinKlein/dh1.jpg", null, null, null, null, null);
		SanPham sanPham2 = new SanPham(null, "Đồng Hồ 2", 1000000.0, 800000.0, null, null,
				"img/product/DongHo/CalvinKlein/dh2.jpg", null, null, null, null, null);
		SanPham sanPham3 = new SanPham(null, "Đồng Hồ 3", 1000000.0, 800000.0, null, null,
				"img/product/DongHo/JustCavalii/dh7.jpg", null, null, null, null, null);
		SanPham sanPham4 = new SanPham(null, "Đồng Hồ 4", 1000000.0, 800000.0, null, null,
				"img/product/DongHo/JustCavalii/dh8.jpg", null, null, null, null, null);
		list.add(sanPham1);
		list.add(sanPham2);
		list.add(sanPham3);
		list.add(sanPham4);
		return list;
	}

	@ModelAttribute("productsC")
	public List<SanPham> getNhan() {
		List<SanPham> list = new ArrayList<SanPham>();
		SanPham sanPham1 = new SanPham(null, "Nhẫn 1", 1000000.0, 800000.0, null, null, "img/product/NhanBac/anh1.png",
				null, null, null, null, null);
		SanPham sanPham2 = new SanPham(null, "Nhẫn 2", 2500000.0, 2000000.0, null, null, "img/product/NhacBac/anh2.png",
				null, null, null, null, null);
		SanPham sanPham3 = new SanPham(null, "Nhẫn 3", 2500000.0, 2000000.0, null, null, "img/product/NhanBac/anh3.png",
				null, null, null, null, null);
		SanPham sanPham4 = new SanPham(null, "Nhẫn 4", 2500000.0, 2000000.0, null, null, "img/product/NhanBac/anh4.png",
				null, null, null, null, null);
		list.add(sanPham1);
		list.add(sanPham2);
		list.add(sanPham3);
		list.add(sanPham4);
		return list;
	}
}
