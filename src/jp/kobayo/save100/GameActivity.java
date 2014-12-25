package jp.kobayo.save100;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.*;

public class GameActivity extends Activity {

	// 一列にならぶ加算する数字の数。
	private static int numbersOneLine = 3;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);

		setLines(numbersOneLine);

	}

	private void setLines(int num) {

		List<int[]> lines = generateLines(num);

		int[] firstLine = lines.get(0);

		TextView up1 = (TextView)findViewById(R.id.up_num1);
		up1.setText(String.valueOf(firstLine[0]));

		TextView up2 = (TextView)findViewById(R.id.up_num2);
		up2.setText(String.valueOf(firstLine[1]));

		TextView up3 = (TextView)findViewById(R.id.up_num3);
		up3.setText(String.valueOf(firstLine[2]));


		int[] secondLine = lines.get(1);

		TextView down1 = (TextView)findViewById(R.id.down_num1);
		down1.setText(String.valueOf(secondLine[0]));

		TextView down2 = (TextView)findViewById(R.id.down_num2);
		down2.setText(String.valueOf(secondLine[1]));

		TextView down3 = (TextView)findViewById(R.id.down_num3);
		down3.setText(String.valueOf(secondLine[2]));

	}

	/**
	 * 上下二本の数字列の列を生成します。
	 *
	 * @return List<int[]>
	 */
	private List<int[]> generateLines(int num) {

		// 上段を生成
		int[] firstLine = generateNumbers(num);
		// 下段は上段と同じパターンではないかをチェックしながら生成
		int[] secondLine;

		CheckLoop:
		while(true) {

			secondLine = generateNumbers(num);
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
	private int[] generateNumbers(int num) {

		// Randomクラス使用
		Random rnd = new Random();

		// 結果配列
		int[] res = new int[num];

		// 合計して100になる数字列を作るので、100までの残り。
		int left = 100;
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
	private List<int[]> swap(int[] firstLine,int[] secondLine,int num) {

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

	// 10秒カウントダウンする
//	new CountDownTimer(10000,1000){
//		TextView count_txt = (TextView)findViewById(R.id.textView1);
//		// カウントダウン処理
//	public void onTick(long millisUntilFinished){
//		count_txt.setText("カウントダウン"+millisUntilFinished/1000);
//	}
//	// カウントが0になった時の処理
//	public void onFinish(){
//		count_txt.setText("タイムオーバー");
//	}
//}.start();
}
