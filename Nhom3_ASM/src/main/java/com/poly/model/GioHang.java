package com.poly.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class GioHang {
	@Id
	Integer maGH;

	@OneToMany(mappedBy = "gioHang")
	List<GioHangChiTiet> gioHangCT;
	@OneToOne
	@JoinColumn(name = "maTK")
	TaiKhoan taiKhoan;
}
