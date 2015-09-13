package jp.kobayo.save100.game;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import jp.kobayo.save100.R;
import jp.kobayo.save100.common.CommonUtils;

import java.util.*;

/**
 * Gameの処理に必要なユーティリティー.
 *
 * Created by Yosuke Kobayashi on 2014/12/27.
 */
public class GameUtils {

	// 上段の数式 左辺項
	private static final int[] upIds = {R.id.up_num1,R.id.up_num2,R.id.up_num3};

	// 下段の数式 左辺項
	private static final int[] downIds = {R.id.down_num1,R.id.down_num2,R.id.down_num3};

	/**
	 * 現在の上段、下段のそれぞれの列の合計値を算出します。
	 *
	 * @param  mustSum  合計値 通常は100
	 * @param  activity GameActivity
	 * @return true 正解
	 */
	public static boolean calc(int mustSum, Activity activity) {

		// 上段の数式
		int upSum = getSum(activity, upIds);

		if (upSum != mustSum) {
			return false;
		}

		// 下段の数式
		int downSum = getSum(activity,downIds);
		if (downSum != mustSum) {
			return false;
		}

		return true;
	}


	/**
	 * 次の数式が出てきた際、カラーをリセットする。
	 *
	 * @param position Position
	 * @param activity Activity
	 */
	public static void resetColor(Position position,Activity activity) {

		List<TextView> views = null;
		if (Position.up.equals(position)) {
			// 上段の数式
			views = getTextViews(activity,upIds);


		} else if (Position.down.equals(position)) {
			// 下段の数式
			views = getTextViews(activity,downIds);

		}

		if (views != null) {
			for (TextView v : views) {
				v.setTextColor(Color.WHITE);
			}
		}

	}

	/**
	 * 正解時処理 =100? となっているところを=100にする。
	 * @param mustSum 合計値
	 * @param activity Activity
	 */
	public static void setSum(int mustSum, Activity activity) {

		TextView upSum = (TextView)activity.findViewById(R.id.up_sum);
		upSum.setText(String.valueOf(mustSum));
		upSum.setTextColor(Color.RED);

		TextView downSum = (TextView)activity.findViewById(R.id.down_sum);
		downSum.setText(String.valueOf(mustSum));
		downSum.setTextColor(Color.RED);

	}


	/**
	 * 次の数式を生成する。
	 *
	 * @param num 項数
	 * @param mustSum 合計値(この場合は100)
	 * @param activity Activity
	 */
	public static void setLines(int num,int mustSum ,Activity activity) {

		List<int[]> lines = generateLines(num, mustSum);

		int[] firstLine = lines.get(0);

		// 上段の数式(左辺)
		List<TextView> upViews = getTextViews(activity,upIds);

		int i = 0;
		for (TextView v : upViews)  {

			v.setText(String.valueOf(firstLine[i]));
			v.setOnClickListener((View.OnClickListener) activity);
			i++;
		}

		// 右辺項
		TextView upSum = (TextView)activity.findViewById(R.id.up_sum);
		upSum.setText(CommonUtils.concat(String.valueOf(mustSum),"?"));
		upSum.setTextColor(Color.WHITE);

		int[] secondLine = lines.get(1);

		// 下段の数式
		List<TextView> downViews = getTextViews(activity,downIds);

		int j = 0;
		for (TextView v : downViews)  {

			v.setText(String.valueOf(secondLine[j]));
			v.setOnClickListener((View.OnClickListener) activity);
			j++;
		}

		TextView downSum = (TextView)activity.findViewById(R.id.down_sum);
		downSum.setText(CommonUtils.concat(String.valueOf(mustSum),"?"));
		downSum.setTextColor(Color.WHITE);

	}

	/**
	 * R.idからTextView を抽出し式の項目の合計を算出します。
	 * @param activity Activity
	 * @param ids R.id
	 */
	private static int getSum(Activity activity, int... ids) {

		// 対象の項目を呼び出し
		List<TextView> views = getTextViews(activity,ids);

		int res = 0;
		for (TextView v : views) {

			res += CommonUtils.parseInt(v.getText().toString());
		}

		return res;

	}

	/**
	 * 指定したIからTextViewを取得します数式の項目を全て取得する用.
	 * @param activity Activity
	 * @param ids R.id
	 */
	private static List<TextView> getTextViews(Activity activity, int... ids) {

		if (ids == null || ids.length == 0) {
			return Collections.emptyList();
		}

		try {

			List<TextView> views = new ArrayList<TextView>();
			for (int id : ids) {

				TextView view = (TextView)activity.findViewById(id);
				views.add(view);
			}

			return views;
		} catch (Exception e) {
			return Collections.emptyList();
		}

	}

	/**
	 * 上下二本の数字列の列を生成します。
	 *
	 * @return List<int[]>
	 */
	private static List<int[]> generateLines(int num,int mustSum) {

		// 上段を生成
		int[] firstLine = generateNumbers(num,mustSum);
		// 下段は上段と同じパターンではないかをチェックしながら生成
		int[] secondLine;

		boolean isOk = false;
		while(true) {

			secondLine = generateNumbers(num,mustSum);
			for (int i : secondLine) {
				// 一つでも違う値があればOK。
				if (!Arrays.asList(firstLine).contains(i)) {
					isOk = true;
					break;
				}
			}
			// ラベルを使う方法もあるが、わかりにくくなるので、ここはフラグで処理する。
			if (isOk) {
				break;
			}
		}

		// 一文字上下を入れ替えてList<int[]>として返す。
		return swap(firstLine,secondLine,num);
	}

	/**
	 * 合計して100になる数字の列を作る。
	 *
	 * @param num : 加算する数字の個数。
	 * @return 合計して100になる数字の配列。
	 */
	private static int[] generateNumbers(int num,int mustSum) {

		// Randomクラス使用
		Random rnd = new Random();

		// 結果配列
		int[] res = new int[num];

		// 合計して100になる数字列を作るので、100までの残り。
		int left = mustSum;
		for (int i = 0;i < num;i++) {

			if (i == num - 1) {
				res[i] = left;
				break;
			}

			while (true) {

				// 1 〜 (残り値 + 1) までの整数をランダムで作る。
				res[i] = rnd.nextInt(left) + 1;

				// 残り値が残り項数より大きければOK (100 + 0 + 0 = 100? は認めない)
				if ((left - res[i]) > (num - i - 1)) {
					// 100まで残り
					left = left - res[i];
					break;
				}
			}

		}
		return res;

	}


	/**
	 * 二つの数字の列の一文字を入れ替える.
	 *
	 * @param firstLine 上段数列
	 * @param secondLine 下段数列
	 */
	private static List<int[]> swap(int[] firstLine,int[] secondLine,int num) {

		Random rnd = new Random();
		int top = rnd.nextInt(num - 1) + 1;
		int bottom = rnd.nextInt(num - 1) + 1;

		int tmp = firstLine[top];
		firstLine[top] = secondLine[bottom];
		secondLine[bottom] = tmp;

		List<int[]> res = new ArrayList<int[]>();

		res.add(firstLine);
		res.add(secondLine);

		return res;

	}
}
