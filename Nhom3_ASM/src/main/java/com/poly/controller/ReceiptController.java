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
import com.poly.model.GioHang;
import com.poly.model.GioHangChiTiet;
import com.poly.model.HoaDon;
import com.poly.model.HoaDonChiTiet;
import com.poly.model.SanPham;
import com.poly.model.TaiKhoan;

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
	HttpSession session;

	@GetMapping("/account/cart/payment")
	public String receipt() {
		TaiKhoan user = (TaiKhoan) session.getAttribute("currentUser");
		Double totalCash = gioHangChiTietDAO.TotalCashHoaDon(user.getUsername());
		GioHang gh = gioHangDAO.getGioHangByUserId(user.getUsername());
		HoaDon hd = new HoaDon(null, gh.getGioHangCT().size(), totalCash, null, null, user);
		hoaDonDAO.save(hd);
		for (GioHangChiTiet ghct : gh.getGioHangCT()) {
			HoaDonChiTiet hdct = new HoaDonChiTiet(null, ghct.getSanPham().getGiaSP(), ghct.getSoLuong(),
					ghct.getSanPham().getGiaSP() * ghct.getSoLuong(), ghct.getSanPham(), hd);
			hoaDonChiTietDAO.save(hdct);
			SanPham sp = sanPhamDAO.findById(ghct.getSanPham().getMaSP()).get();
			sp.setTonKho(sp.getTonKho() - ghct.getSoLuong());
			if (sp.getTonKho() == 0) {
				sp.setAvailable(false);
			}
			sanPhamDAO.save(sp);
			gioHangChiTietDAO.delete(ghct);
		}
		HoaDon recent = hoaDonDAO.getRecentReceipt(user.getUsername());
		return "redirect:/account/receipt/" + recent.getMaHD();
	}

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

}
