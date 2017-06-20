package com.example.cloudAndPurchasing.kind;


import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {
	/** 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false */
	public static boolean isEmpty(String value) {
		if (value != null && !"".equalsIgnoreCase(value.trim()) && !"null".equalsIgnoreCase(value.trim())) {
			return false;
		} else {
			return true;
		}

	}

    /**
     *
     * @param
     * @return       04分34秒78
     */
    public static String formatTime(long millisecond){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm分ss秒SS");
        Date date = new Date(millisecond);
        return simpleDateFormat.format(date);
    }
}
