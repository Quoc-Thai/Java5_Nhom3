package com.poly.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.model.HoaDon;

public interface HoaDonDAO extends JpaRepository<HoaDon, Integer> {
	@Query("SELECT p FROM HoaDon p WHERE p.taiKhoan.username LIKE ?1 ORDER BY p.maHD DESC LIMIT 1")
	HoaDon getRecentReceipt(String username);
	
	@Query("SELECT p FROM HoaDon p WHERE p.taiKhoan.username LIKE ?1")
	List<HoaDon> getList(String username);
}
