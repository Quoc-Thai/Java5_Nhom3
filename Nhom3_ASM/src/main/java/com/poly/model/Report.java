package com.poly.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
	@Id
	Integer maSP;
	String tenSP;
	Boolean available;
	Long soLuong;
	Double tongTien;
}
