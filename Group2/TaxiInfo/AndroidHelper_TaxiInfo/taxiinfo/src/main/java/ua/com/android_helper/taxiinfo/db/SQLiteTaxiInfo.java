package ua.com.android_helper.taxiinfo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by andreyholovko on 3/8/14.

 */
public class SQLiteTaxiInfo extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "taxiinfo.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_CITY= "CREATE TABLE if not exists "+SQLiteContract.City.TB_CITY
            +" ("+SQLiteContract.City._ID+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            +SQLiteContract.City.COLUMN_CITY_ID+" TEXT NOT NULL, "
            +SQLiteContract.City.COLUMN_CITY_NAME+" TEXT, "
            +SQLiteContract.City.COLUMN_CITY_IMAGE+" TEXT, "
            +SQLiteContract.City.COLUMN_CITY_VERSION+" INTEGER NOT NULL)";

    private static final String CREATE_TABLE_TAXINAME= "CREATE TABLE if not exists "+SQLiteContract.Taxiname.TB_TAXINAME+
            " ("+SQLiteContract.Taxiname._ID+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            +SQLiteContract.Taxiname.COLUMN_CITY_ID+" INTEGER NOT NULL, "
            +SQLiteContract.Taxiname.COLUMN_TAXI_NAME+" TEXT, "
            +SQLiteContract.Taxiname.COLUMN_TAXI_INFO+" TEXT, "
            +SQLiteContract.Taxiname.COLUMN_TAXI_RATE+" INTEGER NOT NULL)";

    private static final String CREATE_TABLE_DETAILS= "CREATE TABLE  if not exists "+SQLiteContract.Details.TB_DETAILS+
            " ("+SQLiteContract.Details._ID+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            +SQLiteContract.Details.COLUMN_DETAILS_SERVICE_ID+" INTEGER NOT NULL, "
            +SQLiteContract.Details.COLUMN_DETAILS_NUMBER_VALUE+" TEXT, "
            +SQLiteContract.Details.COLUMN_DETAILS_TYPE+" TEXT)";

    private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";

    public SQLiteTaxiInfo(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_CITY);
        sqLiteDatabase.execSQL(CREATE_TABLE_TAXINAME);
        sqLiteDatabase.execSQL(CREATE_TABLE_DETAILS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_IF_EXISTS + SQLiteContract.City.TB_CITY);
        db.execSQL(DROP_TABLE_IF_EXISTS + SQLiteContract.Taxiname.TB_TAXINAME);
        db.execSQL(DROP_TABLE_IF_EXISTS + SQLiteContract.Details.TB_DETAILS);
        onCreate(db);
    }
}
