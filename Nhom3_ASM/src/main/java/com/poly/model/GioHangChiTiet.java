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
public class GioHangChiTiet {
	@Id
	Integer maGHCT;
	Integer soLuong;
	Double giaSP;
	Double tongTien;

	@ManyToOne
	@JoinColumn(name = "maSP")
	SanPham sanPham;
	@ManyToOne
	@JoinColumn(name = "maGH")
	GioHang gioHang;
}
