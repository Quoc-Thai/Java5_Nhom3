package com.poly.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class KhachHang {
	@Id
	Integer maKH;
	String hoKH;
	String tenKH;
	String email;
	Date ngaySinh;
	String dienThoai;
	String diaChi;

	@OneToOne
	@JoinColumn(name = "maTK")
	TaiKhoan taiKhoan;
}
