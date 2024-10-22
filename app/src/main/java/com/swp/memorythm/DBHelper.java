package com.swp.memorythm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "memorythm.db";
    private static final int DB_VERSION = 1;

    // 테이블 생성 쿼리
    // 폴더 테이블
    private static final String CREATE_TABLE_FOLDER = "CREATE TABLE if not exists folder(name TEXT primary key, sequence integer not null, count integer default 0);";
    /* 테이블 공통 컬럼
    id integer primary key autoincrement,
    title text NOT NULL,
    editdate DATETIME DEFAULT (datetime('now', 'localtime')),
    deleted integer DEFAULT 0,
    fixed integer DEFAULT 0,
    bgcolor text DEFAULT 'yellow',
    template_case text DEFAULT '테이블이름이랑 똑같이',
    folder_name TEXT DEFAULT '메모' FOREIGN KEY(folder_name) REFERENCES folder(name)
    */
    private static final String CREATE_TABLE_NONLINEMEMO = "CREATE TABLE if not exists nonlinememo(id integer primary key autoincrement, userdate text, content text, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'nonlinememo', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    private static final String CREATE_TABLE_LINEMEMO = "CREATE TABLE if not exists linememo(id integer primary key autoincrement, userdate text, content text, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'linememo', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    private static final String CREATE_TABLE_GRIDMEMO = "CREATE TABLE if not exists gridmemo(id integer primary key autoincrement, userdate text, content text, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'gridmemo', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    private static final String CREATE_TABLE_TODOLIST = "CREATE TABLE if not exists todolist(id integer primary key autoincrement, userdate text, content text, done text, splitkey text, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'todolist', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    private static final String CREATE_TABLE_WISHLIST = "CREATE TABLE if not exists wishlist(id integer primary key autoincrement, userdate text, content text, wished text, splitkey text, category integer, customcategory text, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'wishlist', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    private static final String CREATE_TABLE_SHOPPINGLIST = "CREATE TABLE if not exists shoppinglist(id integer primary key autoincrement, userdate text, content text, bought text, amount text, splitkey text, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'shoppinglist', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    private static final String CREATE_TABLE_REVIEW = "CREATE TABLE if not exists review(id integer primary key autoincrement, userdate text, " +
            "categoryCheck integer, categoryName text, starNum integer, score integer, reviewTitle text, reviewContent text, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'review', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    private static final String CREATE_TABLE_DAILYPLAN = "CREATE TABLE if not exists dailyplan(id integer primary key autoincrement, userdate text, contentAm text, contentPm text, weather integer, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'dailyplan', folder_name TEXT DEFAULT '메모',  FOREIGN KEY(folder_name) REFERENCES folder(name));";

    private static final String CREATE_TABLE_WEEKLYPLAN = "CREATE TABLE if not exists weeklyplan(id integer primary key autoincrement, userdate text, contentWeek text, contentDay text, splitKey text, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'weeklyplan', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    private static final String CREATE_TABLE_MONTHLYPLAN = "CREATE TABLE if not exists monthlyplan(id integer primary key autoincrement, userdate text, contentMonth text, splitKey text, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'monthlyplan', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    private static final String CREATE_TABLE_YEARLYPLAN = "CREATE TABLE if not exists yearlyplan(id integer primary key autoincrement, userdate text, contentYear text, splitKey text, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'yearlyplan', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    private static final String CREATE_TABLE_MONTHTRACKER = "CREATE TABLE if not exists monthtracker(id integer primary key autoincrement, userdate text, " +
            "goal text, dayCheck text, comment text," +
            "title text NOT NULL, editdate DATETIME DEFAULT(datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'monthtracker', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    private static final String CREATE_TABLE_HEALTHTRACKER = "CREATE TABLE if not exists healthtracker(id integer primary key autoincrement, userdate text, " +
            "waterCups text, breakfastTime text, breakfastMenu text, lunchTime text, lunchMenu text, snackMenu text, dinnerTime text, dinnerMenu text, exercise integer, aerobic integer, exerciseContent text, comment text, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'healthtracker', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    private static final String CREATE_TABLE_STUDYTRACKER = "CREATE TABLE if not exists studytracker(id integer primary key autoincrement, userdate text, " +
            "studyTimecheck text, commentAll text, commentTime text, splitKey text, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'studytracker', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    // 생성자
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // DB가 처음 만들어 질 때 호출. 여기서 테이블 생성 및 초기 레코드 삽입
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 외래키 허용
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }

        db.execSQL(CREATE_TABLE_FOLDER);
        db.execSQL("INSERT INTO folder('name', 'sequence') VALUES('메모', 0)");
        db.execSQL(CREATE_TABLE_NONLINEMEMO);
        db.execSQL(CREATE_TABLE_LINEMEMO);
        db.execSQL(CREATE_TABLE_GRIDMEMO);
        db.execSQL(CREATE_TABLE_TODOLIST);
        db.execSQL(CREATE_TABLE_WISHLIST);
        db.execSQL(CREATE_TABLE_SHOPPINGLIST);
        db.execSQL(CREATE_TABLE_REVIEW);
        db.execSQL(CREATE_TABLE_DAILYPLAN);
        db.execSQL(CREATE_TABLE_WEEKLYPLAN);
        db.execSQL(CREATE_TABLE_MONTHLYPLAN);
        db.execSQL(CREATE_TABLE_YEARLYPLAN);
        db.execSQL(CREATE_TABLE_MONTHTRACKER);
        db.execSQL(CREATE_TABLE_HEALTHTRACKER);
        db.execSQL(CREATE_TABLE_STUDYTRACKER);
    }

    // DB를 열 때 호출한다.
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    // DB를 업그레이드 할 때 호출. (DB 버전이 바뀌었을 때)
    // 기존 테이블 삭제 & 새로 생성 하거나 ALTER TABLE 로 Schema 수정
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if exists nonlinememo");
        db.execSQL("DROP TABLE if exists linememo");
        db.execSQL("DROP TABLE if exists gridmemo");
        db.execSQL("DROP TABLE if exists todolist");
        db.execSQL("DROP TABLE if exists wishlist");
        db.execSQL("DROP TABLE if exists shoppinglist");
        db.execSQL("DROP TABLE if exists review");
        db.execSQL("DROP TABLE if exists dailyplan");
        db.execSQL("DROP TABLE if exists weeklyplan");
        db.execSQL("DROP TABLE if exists monthlyplan");
        db.execSQL("DROP TABLE if exists yearlyplan");
        db.execSQL("DROP TABLE if exists monthtracker");
        db.execSQL("DROP TABLE if exists healthtracker");
        db.execSQL("DROP TABLE if exists studytracker");

        //참조되는 키 있어서 젤 마지막에 삭제
        db.execSQL("DROP TABLE if exists folder");

        onCreate(db);
    }
}