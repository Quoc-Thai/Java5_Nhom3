package com.poly.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SanPham {
	String MaSP;
	String TenSP;
	Double GiaSP;
	Double GiaSale;
	Integer SoLuong;
	String Mota;
	String HinhAnh;
	String MaLoai;
	String MaTH;
}
