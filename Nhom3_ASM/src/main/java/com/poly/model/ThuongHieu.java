package com.poly.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ThuongHieu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	String maTH;
	String tenTH;
	String diaChi;

	@OneToMany(mappedBy = "thuongHieu")
	List<SanPham> sanPham;
}
