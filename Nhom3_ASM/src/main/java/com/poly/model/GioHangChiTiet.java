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
@Table(name = "giohangchitiet")
public class GioHangChiTiet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ma_ghct")
	Integer maGHCT;
	@Column(name = "so_luong")
	Integer soLuong;
	@Column(name = "gia_sp")
	Double giaSP;
	@Column(name = "tong_tien")
	Double tongTien;

	@ManyToOne
	@JoinColumn(name = "ma_sp")
	SanPham sanPham;
	@ManyToOne
	@JoinColumn(name = "ma_gh")
	GioHang gioHang;
}
