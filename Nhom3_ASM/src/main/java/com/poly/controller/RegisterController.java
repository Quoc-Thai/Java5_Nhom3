package com.poly.controller;

import java.util.Random;

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
import com.poly.service.MailerServiceImpl;

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

	@Autowired
	MailerServiceImpl mailer;

	TaiKhoan tempAcc = null;
	KhachHang tempInfo = null;
	String code = null;

	@GetMapping("/register/account")
	public String dangki1(@ModelAttribute("account") TaiKhoan taiKhoan) {
		return "preRegister";
	}

	@PostMapping("/register/account/submit")
	public String dangki1(Model model, @Valid @ModelAttribute("account") TaiKhoan taiKhoan, BindingResult result,
			@RequestParam("password1") String retype) {
		if (result.hasErrors()) {
			model.addAttribute("account", taiKhoan);
			return "preRegister";
		} else {
			TaiKhoan tk = new TaiKhoan(taiKhoan.getUsername(), taiKhoan.getPassword(), false, null, null, null);
			if (!tk.getPassword().equals(retype)) {
				model.addAttribute("message", "Mật khẩu nhập lại không trùng khớp");
				return "preRegister";
			} else {
				var taikhoancheck = taiKhoanDAO.findAll();
				for (TaiKhoan tkcheck : taikhoancheck) {
					if (tkcheck.getUsername().equals(tk.getUsername()) && tkcheck.getActivated() == true) {
						model.addAttribute("message", "Tài khoản đã tồn tại");
						return "preRegister";
					}
				}
//				session.setAttribute("account", taiKhoan);
				tempAcc = taiKhoan;
				return "redirect:/register/info";
			}
		}
	}

	@GetMapping("/register/info")
	public String dangki2(Model model, @ModelAttribute("customer") KhachHang kh) {
		if (tempAcc == null) {
			return "redirect:/register/account";
		}
		return "register";
	}

	
	@PostMapping("/register/info/submit")
	public String dangki2(Model model, @Valid @ModelAttribute("customer") KhachHang kh, BindingResult result) {
		TaiKhoan tk = tempAcc;
		if (result.hasErrors()) {
			model.addAttribute("customer", kh);
			return "register";
		} else {
			var tkCheck = taiKhoanDAO.findAll();
			for (TaiKhoan tkcheck : tkCheck) {
				if (tkcheck.getUsername().equals(tk.getUsername())) {
					kh.setMaKH(tkcheck.getKhachHang().getMaKH());
				}
			}
		}
		tempInfo = kh;

//		khachHangDAO.save(kh);
//		tk.setKhachHang(kh);
//		taiKhoanDAO.save(tk);
		return "redirect:/register/sendMail";
	}

	@GetMapping("/register/sendMail")
	public String sendMail() {
		if (tempAcc == null || tempInfo == null) {
			return "redirect:/register/account";
		}
		Random rand = new Random();
		code = String.valueOf(rand.nextInt(1000000));
		mailer.send(tempInfo.getEmail(), "Xác nhận tạo tài khoản", code + " là code xác nhận tạo tài khoản "
				+ tempAcc.getUsername() + ". Xin vui lòng không cung cấp mã này cho bất kì bên nào khác.");
		return "redirect:/register/final";
	}

	@GetMapping("/register/final")
	public String dangki3() {
		if (code.isEmpty()) {
			return "redirect:/register/sendMail";
		}
		return "finalRegister";
	}

	@PostMapping("/register/final/submit")
	public String dangki3(Model model, @RequestParam("code") String submitCode) {
		if (code.equals(submitCode)) {
			khachHangDAO.save(tempInfo);
			tempAcc.setKhachHang(tempInfo);
			taiKhoanDAO.save(tempAcc);
			return "redirect:/login";
		} else {
			model.addAttribute("message", "Mã xác nhận không trùng khớp");
			return "finalRegister";
		}
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
