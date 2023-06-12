package com.poly.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.model.SanPham;

public interface SanPhamDAO extends JpaRepository<SanPham, Integer> {
	@Query("SELECT p FROM SanPham p WHERE p.loaiHang.maLoai = ?1 AND p.available = TRUE")
	List<SanPham> getTop8SanPhamByCategoryId(Integer maLoai);

	@Query("SELECT p FROM SanPham p WHERE p.loaiHang.maLoai = ?1 AND p.available = TRUE")
	List<SanPham> getSanPhamByCategoryId(Integer maLoai);

	@Query("SELECT p FROM SanPham p WHERE p.loaiHang.maLoai = ?1 AND p.available = TRUE")
	List<SanPham> getRelatedSanPham(Integer maLoai);
}
