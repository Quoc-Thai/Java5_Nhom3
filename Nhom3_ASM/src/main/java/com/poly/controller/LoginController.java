package com.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.DAO.TaiKhoanDAO;
import com.poly.model.TaiKhoan;
import com.poly.service.CookieService;
import com.poly.service.ParamService;
import com.poly.service.SessionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class LoginController {
	@Autowired
	TaiKhoanDAO taiKhoanDAO;
	@Autowired
	HttpServletRequest request;
	@Autowired
	CookieService cookieService;
	@Autowired
	SessionService sessionService;
	@Autowired
	ParamService paramService;

	@GetMapping("/login")
	public String display(@ModelAttribute("account") TaiKhoan account,
			@CookieValue(name = "rememberUser", defaultValue = "") String cookie) {
		if (!cookie.isEmpty()) {
			sessionService.set("currentUser", cookie);
			return "redirect:/index";
		}
		return "login";
	}

	@PostMapping("/login/submit")
	public String submit(Model model, @Valid @ModelAttribute("account") TaiKhoan account, BindingResult result,
			@RequestParam("username") String username, @RequestParam("password") String password) {
		if (result.hasErrors()) {
			return "/login";
		}
		Boolean rm = paramService.getBoolean("remember", false);
		var taikhoan = taiKhoanDAO.findAll();
		for (TaiKhoan tk : taikhoan) {
			if (tk.getUsername().equals(username) && tk.getPassword().equals(password)) {
				sessionService.set("currentUser", username);
				if (rm.equals(true)) {
					cookieService.addCookie("rememberUser", username, 10);
				} else {
					cookieService.removeCookie("rememberUser");
				}
				return "redirect:/index";
			}
		}
		model.addAttribute("message", "Tài khoản hoặc mật khẩu không đúng");
		return "redirect:/login";
	}
}
