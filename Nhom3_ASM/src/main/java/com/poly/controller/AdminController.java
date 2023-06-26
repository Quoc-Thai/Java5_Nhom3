package com.poly.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poly.DAO.HoaDonDAO;
import com.poly.DAO.LoaiHangDAO;
import com.poly.DAO.SanPhamDAO;
import com.poly.DAO.TaiKhoanDAO;
import com.poly.DAO.ThuongHieuDAO;
import com.poly.DAO.TrangThaiDAO;
import com.poly.model.HoaDon;
import com.poly.model.HoaDonChiTiet;
import com.poly.model.LoaiHang;
import com.poly.model.Report;
import com.poly.model.SanPham;
import com.poly.model.TaiKhoan;
import com.poly.model.ThuongHieu;
import com.poly.model.TrangThai;

import jakarta.servlet.ServletContext;

@Controller
public class AdminController {
	@Autowired
	ServletContext app;
	@Autowired
	SanPhamDAO sanPhamDAO;
	@Autowired
	LoaiHangDAO loaiHangDAO;
	@Autowired
	ThuongHieuDAO thuongHieuDAO;
	@Autowired
	TaiKhoanDAO taiKhoanDAO;
	@Autowired
	HoaDonDAO hoaDonDAO;
	@Autowired
	TrangThaiDAO trangThaiDAO;

	@GetMapping("/admin")
	public String admin(Model model, @RequestParam("p") Optional<Integer> p) {
		model.addAttribute("numTK", taiKhoanDAO.findAll().size());
		model.addAttribute("numSP", sanPhamDAO.findAll().size());
		model.addAttribute("numHD", hoaDonDAO.findAll().size());
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Report> sanPhams = sanPhamDAO.getListSP(pageable);
		var numberOfPages = sanPhams.getTotalPages();
		model.addAttribute("currIndex", p.orElse(0));
		model.addAttribute("numberOfPages", numberOfPages);
		model.addAttribute("products", sanPhams);
		return "admin_html/dashboard";
	}

	@GetMapping("/admin/table")
	public String Table(Model model) {
		List<SanPham> list = sanPhamDAO.findAll();
		model.addAttribute("products", list);
		return "admin_html/basic-table";
	}

	@GetMapping("/admin/bill/table")
	public String Bill(Model model) {
		List<HoaDon> hd = hoaDonDAO.findAll(Sort.by(Sort.Direction.ASC, "trangThai.maTrangThai"));
		model.addAttribute("hoaDon", hd);
		return "admin_html/bill-table";
	}

	@GetMapping("/admin/bill/{id}")
	public String billDetail(Model model, @PathVariable Integer id) {
		HoaDon hd = hoaDonDAO.findById(id).get();
		model.addAttribute("receipt", hd);
		return "receipt";
	}

	@GetMapping("/admin/bill/table/{id}/{status}")
	public String setTrangThai(Model model, @PathVariable Integer id, @PathVariable Integer status) {
		HoaDon hd = hoaDonDAO.findById(id).get();
		TrangThai tt = trangThaiDAO.findById(status + 1).get();
		if (status + 1 == 5) {
			for (HoaDonChiTiet hdct : hd.getHoaDonChiTiet()) {
				SanPham sanPham = sanPhamDAO.findById(hdct.getSanPham().getMaSP()).get();
				sanPham.setTonKho(sanPham.getTonKho() + hdct.getSoLuong());
				sanPhamDAO.save(sanPham);
			}
		}
		hd.setTrangThai(tt);
		hoaDonDAO.save(hd);
		return "redirect:/admin/bill/table";
	}

	@GetMapping("/admin/blank")
	public String Blank() {
		return "admin_html/blank";
	}

	@GetMapping("/admin/icon")
	public String Icon() {
		return "admin_html/fontawesome";
	}

	@GetMapping("/admin/user/table")
	public String Profile(Model model) {
		List<TaiKhoan> list = taiKhoanDAO.getAllUserExceptAdmin();
		model.addAttribute("users", list);
		return "admin_html/profile";
	}

	@PostMapping("/admin/user/lock/{username}")
	public String Lock(Model model, @PathVariable("username") String username) {
		TaiKhoan tk = taiKhoanDAO.findById(username).get();
		tk.setActivated(false);
		taiKhoanDAO.save(tk);
		return "redirect:/admin/user/table";
	}

	@PostMapping("/admin/user/unlock/{username}")
	public String Unlock(Model model, @PathVariable("username") String username) {
		TaiKhoan tk = taiKhoanDAO.findById(username).get();
		tk.setActivated(true);
		taiKhoanDAO.save(tk);
		return "redirect:/admin/user/table";
	}

	@GetMapping("/admin/product/add")
	public String add(Model model, @ModelAttribute("product") SanPham product) {
		List<LoaiHang> cate = loaiHangDAO.findAll();
		List<ThuongHieu> th = thuongHieuDAO.findAll();
		model.addAttribute("categories", cate);
		model.addAttribute("brands", th);
		return "admin_html/addProduct";
	}

	@PostMapping("/admin/product/add/submit")
	public String addSp(Model model, @ModelAttribute("product") SanPham product,
			@RequestParam("image") MultipartFile img) throws IllegalStateException, IOException {
		if (product.getTenSP().equals("") || product.getMoTa().equals("") || product.getGiaCu().equals("")
				|| product.getGiaSP().equals("") || product.getTonKho().equals("")) {
			model.addAttribute("message", "Vui lòng điền đủ thông tin!!");
			return "admin_html/addProduct";
		} else {
			String filename = img.getOriginalFilename();
			LoaiHang lh = loaiHangDAO.findById(product.getLoaiHang().getMaLoai()).get();
			File file = new ClassPathResource("static/img/product/" + lh.getTenFolder()).getFile();
			Path path = Paths.get(file.getAbsolutePath() + File.separator + filename);
			Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			product.setHinhAnh("../img/product/" + lh.getTenFolder() + "/" + filename);
			sanPhamDAO.save(product);
			return "redirect:/index";
		}

	}

	@PostMapping("admin/product/edit/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		List<LoaiHang> cate = loaiHangDAO.findAll();
		List<ThuongHieu> th = thuongHieuDAO.findAll();
		model.addAttribute("categories", cate);
		model.addAttribute("brands", th);
		SanPham sp = sanPhamDAO.findById(id).get();
		model.addAttribute("product", sp);
		sanPhamDAO.save(sp);
		return "admin_html/addProduct";
	}

	@PostMapping("admin/product/edit/submit")
	public String editSubmit(Model model, @ModelAttribute("product") SanPham product,
			@RequestParam("image") MultipartFile img) throws IllegalStateException, IOException {
		System.out.println(product.getAvailable());
		if (product.getTenSP().equals("") || product.getMoTa().equals("") || product.getGiaCu().equals("")
				|| product.getGiaSP().equals("") || product.getTonKho().equals("")) {
			model.addAttribute("message", "Vui lòng điền đủ thông tin!!");
			return "admin_html/addProduct";
		}
		if (!img.getOriginalFilename().isEmpty()) {
			String filename = img.getOriginalFilename();
			LoaiHang lh = loaiHangDAO.findById(product.getLoaiHang().getMaLoai()).get();
			File file = new ClassPathResource("static/img/product/" + lh.getTenFolder()).getFile();
			Path path = Paths.get(file.getAbsolutePath() + File.separator + filename);
			Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			product.setHinhAnh("../img/product/" + lh.getTenFolder() + "/" + filename);
		} else {
			SanPham sp = sanPhamDAO.findById(product.getMaSP()).get();
			product.setHinhAnh(sp.getHinhAnh());
		}
		sanPhamDAO.save(product);
		return "redirect:/admin/table";
	}

//	@PostMapping("admin/product/edit/submit")
//	public String editSubmit(Model model, @ModelAttribute("product") SanPham product,
//			@RequestParam("image") MultipartFile img) throws IllegalStateException, IOException {
//		System.out.println(product.getAvailable());
//		
//		 if (!img.getOriginalFilename().isEmpty()) {
//			String filename = img.getOriginalFilename();
//			LoaiHang lh = loaiHangDAO.findById(product.getLoaiHang().getMaLoai()).get();
//			File file = new ClassPathResource("static/img/product/" + lh.getTenFolder()).getFile();
//			Path path = Paths.get(file.getAbsolutePath() + File.separator + filename);
//			Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//			product.setHinhAnh("../img/product/" + lh.getTenFolder() + "/" + filename);
//		} else {
//			SanPham sp = sanPhamDAO.findById(product.getMaSP()).get();
//			product.setHinhAnh(sp.getHinhAnh());
//		}
//		sanPhamDAO.save(product);
//		return "redirect:/admin/table";
//	}
//	
}
