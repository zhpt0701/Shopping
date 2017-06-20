package com.example.cloudAndPurchasing.kind;

import java.security.MessageDigest;

public class MD5Encoder {
	
	public static String encode(String string) {
		byte[] hash = new byte[0];
		try {
			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuilder hex = new StringBuilder(hash.length * 2);
	    for (byte b : hash) {
	        if ((b & 0xFF) < 0x10) {
	        	hex.append("0");
	        }
	        hex.append(Integer.toHexString(b & 0xFF));
	    }
	    return hex.toString().substring(0,10);
	}
}
