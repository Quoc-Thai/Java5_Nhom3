package com.poly.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.model.GioHang;

public interface GioHangDAO extends JpaRepository<GioHang, Integer> {
	@Query("SELECT p FROM GioHang p WHERE p.taiKhoan.username = ?1")
	GioHang getGioHangByUserId(String username);
}
