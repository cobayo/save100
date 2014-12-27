package jp.kobayo.save100.game;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import jp.kobayo.save100.R;


public class GameActivity extends Activity implements OnClickListener {

	// 一列にならぶ加算する数字の数。
	private static int numbersOneLine = 3;

	// 上段で選択中の数字
	private TextView currentUpView;

	// 下段で選択中の数字
	private TextView currentDownView;

	// CountDownTimer
	private CountDownTimer cdt;

	private int mustSum = 100;

	/**
	 * Called when the activity is first created.
	 */
	@Override


	/**
	 * onCreate
	 */
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);

		GameUtils.setLines(numbersOneLine, mustSum ,this);

		Log.d("onCreate", "Start");

		this.cdt = CountDownTimerFactory.create(this);
		this.cdt.start();

	}

	/**
	 * onClick
	 * @param view : クリックしたView
	 */
	public void onClick(View view) {

		switch(view.getId()) {

			// 上段のView
			case R.id.up_num1:
			case R.id.up_num2:
			case R.id.up_num3:
				// 選択した文字を既に選択している文字を元に正解かどうか判断。正解ならタイマーリスタート
				// 一度でも不正解を出したら終わり。
				if (Result.save.equals(check((TextView) view, Position.up))) {
					success();
				}
				break;

			// 下段のView
			case R.id.down_num1:
			case R.id.down_num2:
			case R.id.down_num3:
				if (Result.save.equals(check((TextView) view, Position.down))) {
					success();
				}
				break;

			default:
				break;
		}


	}

	/**
	 * 選択された数字をもとに、ゲームの状態をどうするか判断します。
	 * 上下両方とも選択されている場合は、計算して、正解か不正解かを判定し、
	 * ゲームを続行するかどうかを決めます。
	 *
	 * @param selectedView : クリックしたView
	 * @param position : 上下どちらのラインか。
	 * @return Result : 正解、不正解、一つしか選択していないの三つのステータスがあります。
	 */
	private Result check(TextView selectedView, Position position) {

		// Nullチェック
		if (selectedView.getText() == null) {
			return Result.fail;
		}

		// 色を一旦リセットし、選択したものだけ赤くする処理
		GameUtils.resetColor(position, this);
		selectedView.setTextColor(Color.RED);

		// 選択した数字を現在選択中の変数として確保
		if (Position.up.equals(position)) {

			this.currentUpView = selectedView;
			// 一つの数字しか選択していなければ処理続行
			if (this.currentDownView == null) {
				return Result.singleSelected;
			}

		} else if (Position.down.equals(position)) {

			this.currentDownView = selectedView;
			if (this.currentUpView == null) {
				return Result.singleSelected;
			}

		}

		// 両方が選択された場合、数字を交換
		CharSequence selectedUp   = currentUpView.getText();
		CharSequence selectedDown = currentDownView.getText();

		currentUpView.setText(selectedDown);
		currentDownView.setText(selectedUp);

		return (GameUtils.calc(mustSum, this)) ? Result.save :Result.fail ;

	}

	/**
	 * 正解時。
	 */
	private void success() {

		GameUtils.setLines(numbersOneLine, mustSum, this);
		this.currentUpView = null;
		this.currentDownView = null;
		GameUtils.resetColor(Position.up, this);
		GameUtils.resetColor(Position.down, this);
		this.cdt.cancel();
		this.cdt.start();

	}

}
