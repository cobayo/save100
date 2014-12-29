package jp.kobayo.save100.game;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.TextView;
import jp.kobayo.save100.R;
import jp.kobayo.save100.common.CommonUtils;
import jp.kobayo.save100.sqlite.MySQLiteOpenHelper;

import java.util.Date;

/**
 * Created by kobayo on 2014/12/28.
 */
public class ScoreManager {

	// 接続したデータベースオブジェクト
	private static SQLiteDatabase mydb;

	// ハイスコア取得クエリ
	private static final String selectHighScore = "select high_score from scores order by high_score desc limit 1;";

	// 最新スコア取得クエリ
	private static final String selectLatestScore = "select latest_score from scores order by latest_score desc limit 1;";

	// 初回時インサート
	private static final String insertQuery = "insert into scores (high_score,latest_score,modified) values(?,?,?);";

	//ハイスコア更新時
	private static final String updateWithHighScore = "update scores set high_score = ?,latest_score = ?,modified = ? where high_score = ?;";

	// ハイスコア未更新時
	private static final String update = "update scores set latest_score = ?,modified = ? where high_score = ?";

	/**
	 * 残り時間などからスコアを算出します。
	 *
	 * @param restTime
	 * @param mustSum
	 * @return
	 */
	public static long getScore(int restTime,int mustSum) {
		return (restTime * mustSum) / 10;
	}

	public static void save(long currentScore, Activity activity) {

		// 今日の日付文字列 yyyy-MM-dd HH:mm:ss
		String currentTimeString = CommonUtils.getDateTimeString(new Date());
		Log.d("today",currentTimeString);

		// スコアを文字列へキャスト
		String currentScoreString = String.valueOf(currentScore);

		MySQLiteOpenHelper helper = new MySQLiteOpenHelper(activity.getApplicationContext());
		mydb = helper.getWritableDatabase();
		Cursor res = mydb.rawQuery(selectHighScore, new String[0]);

		System.out.println(res);

		// 既存レコードがないとき
		if (res.getCount() == 0) {
			String[] params = {currentScoreString, currentScoreString, currentTimeString};
			mydb.rawQuery(insertQuery,params);
			Log.d("new","inserted");
			return;
		}

		// 既存レコードがあるとき
		long highScore = res.getLong(0);
		Log.d("high_score",String.valueOf(highScore));

		String highScoreString = String.valueOf(highScore);
		if (currentScore > highScore ) {

			String[] updateParams = {currentScoreString,currentScoreString,currentTimeString,highScoreString};
			mydb.rawQuery(updateWithHighScore,updateParams);

		} else {

			String[] updateParams = {currentScoreString,currentTimeString,highScoreString};
			mydb.rawQuery(update,updateParams);

		}


	}

	// ハイスコア
	public static String getHighScore(Activity activity) {

		MySQLiteOpenHelper helper = new MySQLiteOpenHelper(activity.getApplicationContext());
		SQLiteDatabase mydb = helper.getReadableDatabase();

		String[] params = {};
		Cursor res = mydb.rawQuery(selectHighScore,params);

		if (res.move(0)) {
			Log.d("high score",String.valueOf(res.getLong(0)));
			return String.valueOf(res.getLong(0));
		}

		return "--";

	}

	// 最新スコア 将来的なことを考えてハイスコアとは分離しておく。
	public static String getLatestScore(Activity activity) {

		MySQLiteOpenHelper helper = new MySQLiteOpenHelper(activity.getApplicationContext());
		SQLiteDatabase mydb = helper.getReadableDatabase();

		String[] params = {};
		Cursor res = mydb.rawQuery(selectLatestScore,params);

		if (res.move(0)) {
			Log.d("latest score",String.valueOf(res.getLong(0)));
			return String.valueOf(res.getLong(0));
		}

		return "--";

	}

}
