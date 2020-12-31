package com.swp.memorythm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "memorythm.db";
    private static final int DB_VERSION = 1;

    // 테이블 생성 쿼리
    // 폴더 테이블
    private static final String CREATE_TABLE_FOLDER = "CREATE TABLE if not exists folder(name TEXT primary key);";

    /* 테이블마다 공통 컬럼
    id integer primary key autoincrement,
    title text NOT NULL,
    editdate DATETIME DEFAULT (datetime('now', 'localtime')),
    deleted integer DEFAULT 0,
    fixed integer DEFAULT 0,
    bgcolor text DEFAULT 'yellow',
    template_case text DEFAULT '테이블이름이랑 똑같이',
    folder_name TEXT DEFAULT '메모' FOREIGN KEY(folder_name) REFERENCES folder(name)
    */

    //재석 탬플릿
    private static final String CREATE_TABLE_LINEMEMO = "CREATE TABLE if not exists linememo(id integer primary key autoincrement, userdate text, content text, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'linememo', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    private static final String CREATE_TABLE_DAILYPLAN = "CREATE TABLE if not exists dailyplan(id integer primary key autoincrement, userdate text, contentAm text, contentPm text, weather text, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'dailyplan', folder_name TEXT DEFAULT '메모',  FOREIGN KEY(folder_name) REFERENCES folder(name));";

    private static final String CREATE_TABLE_WEEKLYPLAN = "CREATE TABLE if not exists weeklyplan(id integer primary key autoincrement, userdate text, " +
            "contentMon text, contentTue text, contentWed text, contentThu text, contentFri text, contentSat text, contentSun text, " +
            "checkedMon integer, checkedTue integer, checkedWed integer, checkedThu integer, checkedFri integer, checkedSat integer, checkedSun integer, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'weeklyplan', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    private static final String CREATE_TABLE_MONTHLYPLAN = "CREATE TABLE if not exists monthlyplan(id integer primary key autoincrement, userdate text, " +
            "'0' text, '1' text, '2' text, '3' text, '4' text, '5' text, '6' text, '7' text, '8' text, '9' text, '10' text, '11' text, '12' text, '13' text, '14' text, " +
            "'15' text, '16' text, '17' text, '18' text, '19' text, '20' text, '21' text, '22' text, '23' text, '24' text, '25' text, '26' text, '27' text, '28' text, " +
            "'29' text, '30' text, '31' text, '32' text, '33' text, '34' text, '35' text, '36' text, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'monthlyplan', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    private static final String CREATE_TABLE_YEARLYPLAN = "CREATE TABLE if not exists yearlyplan(id integer primary key autoincrement, userdate text, " +
            "contentJan text, contentFeb text, contentMar text, contentApr text, contentMay text, contentJun text, contentJul text, contentAug text, contentSep text, contentOct text, contentNov text, contentDec text, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'yearlyplan', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    //윤경 탬플릿
    private static final String CREATE_TABLE_GRIDMEMO = "CREATE TABLE if not exists gridmemo(id integer primary key autoincrement, userdate text, content text, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'gridmemo', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    private static final String CREATE_TABLE_HEALTHTRACKER = "CREATE TABLE if not exists healthtracker(id integer primary key autoincrement, userdate text, " +
            "waterCups text, breakfastTime text, breakfastMenu text, lunchTime text, lunchMenu text, snackMenu text, dinnerTime text, dinnerMenu text, exercise integer, aerobic integer, exerciseContent text, comment text, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'healthtracker', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    private static final String CREATE_TABLE_MONTHTRACKER = "CREATE TABLE if not exists monthtracker(id integer primary key autoincrement, userdate text, " +
            "goal text, dayCheck text, comment text," +
            "title text NOT NULL, editdate DATETIME DEFAULT(datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'monthtracker', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    private static final String CREATE_TABLE_STUDYTRACKER = "CREATE TABLE if not exists studytracker(id integer primary key autoincrement, userdate text, " +
            "studyTimecheck text, commentAll text, commentTime text, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'studytracker', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    private static final String CREATE_TABLE_REVIEW = "CREATE TABLE if not exists review(id integer primary key autoincrement, userdate text, " +
            "categoryCheck integer, categoryName text, starNum integer, score integer, reviewTitle text, reviewContent text, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'review', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    //수호 탬플릿
    private static final String CREATE_TABLE_NONLINEMEMO = "CREATE TABLE if not exists nonlinememo(id integer primary key autoincrement, userdate text, content text, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'nonlinememo', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    private static final String CREATE_TABLE_TODOLIST = "CREATE TABLE if not exists todolist(id integer primary key autoincrement, userdate text, content text, done text, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'todolist', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    private static final String CREATE_TABLE_WISHLIST = "CREATE TABLE if not exists wishlist(id integer primary key autoincrement, userdate text, content text, wished text, category text, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'wishlist', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    private static final String CREATE_TABLE_SHOPPINGLIST = "CREATE TABLE if not exists shoppinglist(id integer primary key autoincrement, userdate text, content text, bought text, price text, " +
            "title text NOT NULL, editdate DATETIME DEFAULT (datetime('now', 'localtime')), deleted integer DEFAULT 0, fixed integer DEFAULT 0, bgcolor text DEFAULT 'yellow', template_case text DEFAULT 'shoppinglist', folder_name TEXT DEFAULT '메모', FOREIGN KEY(folder_name) REFERENCES folder(name));";

    // 생성자
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // DB가 처음 만들어 질 때 호출. 여기서 테이블 생성 및 초기 레코드 삽입
    @Override
    public void onCreate(SQLiteDatabase db) {
        /* 에뮬 테스트 할때는 이부분 없어도 되는듯?
        //재설치할때 테이블 있으면 삭제하고 다시 만듦
        //재석
        db.execSQL("DROP TABLE IF EXISTS linememo");
        db.execSQL("DROP TABLE IF EXISTS dailyplan");
        db.execSQL("DROP TABLE IF EXISTS weeklyplan");
        db.execSQL("DROP TABLE IF EXISTS monthlyplan");
        db.execSQL("DROP TABLE IF EXISTS yearlyplan");
        //윤경
        db.execSQL("DROP TABLE IF EXISTS gridmemo");
        db.execSQL("DROP TABLE IF EXISTS healthtracker");
        db.execSQL("DROP TABLE IF EXISTS monthtracker");
        db.execSQL("DROP TABLE IF EXISTS studytracker");
        db.execSQL("DROP TABLE IF EXISTS review");
        //수호
        db.execSQL("DROP TABLE if exists nonlinememo");
        db.execSQL("DROP TABLE if exists todolist");
        db.execSQL("DROP TABLE if exists wishlist");
        db.execSQL("DROP TABLE if exists shoppinglist");
        //참조되는 키 있어서 젤 마지막에 삭제
        db.execSQL("DROP TABLE IF EXISTS folder");
        */

        // 외래키 허용
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }

        //쿼리 실행 folder 먼저 생성해주기
        db.execSQL(CREATE_TABLE_FOLDER);
        db.execSQL("INSERT INTO folder('name') VALUES('메모')");
        //재석
        db.execSQL(CREATE_TABLE_LINEMEMO);
        db.execSQL(CREATE_TABLE_DAILYPLAN);
        db.execSQL(CREATE_TABLE_WEEKLYPLAN);
        db.execSQL(CREATE_TABLE_MONTHLYPLAN);
        db.execSQL(CREATE_TABLE_YEARLYPLAN);
        //윤경
        db.execSQL(CREATE_TABLE_GRIDMEMO);
        db.execSQL(CREATE_TABLE_HEALTHTRACKER);
        db.execSQL(CREATE_TABLE_MONTHTRACKER);
        db.execSQL(CREATE_TABLE_STUDYTRACKER);
        db.execSQL(CREATE_TABLE_REVIEW);
        //수호
        db.execSQL(CREATE_TABLE_NONLINEMEMO);
        db.execSQL(CREATE_TABLE_TODOLIST);
        db.execSQL(CREATE_TABLE_WISHLIST);
        db.execSQL(CREATE_TABLE_SHOPPINGLIST);
    }

    // DB를 열 때 호출한다.
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    // DB를 업그레이드 할 때 호출. (DB 버전이 바뀌었을 때)
    // 기존 테이블 삭제&새로 생성 하거나 ALTER TABLE 로 Schema 수정
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //재석
        db.execSQL("DROP TABLE if exists linememo");
        db.execSQL("DROP TABLE if exists dailyplan");
        db.execSQL("DROP TABLE if exists weeklyplan");
        db.execSQL("DROP TABLE if exists monthlyplan");
        db.execSQL("DROP TABLE if exists yearlyplan");
        //윤경
        db.execSQL("DROP TABLE if exists gridmemo");
        db.execSQL("DROP TABLE if exists healthtracker");
        db.execSQL("DROP TABLE if exists monthtracker");
        db.execSQL("DROP TABLE if exists studytracker");
        db.execSQL("DROP TABLE if exists review");
        //수호
        db.execSQL("DROP TABLE if exists nonlinememo");
        db.execSQL("DROP TABLE if exists todolist");
        db.execSQL("DROP TABLE if exists wishlist");
        db.execSQL("DROP TABLE if exists shoppinglist");
        //참조되는 키 있어서 젤 마지막에 삭제
        db.execSQL("DROP TABLE if exists folder");

        onCreate(db);
    }
}