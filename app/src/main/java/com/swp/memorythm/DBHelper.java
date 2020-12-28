package com.swp.memorythm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    //테이블 생성 쿼리들
    private static final String CREATE_TABLE_LINEMEMO = "CREATE TABLE if not exists linememo(id integer primary key autoincrement, date text, content text, fixed integer, deleted integer, folder integer, FOREIGN KEY(folder) REFERENCES folders(id));";
    private static final String CREATE_TABLE_FOLDERS = "CREATE TABLE if not exists folders(id integer primary key autoincrement);";

    public DBHelper(Context context) {
        super(context, "memorythm.db", null, 1);
    }

    //최초 한번만 생성 되는듯?
    @Override
    public void onCreate(SQLiteDatabase db) {

        //재설치할때 테이블 있으면 삭제하고 다시 만듦
        db.execSQL("DROP TABLE IF EXISTS linememo");
        db.execSQL("DROP TABLE IF EXISTS folders");

        if (!db.isReadOnly()) {
            // 외래키 사용할 수 있게
            db.execSQL("PRAGMA foreign_keys=ON;");
        }

        //쿼리 실행
        db.execSQL(CREATE_TABLE_FOLDERS);
        db.execSQL(CREATE_TABLE_LINEMEMO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE if exists linememo");
        db.execSQL("DROP TABLE if exists folders");
        onCreate(db);
    }
}