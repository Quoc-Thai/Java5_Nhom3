package com.poly.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.model.SanPham;

public interface SanPhamDAO extends JpaRepository<SanPham, Integer> {
}
