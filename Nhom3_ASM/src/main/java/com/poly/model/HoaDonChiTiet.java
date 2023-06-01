package com.poly.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class HoaDonChiTiet {
	@Id
	Integer maHDCT;
	Integer soLuong;
	Double tongTien;

	@ManyToOne
	@JoinColumn(name = "maSP")
	SanPham sanPham;
	@ManyToOne
	@JoinColumn(name = "maHD")
	HoaDon hoaDon;
}