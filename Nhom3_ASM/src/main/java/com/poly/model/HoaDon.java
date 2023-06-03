package com.poly.model;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "hoadon")
public class HoaDon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ma_hd")
	Integer maHD;
	@Column(name = "tong_sp")
	Integer tongSP;
	@Column(name = "tong_tien")
	Double tongTien;
	@Column(name = "ngay_tao")
	Date ngayTao;

	@OneToMany(mappedBy = "hoaDon")
	List<HoaDonChiTiet> hoaDonChiTiet;
	@ManyToOne
	@JoinColumn(name = "ma_tK")
	TaiKhoan taiKhoan;
}
