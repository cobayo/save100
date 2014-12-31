package jp.kobayo.save100.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 汎用ユーティリティです。
 *
 * Created by Yosuke Kobayashi on 2014/12/25.
 */
public class CommonUtils {

	/**
	 * 文字列をintに変換します。
	 * @param s : 文字列
	 * @return 整数
	 */
	public static int parseInt(String s) {

		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return 0;
		}

	}

	/**
	 * 文字列連結
	 *
	 * @param strings : 文字列
	 * @return 連結済み文字列
	 */
	public static String concat(String...strings) {

		if (strings == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		for (String s : strings) {
			sb.append(s);
		}

		return sb.toString();

	}

	/**
	 * DB用に日本時間の日付を文字列で取得
	 * @param date : Date
	 * @return dateString : String
	 */
	public static String getDateTimeString(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

}
