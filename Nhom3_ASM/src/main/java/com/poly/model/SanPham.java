package com.poly.model;

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
@Table(name = "sanpham")
public class SanPham {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ma_sp")
	Integer maSP;
	@Column(name = "ten_sp")
	String tenSP;
	@Column(name = "gia_sp")
	Double giaSP;
	@Column(name = "gia_cu")
	Double giaCu;
	@Column(name = "ton_kho")
	Integer tonKho;
	@Column(name = "mo_ta")
	String mota;
	@Column(name = "hinh_anh")
	String hinhAnh;
	@Column(name = "available")
	Boolean available;

	@OneToMany(mappedBy = "sanPham")
	List<GioHangChiTiet> gioHangChiTiet;
	@OneToMany(mappedBy = "sanPham")
	List<HoaDonChiTiet> hoaDonChiTiet;
	@ManyToOne
	@JoinColumn(name = "ma_th")
	ThuongHieu thuongHieu;
	@ManyToOne
	@JoinColumn(name = "ma_loai")
	LoaiHang loaiHang;
}
