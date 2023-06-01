package com.poly.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class SanPham {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	String maSP;
	String tenSP;
	Double giaSP;
	Double giaSale;
	Integer soLuong;
	String mota;
	String hinhAnh;

	@OneToMany(mappedBy = "sanPham")
	List<GioHangChiTiet> gioHangChiTiet;
	@OneToMany(mappedBy = "sanPham")
	List<HoaDonChiTiet> hoaDonChiTiet;
	@ManyToOne
	@JoinColumn(name = "maTH")
	ThuongHieu thuongHieu;
	@ManyToOne
	@JoinColumn(name = "maLoai")
	Loai loai;
}
