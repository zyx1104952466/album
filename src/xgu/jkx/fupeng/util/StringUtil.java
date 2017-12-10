package xgu.jkx.fupeng.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class StringUtil {
	/**
	 * 判断字符串是否为空
	 */
	public static boolean isStringEmpty(String testString) {
		boolean ok = false;
		if (testString.trim().length() == 0 || testString.trim() == "") {
			ok = true;
		}
		return ok;
	}

	/**
	 * 获取Timestamp的格式
	 * 
	 * @param args
	 */
	public static String changeTimestamp(Timestamp t) {
		String time;
		try {
			time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(t);
		} catch (Exception e) {
			time = "";
		}
		return time;
	}

	/**
	 * 将Date格式转换成String格式
	 * @param t
	 * @return
	 */
	public static String dateToString(Date t) {
		String time;
		try {
			time = new SimpleDateFormat("yyyy-MM-dd").format(t);
		} catch (Exception e) {
			time = "";
		}
		return time;
	}

}
