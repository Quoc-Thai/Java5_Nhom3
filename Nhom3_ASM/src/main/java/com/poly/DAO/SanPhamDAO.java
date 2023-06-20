package com.poly.DAO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.model.Report;
import com.poly.model.SanPham;

public interface SanPhamDAO extends JpaRepository<SanPham, Integer> {
	@Query("SELECT p FROM SanPham p WHERE p.loaiHang.maLoai = ?1 AND p.available = TRUE")
	List<SanPham> getTop8SanPhamByCategoryId(Integer maLoai);

	@Query("SELECT p FROM SanPham p WHERE p.loaiHang.maLoai = ?1 AND p.available = TRUE")
	Page<SanPham> getSanPhamByCategoryId(Integer maLoai, Pageable pageable);

	@Query("SELECT p FROM SanPham p WHERE p.loaiHang.maLoai = ?1 AND p.available = TRUE")
	List<SanPham> getRelatedSanPham(Integer maLoai);

	@Query("SELECT new Report(p.sanPham.maSP, p.sanPham.tenSP, p.sanPham.available, SUM(p.soLuong), SUM(p.tongTien)) "
			+ " FROM HoaDonChiTiet p " + " GROUP BY p.sanPham.maSP, p.sanPham.tenSP, p.sanPham.available"
			+ " ORDER BY SUM(p.soLuong) DESC")
	Page<Report> getListSP(Pageable pageable);
}
