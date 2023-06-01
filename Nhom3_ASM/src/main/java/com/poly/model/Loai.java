package com.poly.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Loai {
	@Id
	Integer maLoai;
	String tenLoai;
	
	@OneToMany(mappedBy = "loai")
	List<SanPham> sanPham;
}
