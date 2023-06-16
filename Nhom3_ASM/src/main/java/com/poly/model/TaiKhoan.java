package com.poly.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
	@NotBlank(message = "Username không được bỏ trống")
	String username;

	@NotBlank(message = "Mật khẩu không được bỏ trống")
	@Column(name = "password")
	String password;
	@Column(name = "is_admin")
	Boolean isAdmin = false;
	@Column(name = "activated")
	Boolean activated = false;

	@OneToMany(mappedBy = "taiKhoan")
	List<HoaDon> hoaDon;

	@OneToOne
	@JoinColumn(name = "ma_kh")
	KhachHang khachHang;
}
