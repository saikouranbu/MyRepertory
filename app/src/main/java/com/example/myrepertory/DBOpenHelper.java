package com.example.myrepertory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "karaoke2";
    private static final int DB_VERSION = 1;
    private int m_writableDatabaseCount = 0;

    private static DBOpenHelper m_instance = null;


    synchronized static
    public DBOpenHelper getInstance(Context context) {
        if (m_instance == null) {
            m_instance = new DBOpenHelper(context.getApplicationContext());
        }

        return m_instance;
    }

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    synchronized public SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase db = super.getWritableDatabase();
        if (db != null) {
            ++m_writableDatabaseCount;
        }

        return db;
    }

    synchronized public void closeWritableDatabase(SQLiteDatabase database) {
        if (m_writableDatabaseCount > 0 && database != null) {
            --m_writableDatabaseCount;
            if (m_writableDatabaseCount == 0) {
                database.close();
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS repertory (" +
                        " id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL," +
                        " name TEXT NOT NULL, artist TEXT NOT NULL, genre TEXT," +
                        " data TEXT, dam TEXT, joy TEXT, singable REAL DEFAULT 0," +
                        " priority REAL DEFAULT 0 )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }


}
