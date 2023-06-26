package com.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.DAO.KhachHangDAO;
import com.poly.DAO.TaiKhoanDAO;
import com.poly.model.KhachHang;
import com.poly.model.TaiKhoan;
import com.poly.service.CookieService;
import com.poly.service.ParamService;
import com.poly.service.SessionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class LoginController {
	@Autowired
	TaiKhoanDAO taiKhoanDAO;
	@Autowired
	KhachHangDAO khachHangDAO;
	@Autowired
	HttpServletRequest request;
	@Autowired
	CookieService cookieService;
	@Autowired
	SessionService sessionService;
	@Autowired
	ParamService paramService;
	@Autowired
	HttpSession session;
	String name;

	@GetMapping("/login")
	public String display(@ModelAttribute("account") TaiKhoan account) {
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
				TaiKhoan acc = taiKhoanDAO.findById(username).get();
				if (acc.getActivated() == false) {
					model.addAttribute("message", "Tài khoản đã bị khóa vui lòng liên hệ với web để được hỗ trợ");
					return "login";
				} else {
					sessionService.set("currentUser", acc);
					if (rm.equals(true)) {
						cookieService.addCookie("rememberUser", username, 10);
					} else {
						cookieService.removeCookie("rememberUser");
					}
					return "redirect:/index";
				}
			}
		}
		model.addAttribute("message", "Tài khoản hoặc mật khẩu không đúng");
		return "login";
	}

///////////////////////////////////////////////////////////////////////////////////////
	@GetMapping("/account/changePassword")
	public String form() {
		return "changePassword";
	}

	@PostMapping("/account/changePassword/submit")
	public String DoiMatKhau(Model model, @RequestParam("passOld") String passOld,
			@RequestParam("passNew") String passNew, @RequestParam("passNew1") String passNew1) {
		TaiKhoan user = (TaiKhoan) session.getAttribute("currentUser");
		TaiKhoan tk = taiKhoanDAO.findById(user.getUsername()).get();
		if (passOld.equals("") || passNew.equals("") || passNew1.equals("")) {
			model.addAttribute("message", "Vui lòng điền đủ thông tin");
			return "changePassword";
		} else if (!passNew.equals(passNew1)) {
			model.addAttribute("message", "Vui lòng nhập chính xác mật khẩu mới");
			return "changePassword";
		} else if(!user.getPassword().equals(passOld)) {
			model.addAttribute("message", "Vui lòng nhập chính xác mật khẩu cũ");
			return "changePassword";
		}
		else 
			{tk.setPassword(passNew1);
			taiKhoanDAO.save(tk);
			return "redirect:/index";}	
	}

///////////////////////////////////////////////////////////////////////////
	@GetMapping("/account/info")
	public String thongTin(Model model, @ModelAttribute("khachHang") KhachHang khachHang) {
		TaiKhoan user = (TaiKhoan) session.getAttribute("currentUser");
		TaiKhoan item = taiKhoanDAO.findById(user.getUsername()).get();
		model.addAttribute("item", item.getKhachHang());
		return "info";
	}

	@PostMapping("/account/info/submit")
	public String changeInfo(Model model, @Valid @ModelAttribute("khachHang") KhachHang kh, BindingResult result) {
		if (result.hasErrors()) {
			model.addAttribute("item", kh);
			return "info";
		} else {
			TaiKhoan user = (TaiKhoan) session.getAttribute("currentUser");
			TaiKhoan tk = taiKhoanDAO.findById(user.getUsername()).get();
			KhachHang kh1 = khachHangDAO.findById(tk.getKhachHang().getMaKH()).get();
			kh1.setHoKH(kh.getHoKH());
			kh1.setEmail(kh.getEmail());
			kh1.setTenKH(kh.getTenKH());
			kh1.setNgaySinh(kh.getNgaySinh());
			kh1.setDienThoai(kh.getDienThoai());
			kh1.setDiaChi(kh.getDiaChi());
			khachHangDAO.save(kh1);
			return "redirect:/index";
		}
	}

}
