package com.poly.authenticator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poly.DAO.LoaiHangDAO;
import com.poly.DAO.TaiKhoanDAO;
import com.poly.model.TaiKhoan;
import com.poly.service.CookieService;
import com.poly.service.SessionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class AuthInterceptor implements HandlerInterceptor {

	@Autowired
	HttpSession session;
	@Autowired
	LoaiHangDAO loaiHangDAO;
	@Autowired
	TaiKhoanDAO taiKhoanDAO;
	@Autowired
	CookieService cookieService;
	@Autowired
	SessionService sessionService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		String cookie = cookieService.getCookieValue("rememberUser");
		if (cookie != null) {
			TaiKhoan tk = taiKhoanDAO.findById(cookie).get();
			sessionService.set("currentUser", tk);
		}
		TaiKhoan user = (TaiKhoan) session.getAttribute("currentUser");
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
