package com.poly.model;



import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "khachhang")
public class KhachHang {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ma_kh")
	Integer maKH;
	@Column(name = "ho_kh")
	String hoKH;
	@Column(name = "ten_kh")
	String tenKH;
	@Column(name = "email")
	String email;
	@Column(name = "ngay_sinh")
	String ngaySinh;
	@Column(name = "dien_thoai")
	String dienThoai;
	@Column(name = "dia_chi")
	String diaChi;

	@OneToOne
	@JoinColumn(name = "username")
	TaiKhoan taiKhoan;

}
