package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.poly.DAO.GioHangChiTietDAO;
import com.poly.DAO.GioHangDAO;
import com.poly.DAO.HoaDonChiTietDAO;
import com.poly.DAO.HoaDonDAO;
import com.poly.DAO.SanPhamDAO;
import com.poly.DAO.TrangThaiDAO;
import com.poly.model.HoaDon;
import com.poly.model.HoaDonChiTiet;
import com.poly.model.SanPham;
import com.poly.model.TaiKhoan;
import com.poly.model.TrangThai;

import jakarta.servlet.http.HttpSession;

@Controller
public class ReceiptController {
	@Autowired
	GioHangDAO gioHangDAO;
	@Autowired
	GioHangChiTietDAO gioHangChiTietDAO;
	@Autowired
	HoaDonDAO hoaDonDAO;
	@Autowired
	HoaDonChiTietDAO hoaDonChiTietDAO;
	@Autowired
	SanPhamDAO sanPhamDAO;
	@Autowired
	TrangThaiDAO trangThaiDAO;
	@Autowired
	HttpSession session;

	@GetMapping("/account/receipt/{id}")
	public String getReceiptList(@PathVariable Integer id, Model model) {
		TaiKhoan user = (TaiKhoan) session.getAttribute("currentUser");
		List<HoaDon> list = hoaDonDAO.findAll();
		for (HoaDon hd : list) {
			if (hd.getTaiKhoan().getUsername().equals(user.getUsername()) && hd.getMaHD() == id) {
				model.addAttribute("receipt", hd);
				return "receipt";
			}
		}
		return "redirect:/404";
	}

	@GetMapping("/account/receipt")
	public String getReceiptList(Model model) {
		TaiKhoan user = (TaiKhoan) session.getAttribute("currentUser");
		List<HoaDon> list = hoaDonDAO.getList(user.getUsername());
		model.addAttribute("receiptList", list);
		return "receiptList";
	}

	@GetMapping("/account/receipt/cancel/{id}")
	public String cancelCart(@PathVariable Integer id, Model model) {
		TaiKhoan user = (TaiKhoan) session.getAttribute("currentUser");
		List<HoaDon> list = hoaDonDAO.findAll();
		for (HoaDon hd : list) {
			if (hd.getTaiKhoan().getUsername().equals(user.getUsername()) && hd.getMaHD() == id) {
				for (HoaDonChiTiet hdct : hd.getHoaDonChiTiet()) {
					SanPham sanPham = sanPhamDAO.findById(hdct.getSanPham().getMaSP()).get();
					sanPham.setTonKho(sanPham.getTonKho() + hdct.getSoLuong());
					sanPhamDAO.save(sanPham);
				}
				TrangThai tt = trangThaiDAO.findById(5).get();
				hd.setTrangThai(tt);
				hoaDonDAO.save(hd);
				return "redirect:/account/receipt";
			}
		}
		return "redirect:/404";
	}
}
