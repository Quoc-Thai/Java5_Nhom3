package com.poly.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "khachhang")
public class KhachHang {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ma_kh")
	Integer maKH;
	@NotBlank(message = "Họ không được bỏ trống")
	@Column(name = "ho_kh")
	String hoKH;
	@NotBlank(message = "Tên không được bỏ trống")
	@Column(name = "ten_kh")
	String tenKH;
	@NotBlank(message = "Email không được bỏ trống")
	@Email(message = "Vui lòng nhập đúng format 'abc@xyz.com'")
	@Column(name = "email")
	String email;
	@NotBlank(message = "Ngày không được bỏ trống")
	@Column(name = "ngay_sinh")
	String ngaySinh;
	@NotBlank(message = "Số điện thoại không được bỏ trống")
	@Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})",message = "Số điện thoại sai định dạng")
	@Column(name = "dien_thoai")
	String dienThoai;
	@NotBlank(message = "Địa chỉ không được bỏ trống")
	@Column(name = "dia_chi")
	String diaChi;
}
