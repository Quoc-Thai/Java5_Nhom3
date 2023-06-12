package com.poly.authenticator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poly.DAO.LoaiHangDAO;
import com.poly.model.TaiKhoan;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class AuthInterceptor implements HandlerInterceptor {
	
	@Autowired
	HttpSession session;
	@Autowired
	LoaiHangDAO loaiHangDAO;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		TaiKhoan user =  (TaiKhoan) session.getAttribute("currentUser");
		if (user == null) {
			response.sendRedirect("/login");
			return false;
		} else if (!user.getIsAdmin() && uri.startsWith("/admin")) {
			response.sendRedirect("/404");
			return false;
		}
		return true;
	}
}
