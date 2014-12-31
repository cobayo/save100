package jp.kobayo.save100.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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
	 * List 文字列連結
	 *
	 * @param ints : List
	 * @param sep: 区切り文字
	 * @return 文字列
	 */
	public synchronized static String concatList(int[] ints,String sep) {

		if (ints == null) {
			return "";
		}

		try {
			StringBuilder sb = new StringBuilder();
			int i = 0;
			for (int s : ints) {

				sb.append(s);

				if (i != ints.length - 1) {
					sb.append(sep);
					i++;
				}
			}

			return sb.toString();

		} catch (Exception e) {
			return "";
		}


	}

	/**
	 * DB用に日本時間の日付を文字列で取得
	 * @param date
	 * @return
	 */
	public static String getDateTimeString(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

}
