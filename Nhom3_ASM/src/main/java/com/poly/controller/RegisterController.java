package com.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.DAO.GioHangDAO;
import com.poly.DAO.KhachHangDAO;
import com.poly.DAO.TaiKhoanDAO;
import com.poly.model.KhachHang;
import com.poly.model.TaiKhoan;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class RegisterController {
	@Autowired
	TaiKhoanDAO taiKhoanDAO;

	@Autowired
	KhachHangDAO khachHangDAO;

	@Autowired
	GioHangDAO gioHangDAO;

	@Autowired
	HttpSession session;

	@GetMapping("/dangki/account")
	public String dangki() {
		return "register0";
	}

	@PostMapping("/dangki/account/submit")
	public String dangki1(Model model, @ModelAttribute("account") TaiKhoan taiKhoan,
			@RequestParam("password1") String retype) {
		TaiKhoan tk = new TaiKhoan(taiKhoan.getUsername(), taiKhoan.getPassword(), false, null, null, null);
		if (!tk.getPassword().equals(retype)) {
			model.addAttribute("message", "Mật khẩu nhập lại không trùng khớp");
			return "register0";
		} else {
			var taikhoancheck = taiKhoanDAO.findAll();
			for (TaiKhoan tkcheck : taikhoancheck) {
				if (tkcheck.getUsername().equals(tk.getUsername())) {
					model.addAttribute("message", "Tài khoản đã tồn tại");
					return "register0";
				}
			}
			session.setAttribute("account", taiKhoan);
			return "redirect:/dangki/info";
		}
	}

	@GetMapping("/dangki/info")
	public String dangki2(Model model, @ModelAttribute("customer") KhachHang kh) {
		return "register";
	}

	@PostMapping("/dangki/info/submit")
	public String dangki2(Model model, @Valid @ModelAttribute("customer") KhachHang kh, BindingResult result) {
		if (result.hasErrors()) {
			model.addAttribute("customer", kh);
			return "register";
		}
		khachHangDAO.save(kh);
		TaiKhoan tk = (TaiKhoan) session.getAttribute("account");
		tk.setKhachHang(kh);
		taiKhoanDAO.save(tk);
		return "redirect:/login";
	}

	/*
	 * @PostMapping("/dangki/submit") public String
	 * submit(@Valid @ModelAttribute("account") TaiKhoan
	 * account,@Valid @ModelAttribute("customer") KhachHang khachHang, BindingResult
	 * result,
	 * 
	 * @RequestParam("username") String username, @RequestParam("password") String
	 * password,
	 * 
	 * @RequestParam("ngaySinh") String ngaysinh, @RequestParam("hoKH") String ho,
	 * 
	 * @RequestParam("tenKH") String ten, @RequestParam("diaChi") String diachi,
	 * 
	 * @RequestParam("dienThoai") String dienthoai, @RequestParam("email") String
	 * email) { TaiKhoan tk = new TaiKhoan(); KhachHang kh = new KhachHang();
	 * GioHang gh = new GioHang(); kh.setDiaChi(diachi); kh.setDienThoai(dienthoai);
	 * kh.setEmail(email); kh.setHoKH(ho); kh.setTenKH(ten);
	 * kh.setNgaySinh(ngaysinh); gh.setTaiKhoan(tk); tk.setUsername(username);
	 * tk.setPassword(password); tk.setKhachHang(kh); khachHangDAO.save(kh);
	 * taiKhoanDAO.save(tk); gioHangDAO.save(gh); return "redirect:/index"; }
	 */

}
