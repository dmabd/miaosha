package com.dm.miaosha.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class ValidatorUtil {
	private final static Pattern mobile_pattern =Pattern.compile("1\\d{10}");
	public static boolean isMobile(String src) {
		if(StringUtils.isEmpty(src)) {
			return false;
		}
	Matcher mobile=mobile_pattern.matcher(src);	
	return mobile.matches();
	}
	public static void main(String[] args) {
		System.out.println(isMobile("13894841150"));
	}

}
