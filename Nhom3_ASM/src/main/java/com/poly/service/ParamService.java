	package com.poly.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ParamService {
	@Autowired
	HttpServletRequest request;

	public String getString(String name, String defaultValue) {
		if (name != null) {
			return request.getParameter(name);
		}
		return defaultValue;
	}

	public Integer getInt(String name, Integer defaultValue) {
		if (name != null) {
			return Integer.parseInt(request.getParameter(name));
		}
		return defaultValue;
	}

	public Double getDouble(String name, Double defaultValue) {
		if (name != null) {
			return Double.parseDouble(request.getParameter(name));
		}
		return defaultValue;
	}

	public Boolean getBoolean(String name, Boolean defaultValue) {
		if (name != null) {
			return Boolean.parseBoolean(request.getParameter(name));

		}
		return defaultValue;
	}

	public Date getDate(String name, String pattern) {

		try {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			String temp = request.getParameter(name);
			return formatter.parse(temp);

		} catch (ParseException e) {
			throw new RuntimeException(e);

		}
	}

	public File save(MultipartFile file, String path) {
		File dir = new File(request.getServletContext().getRealPath(path));
		if (!dir.exists()) {
			dir.mkdir();
		}
		try {
			File saveFile = new File(dir, file.getOriginalFilename());
			file.transferTo(saveFile);
			return saveFile;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
