package com.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.DAO.TaiKhoanDAO;
import com.poly.model.TaiKhoan;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
	@Autowired
	TaiKhoanDAO taiKhoanDAO;
	@Autowired
	HttpServletRequest request;

	@GetMapping("/login")
	public String display() {
		return "login";
	}

	@PostMapping("/login/submit")
	public String submit(Model model, @RequestParam("username") String username,
			@RequestParam("password") String password) {
		var taikhoan = taiKhoanDAO.findAll();
		for (TaiKhoan tk : taikhoan) {
			if (tk.getUsername().equals(username) && tk.getPassword().equals(password)) {
				return "redirect:/index";
			}
		}
		model.addAttribute("message", "Tài khoản hoặc mật khẩu không đúng");
		return "redirect:/login";
	}
}
