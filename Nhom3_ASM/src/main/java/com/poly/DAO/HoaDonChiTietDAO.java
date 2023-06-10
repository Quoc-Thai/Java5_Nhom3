package com.poly.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.model.HoaDonChiTiet;

public interface HoaDonChiTietDAO extends JpaRepository<HoaDonChiTiet, Integer> {
	
}
