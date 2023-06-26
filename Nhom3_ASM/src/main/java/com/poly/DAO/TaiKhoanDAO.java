package com.poly.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.model.TaiKhoan;

public interface TaiKhoanDAO extends JpaRepository<TaiKhoan, String> {
	@Query("SELECT p FROM TaiKhoan p WHERE p.isAdmin = false")
	List<TaiKhoan> getAllUserExceptAdmin();
}
