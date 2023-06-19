package com.poly.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.DAO.GioHangChiTietDAO;
import com.poly.DAO.GioHangDAO;
import com.poly.DAO.HoaDonChiTietDAO;
import com.poly.DAO.HoaDonDAO;
import com.poly.DAO.SanPhamDAO;
import com.poly.DAO.TrangThaiDAO;
import com.poly.model.GioHang;
import com.poly.model.GioHangChiTiet;
import com.poly.model.HoaDon;
import com.poly.model.HoaDonChiTiet;
import com.poly.model.SanPham;
import com.poly.model.TaiKhoan;
import com.poly.model.TrangThai;

import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {
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

	@PostMapping("/account/addToCart/{id}")
	public String addToCart(@PathVariable Integer id, Model model, @RequestParam("quantity") Integer quantity,
			@CookieValue(name = "rememberUser", defaultValue = "", required = true) String cookie) {
		TaiKhoan user = (TaiKhoan) session.getAttribute("currentUser");
		SanPham sanPham = sanPhamDAO.findById(id).get();
		GioHang gioHang = gioHangDAO.getGioHangByUserId(user.getUsername());
		GioHangChiTiet exist = gioHangChiTietDAO.getGioHangChiTietByMaSP(gioHang.getMaGH(), id);
		if (exist != null) {
			System.out.println("exist");
			Integer num = exist.getSoLuong() + quantity;
			if (num > sanPham.getTonKho()) {
				exist.setSoLuong(sanPham.getTonKho());
			} else {
				exist.setSoLuong(num);
			}
			gioHangChiTietDAO.save(exist);
		} else {
			GioHangChiTiet ghct = new GioHangChiTiet(null, quantity, sanPham, gioHang);
			gioHangChiTietDAO.save(ghct);
		}
		return "redirect:/category";
	}

	@GetMapping("/account/cart")
	public String getCart(Model model) {
		TaiKhoan user = (TaiKhoan) session.getAttribute("currentUser");
		GioHang gh = gioHangDAO.getGioHangByUserId(user.getUsername());
		Integer totalProduct = gh.getGioHangCT().size();
		Double totalCash = gioHangChiTietDAO.TotalCashHoaDon(user.getUsername());
		model.addAttribute("totalProduct", totalProduct);
		model.addAttribute("totalCash", totalCash);
		model.addAttribute("cart", gh);
		return "cart";
	}

	@PostMapping("/account/cart/edit/{id}")
	public String editCart(Model model, @PathVariable Integer id, @RequestParam("quantity") Integer quantity) {
		GioHangChiTiet ghct = gioHangChiTietDAO.findById(id).get();
		if (quantity > ghct.getSoLuong()) {
			ghct.setSoLuong(ghct.getSoLuong());
		} else {
			ghct.setSoLuong(quantity);
		}
		gioHangChiTietDAO.save(ghct);
		return "redirect:/account/cart";
	}

	@PostMapping("/account/cart/remove/{id}")
	public String deleteCart(Model model, @PathVariable Integer id) {
		TaiKhoan user = (TaiKhoan) session.getAttribute("currentUser");
		GioHang gh = gioHangDAO.getGioHangByUserId(user.getUsername());
		gioHangChiTietDAO.deleteItem(gh.getMaGH(), id);
		return "redirect:/account/cart";
	}

	@GetMapping("/account/recart/{id}")
	public String reCart(@PathVariable Integer id, Model model) {
		TaiKhoan user = (TaiKhoan) session.getAttribute("currentUser");
		List<HoaDon> list = hoaDonDAO.findAll();
		for (HoaDon hd : list) {
			if (hd.getTaiKhoan().getUsername().equals(user.getUsername()) && hd.getMaHD() == id) {
				for (HoaDonChiTiet hdct : hd.getHoaDonChiTiet()) {
					SanPham sanPham = sanPhamDAO.findById(hdct.getSanPham().getMaSP()).get();
					GioHang gioHang = gioHangDAO.getGioHangByUserId(user.getUsername());
					GioHangChiTiet exist = gioHangChiTietDAO.getGioHangChiTietByMaSP(gioHang.getMaGH(),
							sanPham.getMaSP());
					if (exist != null) {
						System.out.println("exist");
						Integer num = exist.getSoLuong() + hdct.getSoLuong();
						if (num > sanPham.getTonKho()) {
							exist.setSoLuong(sanPham.getTonKho());
						} else {
							exist.setSoLuong(num);
						}
						gioHangChiTietDAO.save(exist);
					} else {
						GioHangChiTiet ghct = new GioHangChiTiet(null, hdct.getSoLuong(), sanPham, gioHang);
						gioHangChiTietDAO.save(ghct);
					}
				}
				return "redirect:/account/cart";
			}
		}
		return "redirect:/404";
	}

	@GetMapping("/account/cancel/{id}")
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

	@GetMapping("/account/cart/payment")
	public String receipt() {
		TaiKhoan user = (TaiKhoan) session.getAttribute("currentUser");
		Double totalCash = gioHangChiTietDAO.TotalCashHoaDon(user.getUsername());
		GioHang gh = gioHangDAO.getGioHangByUserId(user.getUsername());
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		TrangThai tt = trangThaiDAO.findById(1).get();
		HoaDon hd = new HoaDon(null, gh.getGioHangCT().size(), totalCash, timestamp, null, user, tt);
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
}
