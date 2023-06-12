package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.poly.DAO.LoaiHangDAO;
import com.poly.DAO.SanPhamDAO;
import com.poly.DAO.TaiKhoanDAO;
import com.poly.model.LoaiHang;
import com.poly.model.TaiKhoan;
import com.poly.service.CookieService;
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
	TaiKhoanDAO taiKhoanDAO;
	@Autowired
	LoaiHangDAO loaiHangDAO;
	@Autowired
	SessionService sessionService;
	@Autowired
	CookieService cookieService;
	@Autowired
	SanPhamDAO sanPhamDAO;

	@GetMapping("/index")
	public String login(Model model,
			@CookieValue(name = "rememberUser", defaultValue = "", required = false) String cookie) {
		if (!cookie.isBlank() && session.getAttribute("currentUser") == null) {
			TaiKhoan tk = taiKhoanDAO.findById(cookie).get();
			sessionService.set("currentUser", tk);
		}
		return "index";
	}

	@GetMapping("/logout")
	public String logout() {
		cookieService.removeCookie("rememberUser");
		session.removeAttribute("currentUser");
		return "redirect:/index";
	}
	
	@ModelAttribute("categories")
	public List<LoaiHang> getLoai(){
		List<LoaiHang> categories = loaiHangDAO.findAll();
		return categories;
	}

}
