package com.swp.memorythm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SuhoDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "suhotest.db";
    private static final int DB_VERSION = 1;

    public static final String CREATE_FOLDERS_TABLE = "CREATE TABLE if not exists folder(name TEXT primary key);";
    public static final String CREATE_NONLINE_TABLE = "CREATE TABLE if not exists nonline(id INTEGER primary key autoincrement, isdelete INTEGER DEFAULT 0, writedate DATETIME DEFAULT (datetime('now', 'localtime')), content TEXT, folder_name TEXT, FOREIGN KEY(folder_name) REFERENCES folder(name));";

    public SuhoDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        if (!sqLiteDatabase.isReadOnly()) {
            // 외래키 사용할 수 있게
            sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON;");
        }

        sqLiteDatabase.execSQL(CREATE_FOLDERS_TABLE);
        sqLiteDatabase.execSQL("INSERT INTO folder('name') VALUES('메모')");
        sqLiteDatabase.execSQL(CREATE_NONLINE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int old, int now) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS nonline;");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS folders;");
        onCreate(sqLiteDatabase);
    }
}
