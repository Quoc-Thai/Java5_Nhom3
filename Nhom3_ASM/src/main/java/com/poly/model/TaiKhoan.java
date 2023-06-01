package com.poly.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class TaiKhoan {
	@Id
	Integer maTK;
	String userName;
	String pass;
	Boolean isAdmin;

	@OneToMany(mappedBy = "taiKhoan")
	List<HoaDon> hoaDon;

	@OneToOne
	@JoinColumn(name = "maGH")
	GioHang gioHang;

	@OneToOne
	@JoinColumn(name = "maKH")
	KhachHang khachHang;
}
