package jp.kobayo.save100.utils;

import java.util.List;

/**
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


}
