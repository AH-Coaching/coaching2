package com.android_helper.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA. User: andriistakhov Date: 18.05.13 Time: 06:28 To change this template use File |
 * Settings | File Templates.
 */
public class SQLiteHelperNew extends SQLiteOpenHelper {
    // Base
    private static final String DATABASE_NAME = "android.helper.db";
    private static final int DATABASE_VERSION = 1;
    // Tables query
    private static final String CREATE_TABLE_CART = "CREATE TABLE if not exists " + SQLiteContract.Items.TB_ITEMS
            + " (" + SQLiteContract.Items._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + SQLiteContract.Items.COLUMN_ITEM_TEXT + " TEXT NOT NULL, "
            + SQLiteContract.Items.COLUMN_ITEM_PARENT_ID + " INT DEFAULT 0)";
    private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";

    public SQLiteHelperNew(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_IF_EXISTS + SQLiteContract.Items.TB_ITEMS);
        onCreate(db);
    }
}
