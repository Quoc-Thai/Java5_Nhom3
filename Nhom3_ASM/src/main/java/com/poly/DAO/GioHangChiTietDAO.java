package com.poly.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.poly.model.GioHangChiTiet;

@Transactional
public interface GioHangChiTietDAO extends JpaRepository<GioHangChiTiet, Integer> {
	@Query("SELECT p FROM GioHangChiTiet p WHERE p.gioHang.maGH = ?1 AND p.sanPham.maSP = ?2")
	GioHangChiTiet getGioHangChiTietByMaSP(Integer maGH, Integer maSP);

	@Query("SELECT SUM(p.soLuong*p.sanPham.giaSP) From GioHangChiTiet p WHERE p.gioHang.taiKhoan.username LIKE ?1")
	Double TotalCashHoaDon(String username);

	@Modifying
	@Query("DELETE FROM GioHangChiTiet p WHERE p.gioHang.maGH = ?1 AND p.sanPham.maSP = ?2")
	void deleteItem(Integer maGH, Integer maSP);
}
