package com.poly.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "hoadonchitiet")
public class HoaDonChiTiet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ma_hdct")
	Integer maHDCT;
	@Column(name = "so_luong")
	Integer soLuong;
	@Column(name = "tong_tien")
	Double tongTien;

	@ManyToOne
	@JoinColumn(name = "ma_sp")
	SanPham sanPham;
	@ManyToOne
	@JoinColumn(name = "ma_hd")
	HoaDon hoaDon;
}