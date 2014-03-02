package ua.com.androidhelper.ui.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Androidsmith on 20.02.14.
 */
public class SQLiteHelperNew extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "android.helper.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_CART = "CREATE TABLE if not exists "+SQLiteContract.Items.TB_ITEMS
            +" ("+SQLiteContract.Items._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
    +SQLiteContract.Items.COLUMN_ITEM_TEXT+" TEXT NOT NULL, "
    +SQLiteContract.Items.COLUMN_ITEM_PARENT_ID+" INT DEFAULT 0)";
    private static final String DROP_TABLE_IF_EXITS = "DROP TABLE IF EXITS";

    public SQLiteHelperNew(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_TABLE_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
    db.execSQL(DROP_TABLE_IF_EXITS + SQLiteContract.Items.TB_ITEMS);
        onCreate(db);
    }
}
