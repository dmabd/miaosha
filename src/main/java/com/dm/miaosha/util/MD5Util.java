package com.dm.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
	public static String md5(String str) {
		return DigestUtils.md5Hex(str);
	}
	private static final String salt ="1a2b3c4d";
	public static String inputPasstoFormPass(String inputPass) {
		String str=""+salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
		return md5(str);
	}

	public static String FromPassToDBPass(String formPass,String salt) {
		String str=""+salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(5)+salt.charAt(4);
		return md5(str);
	}
	public static String inputPassToDbPass(String input,String saltDB) {
		String formPass=inputPasstoFormPass(input);
		return FromPassToDBPass(formPass,saltDB);
	}
	
	public static void main(String[] args) {
		System.out.println(inputPassToDbPass("123456", "1a2b3c4d"));
	}
	


}
