package jp.kobayo.save100.game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import jp.kobayo.save100.R;
import jp.kobayo.save100.common.CommonUtils;
import jp.kobayo.save100.top.TopActivity;


public class GameActivity extends Activity implements OnClickListener {

	// 一列にならぶ加算する数字の数。
	private static final int numbersOneLine = 3;

	// 上段で選択中の数字
	private TextView currentUpView;

	// 下段で選択中の数字
	private TextView currentDownView;

	// カウンタView
	private TextView counter;

	// スコアView
	private TextView score;

	// 正解時演出用画像
	private ImageView success;

	// 失敗時演出用画像
	private ImageView fail;

	// CountDownTimer
	private CountDownTimer cdt;

	// 正解となる合計値
	private int mustSum = 100;

	// タイマー
	private int limitSecond = 10;

	// スコア
	private long currentScore = 0L;

	// lock
	private boolean lock = false;

	/**
	 * onCreate
	 * Called when the activity is first created.
	 * @param savedInstanceState : Bundle
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_m);

		// 初期化し、ゲームスタート
		initGame();
	}


	/**
	 * ゲームを最初から開始します。
	 */
	private void initGame() {

		// 正解時画像をセットし、初期では非表示
		success = (ImageView)findViewById(R.id.success);
		success.setOnClickListener(this);
		success.setVisibility(View.INVISIBLE);

		// 失敗時画像をセット 初期は非表示
		fail = (ImageView)findViewById(R.id.fail);
		fail.setOnClickListener(this);
		fail.setVisibility(View.INVISIBLE);



		// 色リセット
		GameUtils.resetColor(Position.up, this);
		GameUtils.resetColor(Position.down, this);

		// 初期化
		currentUpView = null;
		currentDownView = null;
		counter = (TextView)findViewById(R.id.counter);
		score = (TextView)findViewById(R.id.score);

		// カウンター初期値導入
		counter.setText(String.valueOf(limitSecond));

		// スコア初期化
		currentScore = 0L;
		score.setText(String.valueOf(0));

		// 問題作成
		GameUtils.setLines(numbersOneLine, mustSum, this);

		// タイマー生成
		this.cdt = CountDownTimerFactory.create(this, limitSecond);
		this.cdt.start();

		// ロックフラグ初期化
		lock = false;
	}


	/**
	 * onClick
	 * @param view : クリックしたView
	 */
	public void onClick(View view) {

		Result result = null;
		switch(view.getId()) {

			// 上段のView
			case R.id.up_num1:
			case R.id.up_num2:
			case R.id.up_num3:
				// 正解時演出&処理中
				if (lock) {
					return;
				}
				// 両段ともに選択したか、正解かどうかを判断。
				result = check((TextView) view, Position.up);

				break;

			// 下段のView
			case R.id.down_num1:
			case R.id.down_num2:
			case R.id.down_num3:

				// 正解時演出&処理中
				if (lock) {
					return;
				}

				// 両段ともに選択したか、正解かどうかを判断。
				result = check((TextView) view, Position.down);
				break;

			case R.id.success:
				// 次の問題へ。
				next();
				break;

			case R.id.fail:
				// スコア保存処理をしてトップへ戻る
				backTop();
				break;
			default:
				break;
		}


		if (Result.save.equals(result)) {
			// 正解ならタイマー止めて正解時演出。
			success();
		} else if (Result.fail.equals(result)) {
			// 一度でも不正解を出したら終わり。
			fail();
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

			// 選択中のView確保
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

		// 選択した数字を取得
		CharSequence selectedUp   = currentUpView.getText();
		CharSequence selectedDown = currentDownView.getText();

		// 両方が選択された場合、数字を交換する。
		currentUpView.setText(selectedDown);
		currentDownView.setText(selectedUp);

		// 計算し、合計値が正しければ正解となる。
		return (GameUtils.calc(mustSum, this)) ? Result.save :Result.fail ;

	}

	/**
	 * 正解時。
	 */
	private void success() {

		// タイマー再スタートまでロックフラグを立てる。
		lock = true;

		// タイマーストップ
		this.cdt.cancel();

		// 正解画像を表示
		success.setVisibility(View.VISIBLE);

		// 選択解除
		this.currentUpView = null;
		this.currentDownView = null;
		GameUtils.resetColor(Position.up, this);
		GameUtils.resetColor(Position.down, this);

		// スコア計算
		int restTime = CommonUtils.parseInt(counter.getText().toString());

		// スコア加算
		currentScore += ScoreManager.getScore(restTime, mustSum);
		score.setText(String.valueOf(currentScore));

	}

	/**
	 * 次の問題へ進む
	 */
	private void next() {

		// 正解時画像を非表示にする。
		success.setVisibility(View.INVISIBLE);

		// 新しい問題を作る。
		GameUtils.setLines(numbersOneLine, mustSum, this);

		// カウンター再スタート
		counter.setText(String.valueOf(limitSecond));
		this.cdt.start();

		// ロック解除
		lock = false;

	}

	/**
	 * 失敗時処理
	 */
	private void fail() {
		lock = true;
		fail.setVisibility(View.VISIBLE);
	}

	// スコアを保存し、Topへ戻ります。
	private void backTop() {

		// スコアを保存する処理
		ScoreManager.save(currentScore, this);

		// Topへ戻る
		Intent intent = new Intent(this, TopActivity.class);
		startActivity(intent);
	}

}
