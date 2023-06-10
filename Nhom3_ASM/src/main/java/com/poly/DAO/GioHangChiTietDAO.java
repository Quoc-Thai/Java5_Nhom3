package com.poly.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.model.GioHangChiTiet;

public interface GioHangChiTietDAO extends JpaRepository<GioHangChiTiet, Integer> {
	@Query("SELECT p FROM GioHangChiTiet p WHERE p.sanPham.maSP = ?1")
	GioHangChiTiet getGioHangChiTietByMaSP(Integer maSP);
	
	@Query("SELECT SUM(p.soLuong*p.sanPham.giaSP) From GioHangChiTiet p WHERE p.gioHang.taiKhoan.username LIKE ?1")
	Double TotalCashHoaDon(String username);
}
