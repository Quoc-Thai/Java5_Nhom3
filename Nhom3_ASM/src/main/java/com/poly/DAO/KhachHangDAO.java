package com.poly.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.model.KhachHang;

public interface KhachHangDAO extends JpaRepository<KhachHang, Integer> {
}
