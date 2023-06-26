package com.poly.model;

import java.sql.Timestamp;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ngay_tao")
	Timestamp ngayTao;
	@OneToMany(mappedBy = "hoaDon")
	List<HoaDonChiTiet> hoaDonChiTiet;
	@ManyToOne
	@JoinColumn(name = "username")
	TaiKhoan taiKhoan;
	@ManyToOne
	@JoinColumn(name = "ma_trang_thai")
	TrangThai trangThai;

}
