package jp.kobayo.save100.game;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import jp.kobayo.save100.R;
import jp.kobayo.save100.common.CommonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Gameの処理に必要なユーティリティーです。
 *
 * Created by kobayo on 2014/12/27.
 */
public class GameUtils {


	/**
	 * 現在の上段、下段のそれぞれの列の合計値を算出します。
	 *
	 * @param mustSum : 合計値 通常は100
	 * @param activity : GameActivity
	 * @return true : 正解
	 */
	public static boolean calc(int mustSum, Activity activity) {

		TextView up1 = (TextView)activity.findViewById(R.id.up_num1);
		int upNum1 = CommonUtils.parseInt(up1.getText().toString());

		TextView up2 = (TextView)activity.findViewById(R.id.up_num2);
		int upNum2 = CommonUtils.parseInt(up2.getText().toString());

		TextView up3 = (TextView)activity.findViewById(R.id.up_num3);
		int upNum3 = CommonUtils.parseInt(up3.getText().toString());

		if ((upNum1 + upNum2 + upNum3) != mustSum) {
			return false;
		}

		TextView down1 = (TextView)activity.findViewById(R.id.down_num1);
		int downNum1 = CommonUtils.parseInt(down1.getText().toString());

		TextView down2 = (TextView)activity.findViewById(R.id.down_num2);
		int downNum2 = CommonUtils.parseInt(down2.getText().toString());

		TextView down3 = (TextView)activity.findViewById(R.id.down_num3);
		int downNum3 = CommonUtils.parseInt(down3.getText().toString());

		if ((downNum1 + downNum2 + downNum3) != mustSum) {
			return false;
		}

		return true;
	}


	public static void resetColor(Position position,Activity activity) {

		if (Position.up.equals(position)) {

			TextView up1 = (TextView)activity.findViewById(R.id.up_num1);
			up1.setTextColor(Color.WHITE);

			TextView up2 = (TextView)activity.findViewById(R.id.up_num2);
			up2.setTextColor(Color.WHITE);

			TextView up3 = (TextView)activity.findViewById(R.id.up_num3);
			up3.setTextColor(Color.WHITE);

		} else if (Position.down.equals(position)) {

			TextView down1 = (TextView)activity.findViewById(R.id.down_num1);
			down1.setTextColor(Color.WHITE);

			TextView down2 = (TextView)activity.findViewById(R.id.down_num2);
			down2.setTextColor(Color.WHITE);

			TextView down3 = (TextView)activity.findViewById(R.id.down_num3);
			down3.setTextColor(Color.WHITE);

		}

	}

	public static void setSum(int mustSum, Activity activity) {

		TextView upSum = (TextView)activity.findViewById(R.id.up_sum);
		upSum.setText(String.valueOf(mustSum));
		upSum.setTextColor(Color.RED);

		TextView downSum = (TextView)activity.findViewById(R.id.down_sum);
		downSum.setText(String.valueOf(mustSum));
		downSum.setTextColor(Color.RED);

	}



	public static void setLines(int num,int mustSum ,Activity activity) {

		List<int[]> lines = generateLines(num,mustSum);

		int[] firstLine = lines.get(0);

		TextView up1 = (TextView)activity.findViewById(R.id.up_num1);
		up1.setText(String.valueOf(firstLine[0]));
		up1.setOnClickListener((View.OnClickListener)activity);

		TextView up2 = (TextView)activity.findViewById(R.id.up_num2);
		up2.setText(String.valueOf(firstLine[1]));
		up2.setOnClickListener((View.OnClickListener)activity);

		TextView up3 = (TextView)activity.findViewById(R.id.up_num3);
		up3.setText(String.valueOf(firstLine[2]));
		up3.setOnClickListener((View.OnClickListener)activity);

		TextView upSum = (TextView)activity.findViewById(R.id.up_sum);
		upSum.setText(CommonUtils.concat(String.valueOf(mustSum),"?"));
		upSum.setTextColor(Color.WHITE);

		int[] secondLine = lines.get(1);

		TextView down1 = (TextView)activity.findViewById(R.id.down_num1);
		down1.setText(String.valueOf(secondLine[0]));
		down1.setOnClickListener((View.OnClickListener) activity);

		TextView down2 = (TextView)activity.findViewById(R.id.down_num2);
		down2.setText(String.valueOf(secondLine[1]));
		down2.setOnClickListener((View.OnClickListener)activity);

		TextView down3 = (TextView)activity.findViewById(R.id.down_num3);
		down3.setText(String.valueOf(secondLine[2]));
		down3.setOnClickListener((View.OnClickListener)activity);

		TextView downSum = (TextView)activity.findViewById(R.id.down_sum);
		downSum.setText(CommonUtils.concat(String.valueOf(mustSum),"?"));
		downSum.setTextColor(Color.WHITE);

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

		CheckLoop:
		while(true) {

			secondLine = generateNumbers(num,mustSum);
			for (int i : secondLine) {
				// 一つでも違う値があればOK。
				if (!Arrays.asList(firstLine).contains(i)) {
					break CheckLoop;
				}
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
				// 1 〜 (100 - num) までの整数をランダムで作る。
				res[i] = rnd.nextInt(left - num) + 1;

				if ((left - res[i]) > 0) {
					// 100まで残り
					left = left - res[i];
					break;
				}
			}

		}
		return res;

	}


	/**
	 * 二つの数字の列の一文字を入れ替える
	 *
	 * @param firstLine
	 * @param secondLine
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
