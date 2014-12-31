package jp.kobayo.save100.game;

import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import jp.kobayo.save100.sqlite.MySQLiteOpenHelper;

/**
 * Created by kobayo on 2014/12/28.
 */
public class ScoreManager {

	private MySQLiteOpenHelper helper;

	public static ScoreManager create(Activity activity) {
		return new ScoreManager(activity);
	}

	private ScoreManager(Activity activity) {
		this.helper = new MySQLiteOpenHelper(activity.getApplicationContext());
		this.helper.open();
	}

	/**
	 * 残り時間などからスコアを算出します。
	 *
	 * @param restTime
	 * @param mustSum
	 * @return
	 */
	public static int calcScore(int restTime,int mustSum) {
		return (restTime * mustSum) / 10;
	}

	/**
	 * 保存
	 *
	 * @param currentScore
	 */
	public void save(int currentScore) {

		Log.i("Current Score",String.valueOf(currentScore));

		Cursor res = helper.getScore();

		// 既存レコードがないとき
		if (res.getCount() == 0) {
			helper.insert(currentScore);
			Log.i("New Record","inserted");
			return;
		}

		// 既存レコードがあるとき
		res.moveToFirst();
		int id = res.getInt(0);
		int highScore = res.getInt(1);
		Log.i("high_score",String.valueOf(highScore));

		// 過去のハイスコアを超えているかどうか
		if (currentScore > highScore) {
			// ハイスコアも現在のスコアで更新
			helper.update(id,currentScore,currentScore);

		} else {
			helper.update(id,highScore,currentScore);
		}

	}

	/**
	 * 表示用にハイスコアを文字列で返します。
	 */
	public String getHighScore() {

		Cursor res = helper.getScore();

		if (res.getCount() == 0) {
			return "-- ";
		}

		res.moveToFirst();
		int highScore = res.getInt(1);


		return String.valueOf(highScore);
	}

	public String getLatestScore() {

		Cursor res = helper.getScore();

		if (res.getCount() == 0) {
			return "-- ";
		}

		res.moveToFirst();
		int latestScore = res.getInt(2);


		return String.valueOf(latestScore);
	}

	public void close() {
		if (helper != null) {
			this.helper.close();
		}
	}

}
