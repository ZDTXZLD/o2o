package com.wu.o2o.util;

import javax.servlet.http.HttpServletRequest;

import com.google.code.kaptcha.Constants;

public class CodeUtil {
	public static boolean cheakVerifyCode(HttpServletRequest request) {
		String verifyCodeExcepted = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		String verifyCodeActul = HttpServletRequestUtil.getString(request, "verifyCodeActual");
		if (verifyCodeExcepted == null || !verifyCodeActul.equalsIgnoreCase(verifyCodeExcepted)) {
			return false;
		}
		return true;
	}
}
