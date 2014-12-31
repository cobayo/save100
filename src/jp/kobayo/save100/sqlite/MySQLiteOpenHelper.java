package jp.kobayo.save100.sqlite;

/**
 * Created by kobayo on 2014/12/29.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import jp.kobayo.save100.common.CommonUtils;

import java.util.Date;


public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	private static final String DB = "save100.db";
	private static final int DB_VERSION = 2;
	private static final String TABLE_NAME = "scores";
	private static final String CREATE_TABLE = "create table scores (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"high_score INTEGER NOT NULL, latest_score INTEGER NOT NULL,modified TEXT NOT NULL );";
	private static final String DROP_TABLE = "drop table scores;";
	protected SQLiteDatabase db;

	public MySQLiteOpenHelper(Context c) {
		super(c, DB, null, DB_VERSION);
	}
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_TABLE);
		Log.i("SQLite","Created");
	}

	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		database.execSQL(DROP_TABLE);
		onCreate(database);
	}


	public MySQLiteOpenHelper open() {
		db = this.getWritableDatabase();
		return this;
	}
	/**
	 * 初回insert
	 * @param currentScore : 取得したスコア
	 */
	public void insert(int currentScore) {

		// 今日の日付
		String now = CommonUtils.getDateTimeString(new Date());

		// パラメータを入れる。
		ContentValues contentValues = new ContentValues();
		contentValues.put("high_score",currentScore);
		contentValues.put("latest_score",currentScore);
		contentValues.put("modified", now);

		db.insertOrThrow(TABLE_NAME, null, contentValues);

	}

	public Cursor getScore() {
		String[] columns = {"_id","high_score","latest_score"};
		return db.query(TABLE_NAME, columns ,null,null,null,null,"high_score","1");
	}

	public void update(int _id, int highScore ,int currentScore) {

		// 今日の日付
		String now = CommonUtils.getDateTimeString(new Date());

		// set句のパラメータ
		ContentValues contentValues = new ContentValues();
		contentValues.put("high_score",highScore);
		contentValues.put("latest_score",currentScore);
		contentValues.put("modified",now);

		// where句
		String[] params = {String.valueOf(_id)};
		db.update(TABLE_NAME,contentValues, "_id=?",params);

	}
}