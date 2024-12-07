package com.example.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todo.db";
    private static final String DATABASE_NAME1 = "new.db";
    private static final int DATABASE_VERSION = 3;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public DBHelper(Context context,SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME1, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE finished_tasks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "task_id TEXT, " +
                "task_name TEXT, " +
                "user_id TEXT, " +
                "completed_at INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS finished_task");
        onCreate(db);
    }
}
