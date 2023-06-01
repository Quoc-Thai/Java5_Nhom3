package com.poly.model;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class HoaDon {
	@Id
	Integer maHD;
	Integer tongSanPham;
	Double tongTien;
	Date ngayTao;

	@OneToMany(mappedBy = "hoaDon")
	List<HoaDonChiTiet> hoaDonChiTiet;
	@ManyToOne
	@JoinColumn(name = "maTK")
	TaiKhoan taiKhoan;
}
