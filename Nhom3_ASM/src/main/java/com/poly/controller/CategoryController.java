package com.poly.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.DAO.LoaiHangDAO;
import com.poly.DAO.SanPhamDAO;
import com.poly.model.LoaiHang;
import com.poly.model.SanPham;

@Controller
public class CategoryController {
	@Autowired
	LoaiHangDAO categoryDAO;
	@Autowired
	SanPhamDAO sanPhamDAO;

	@GetMapping("/category")
	public String category(Model model) {
		List<LoaiHang> categories = categoryDAO.findAll();
		model.addAttribute("categories", categories);
		return "category";
	}

	@GetMapping("/category/page")
	public String categoryNo(Model model, @RequestParam("id") Integer id, @RequestParam("p") Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0), 6);
		Page<SanPham> sanPhams = sanPhamDAO.getSanPhamByCategoryId(id, pageable);
		var numberOfPages = sanPhams.getTotalPages();
		LoaiHang category = categoryDAO.findById(id).get();
		model.addAttribute("currentCategory", category);
		model.addAttribute("products", sanPhams);
		model.addAttribute("currIndex", p.orElse(0));
		model.addAttribute("numberOfPages", numberOfPages);
		return "product";
	}

}
