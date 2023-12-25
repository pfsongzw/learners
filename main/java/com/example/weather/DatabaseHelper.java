package com.example.weather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WeatherDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CITIES = "Cities";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CODE = "code";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建新的数据库表
        String CREATE_CITIES_TABLE =
                "CREATE TABLE " + TABLE_CITIES + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_NAME + " TEXT,"
                        + COLUMN_CODE + " TEXT);";
        db.execSQL(CREATE_CITIES_TABLE);
    }
    public boolean isDataInitialized() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT 1 FROM " + TABLE_CITIES;
        Cursor cursor = db.rawQuery(countQuery, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 更新数据库版本时删除旧表并重新创建
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITIES);
        onCreate(db);
    }

    // 添加城市和它的代码到数据库
    public void addCity(String name, String code) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_CODE, code);

        db.insertWithOnConflict(TABLE_CITIES, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    // 根据城市名称查询代码
    @SuppressLint("Range")
    public String getCityCode(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CITIES, new String[] {COLUMN_CODE}, COLUMN_NAME + "=?",
                new String[] {name}, null, null, null, null);

        String code = null;
        if (cursor.moveToFirst()) {
            code = cursor.getString(cursor.getColumnIndex(COLUMN_CODE));
        }

        cursor.close();
        db.close();

        return code;
    }
}