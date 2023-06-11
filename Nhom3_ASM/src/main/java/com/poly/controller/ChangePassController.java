package com.poly.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.poly.DAO.TaiKhoanDAO;
import com.poly.model.TaiKhoan;

@Controller
public class ChangePassController {

	@Autowired
	TaiKhoanDAO tkDao;
	
	
}
