package com.example.secnote;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Point;

public class Database extends SQLiteOpenHelper {
	private static final String TAB_NAME = "POINTS";
	private static final String TAB_ID = "ID";
	private static final String TAB_X = "X";
	private static final String TAB_Y = "Y";

	public Database(Context context) {
		super(context, "Secnote.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = String
				.format("create table %s (%s INTEGER PRIMARY KEY, %s INTEGER NOT NULL, %s INTEGER NOT NULL)",
						TAB_NAME, TAB_ID, TAB_X, TAB_Y);
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public void storePoints(List<Point> points) {
		SQLiteDatabase db = getWritableDatabase();

		db.delete(TAB_NAME, null, null);

		int i = 0;
		for (Point point : points) {
			ContentValues values = new ContentValues();
			values.put(TAB_ID, i);
			values.put(TAB_X, point.x);
			values.put(TAB_Y, point.y);
			db.insert(TAB_NAME, null, values);
			i++;
		}

		db.close();
	}

	public List<Point> getPoints() {
		List<Point> points = new ArrayList<Point>();
		SQLiteDatabase db = getReadableDatabase();
		String sql = String.format("SELECT %s, %s FROM %s ORDER BY %s", TAB_X,
				TAB_Y, TAB_NAME, TAB_ID);
		Cursor cursor = db.rawQuery(sql, null);

		while (cursor.moveToNext()) {
			int x = cursor.getInt(0);
			int y = cursor.getInt(1);
			points.add(new Point(x, y));
		}
		db.close();
		return points;

	}
}
