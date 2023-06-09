package com.poly.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.DAO.GioHangDAO;
import com.poly.DAO.KhachHangDAO;
import com.poly.DAO.TaiKhoanDAO;
import com.poly.model.GioHang;
import com.poly.model.KhachHang;
import com.poly.model.TaiKhoan;

@Controller
public class RegisterController {
	@Autowired
	TaiKhoanDAO taiKhoanDAO;
	
	@Autowired
	KhachHangDAO khachHangDAO;
	
	@Autowired 
	GioHangDAO gioHangDAO;
	
	@GetMapping("/dangki")
	public String dangki() {
		return "register";
	}
	
	@PostMapping("/dangki/submit")
	public String submit(@RequestParam("username") String username,@RequestParam("password") String password,
			@RequestParam("ngaysinh") String ngaysinh, @RequestParam("ho") String ho, @RequestParam("ten") String ten,
			@RequestParam("diachi") String diachi, @RequestParam("dienthoai") String dienthoai,
			@RequestParam("email") String email) {
		TaiKhoan tk = new TaiKhoan();
		KhachHang kh = new KhachHang();
		GioHang gh = new GioHang();
	
		tk.setUsername(username);
		tk.setPassword(password);
		
		kh.setDiaChi(diachi);
		kh.setDienThoai(dienthoai);
		kh.setEmail(email);
		kh.setHoKH(ho);
		kh.setTenKH(ten);
		kh.setNgaySinh(ngaysinh);
		kh.setTaiKhoan(tk);
		
		gh.setTaiKhoan(tk);
		
		
		taiKhoanDAO.save(tk);
		khachHangDAO.save(kh);
		gioHangDAO.save(gh);
		return "redirect:/index";
		
	}
}
