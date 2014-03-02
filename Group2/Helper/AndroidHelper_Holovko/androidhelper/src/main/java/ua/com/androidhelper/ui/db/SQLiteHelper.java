package ua.com.androidhelper.ui.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by Androidsmith on 20.02.14.
 *
 * Load DB from assets
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "/mnt/sdcard/";
    private static String DB_NAME = "ah.sqlite";
    private SQLiteDatabase myDataBase;
    private final Context myContext;
    public static String TABLE_TEXTS = "tb_texts";

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, 6);
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    createDataBase();
    }

    public void createDataBase() {

        try {
            if (!checkDataBase()) {
                copyDataBase();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }


    public SQLiteDatabase getReadableDatabase() {
        if (myDataBase == null) {
            createDataBase();
            try {
                openDataBase();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return myDataBase;
    }

    public SQLiteDatabase getWritableDatabase() {
        if (myDataBase == null) {
            try {
                openDataBase();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return myDataBase;
    }


    @Override
    public synchronized void close() {

        if (myDataBase != null) {
            myDataBase.close();
        }

        super.close();

    }


    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {


        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    public void copyDataBase() throws IOException {

        InputStream myInput = myContext.getAssets().open(DB_NAME);

        String outFileName = DB_PATH + DB_NAME;

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
