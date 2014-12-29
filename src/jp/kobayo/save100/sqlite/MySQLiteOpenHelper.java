package jp.kobayo.save100.sqlite;

/**
 * Created by kobayo on 2014/12/29.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	private static final String DB = "save100.db";
	private static final int DB_VERSION = 1;
	private static final String CREATE_TABLE = "create table scores (high_score integer not null, latest_score integer not null,modified text not null );";
	private static final String DROP_TABLE = "drop table save100;";

	public MySQLiteOpenHelper(Context c) {
		super(c, DB, null, DB_VERSION);
	}
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_TABLE);
		onCreate(db);
	}
}