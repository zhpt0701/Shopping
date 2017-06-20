package com.example.cloudAndPurchasing.http;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author 张安得
 * 
 */
public class DateTimeUtil {

	/**
	 * 日期统一格式
	 */
	private final static SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 获取下一秒的时间
	 * 
	 * @param currentDate
	 * @return
	 */
	public static String getDateAddOneSecond(String currentDate) {

		String nextSecondDate = "";

		if (currentDate != null && !currentDate.equals("")) {

			try {
				Date date = format.parse(currentDate); // 将当前时间格式化
				// System.out.println("front:" + format.format(date)); //
				// 显示输入的日期
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.SECOND, 1); // 当前时间加1秒
				date = cal.getTime();
				// System.out.println("after:" + format.format(date));
				nextSecondDate = format.format(date); // 加一秒后的时间
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return nextSecondDate;
	}

	/**
	 * 获取剩余时间 几天几时几分几秒
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static String getRemainTime(String startTime, String endTime) {

		String remainTime = "0"; // 剩余时间

		long dayMsec = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long hourMsec = 1000 * 60 * 60;// 一小时的毫秒数
		long minuteMsec = 1000 * 60;// 一分钟的毫秒数
		long secondMsec = 1000;// 一秒钟的毫秒数
		long diffMsec; // 毫秒差

		if (startTime != null && !startTime.equals("") && endTime != null
				&& !endTime.equals("")) {
			try {
				// 获得两个时间的毫秒时间差异
				diffMsec = format.parse(endTime).getTime()
						- format.parse(startTime).getTime();
				if(diffMsec > 0){
					/*判断结束时间是否大于开始时间*/
					long diffDay = diffMsec / dayMsec;// 计算差多少天
					long diffHour = diffMsec % dayMsec / hourMsec;// 计算差多少小时
					long diffMin = diffMsec % dayMsec % hourMsec / minuteMsec;// 计算差多少分钟
					long diffSec = diffMsec % dayMsec % dayMsec % minuteMsec
							/ secondMsec;// 计算差多少秒//输出结果
					remainTime = diffDay + "天" + diffHour + "时" + diffMin + "分"
							+ diffSec + "秒";
				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return remainTime;
	}

	/**
	 * 格式化日期格式
	 * 
	 * @param dateTimeString
	 * @return
	 */
	public static String formatDateType(String dateTimeString) {

		String formatAfterDateTimeString = "";
		// System.out.println(dateTimeString);

		if (dateTimeString != null && !dateTimeString.equals("")) {
			/* 判断字符串是否有值 */
			formatAfterDateTimeString = dateTimeString;

			if (formatAfterDateTimeString.contains("/")) {
				/* 判断日期中是否包含'/' */
				formatAfterDateTimeString = formatAfterDateTimeString.replace(
						"/", "-");
			}

			if ((formatAfterDateTimeString.lastIndexOf("-") - formatAfterDateTimeString
					.indexOf("-")) == 2) {
				/* 判断月份格式是否是MM格式 */
				String frontSubString = formatAfterDateTimeString.substring(0,
						formatAfterDateTimeString.indexOf("-") + 1);
				String afterSubString = "0" + formatAfterDateTimeString.substring(
						formatAfterDateTimeString.indexOf("-") + 1,
						formatAfterDateTimeString.length());
				
				formatAfterDateTimeString = frontSubString + afterSubString; //拼接字符串
			}
		}
		return formatAfterDateTimeString;
	}
}
