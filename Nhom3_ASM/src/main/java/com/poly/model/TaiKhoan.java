package com.poly.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "taikhoan")
public class TaiKhoan {
	@Id
	@Column(name = "username")
	String username;
	@Column(name = "password")
	String password;
	@Column(name = "is_admin")
	Boolean isAdmin;

	@OneToMany(mappedBy = "taiKhoan")
	List<HoaDon> hoaDon;

	@OneToOne(mappedBy = "taiKhoan")
	@PrimaryKeyJoinColumn
	GioHang gioHang;

	@OneToOne(mappedBy = "taiKhoan")
	@PrimaryKeyJoinColumn
	KhachHang khachHang;
}
