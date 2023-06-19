package com.poly.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.poly.DAO.LoaiHangDAO;
import com.poly.DAO.SanPhamDAO;
import com.poly.model.LoaiHang;
import com.poly.model.SanPham;

import jakarta.servlet.ServletContext;

@Controller
public class AdminController {
	@Autowired
	ServletContext app;
	@Autowired
	SanPhamDAO sanPhamDAO;
	@Autowired
	LoaiHangDAO loaiHangDAO;

	@GetMapping("/admin")
	public String admin() {
		return "admin_html/dashboard";
	}

	@GetMapping("/admin/table")
	public String Table() {
		return "admin_html/basic-table";
	}

	@GetMapping("/admin/blank")
	public String Blank() {
		return "admin_html/blank";
	}

	@GetMapping("/admin/icon")
	public String Icon() {
		return "admin_html/fontawesome";
	}

	@GetMapping("/admin/profile")
	public String Profile() {
		return "admin_html/profile";
	}

	@GetMapping("/admin/product/add")
	public String add() {
		return "admin_html/addProduct";
	}

	@PostMapping("/admin/product/add/submit")
	public String addSp(Model model, @ModelAttribute("product") SanPham product,
			@RequestParam("image") MultipartFile img) throws IllegalStateException, IOException {
		String filename = img.getOriginalFilename();
		LoaiHang lh = loaiHangDAO.findById(product.getLoaiHang().getMaLoai()).get();
		File file = new ClassPathResource("static/img/product/"+lh.getTenFolder()).getFile();
		Path path = Paths.get(file.getAbsolutePath() + File.separator + filename);
		Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		
		product.setHinhAnh("../img/product/"+lh.getTenFolder()+"/"+filename);
		sanPhamDAO.save(product);		
		return "redirect:/index";
	}

}
