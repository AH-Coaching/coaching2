package ua.com.android_helper.taxiinfo.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by andreyholovko on 3/8/14.
 */
public class SQLiteDataProvider extends ContentProvider {
    //For uriMatcher
    private static final int INCOMING_CITY_COLLECTION_URI_INDICATOR=1;
    private static final int INCOMING_SINGLE_CITY_URI_INDICATOR=2;
    private static final int INCOMING_TAXINAME_COLLECTION_URI_INDICATOR=3;
    private static final int INCOMING_SINGLE_TAXINAME_URI_INDICATOR=4;
    private static final int INCOMING_DETAILS_COLLECTION_URI_INDICATOR=5;
    private static final int INCOMING_SINGLE_DETAILS_URI_INDICATOR=6;
    private static UriMatcher sUriMatcher;


    //database
    private SQLiteTaxiInfo mOpenHelper;

    public static synchronized UriMatcher getUriMatcher(){
        if (sUriMatcher==null){
            sUriMatcher = buildUriMatcher();
        }
        return sUriMatcher;
    }

    private static UriMatcher buildUriMatcher() {

        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(SQLiteContract.CONTENT_AUTHORITHY,SQLiteContract.City.TB_CITY,INCOMING_CITY_COLLECTION_URI_INDICATOR);
        sUriMatcher.addURI(SQLiteContract.CONTENT_AUTHORITHY,SQLiteContract.City.TB_CITY+"/*",INCOMING_SINGLE_CITY_URI_INDICATOR);
        sUriMatcher.addURI(SQLiteContract.CONTENT_AUTHORITHY,SQLiteContract.Taxiname.TB_TAXINAME,INCOMING_TAXINAME_COLLECTION_URI_INDICATOR);
        sUriMatcher.addURI(SQLiteContract.CONTENT_AUTHORITHY,SQLiteContract.Taxiname.TB_TAXINAME+"/*",INCOMING_SINGLE_TAXINAME_URI_INDICATOR);
        sUriMatcher.addURI(SQLiteContract.CONTENT_AUTHORITHY,SQLiteContract.Details.TB_DETAILS,INCOMING_DETAILS_COLLECTION_URI_INDICATOR);
        sUriMatcher.addURI(SQLiteContract.CONTENT_AUTHORITHY,SQLiteContract.Details.TB_DETAILS+"/*",INCOMING_SINGLE_DETAILS_URI_INDICATOR);


        return sUriMatcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new SQLiteTaxiInfo(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final SQLiteQueryBuilder builder = buildSelection(uri);
        return builder.query(db,projection,selection,selectionArgs,null,null,sortOrder);

    }

    private SQLiteQueryBuilder buildSelection(Uri uri) {
        final SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        final int match = getUriMatcher().match(uri);
        String cartID;
        switch (match){
            case INCOMING_CITY_COLLECTION_URI_INDICATOR:
                builder.setTables(SQLiteContract.City.TB_CITY);
                break;
            case INCOMING_SINGLE_CITY_URI_INDICATOR:
                cartID = SQLiteContract.City.getID(uri);
                builder.setTables(SQLiteContract.City.TB_CITY);
                builder.appendWhere(String.format(SQLiteContract.City._ID+"=%s",cartID));
                break;
             case INCOMING_TAXINAME_COLLECTION_URI_INDICATOR:
                builder.setTables(SQLiteContract.Taxiname.TB_TAXINAME);
                break;
            case INCOMING_SINGLE_TAXINAME_URI_INDICATOR:
                cartID = SQLiteContract.Taxiname.getID(uri);
                builder.setTables(SQLiteContract.Taxiname.TB_TAXINAME);
                builder.appendWhere(String.format(SQLiteContract.Taxiname._ID+"=%s",cartID));
                break;
             case INCOMING_DETAILS_COLLECTION_URI_INDICATOR:
                builder.setTables(SQLiteContract.Details.TB_DETAILS);
                break;
            case INCOMING_SINGLE_DETAILS_URI_INDICATOR:
                cartID = SQLiteContract.Details.getID(uri);
                builder.setTables(SQLiteContract.Details.TB_DETAILS);
                builder.appendWhere(String.format(SQLiteContract.Details._ID+"=%s",cartID));
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        return builder;
    }

    @Override
    public String getType(Uri uri) {
        final int match = getUriMatcher().match(uri);
        switch (match){
            case INCOMING_CITY_COLLECTION_URI_INDICATOR:
                return SQLiteContract.City.CONTENT_TYPE;
            case INCOMING_SINGLE_CITY_URI_INDICATOR:
                return SQLiteContract.City.CONTENT_ITEM_TYPE;
            case INCOMING_TAXINAME_COLLECTION_URI_INDICATOR:
                return SQLiteContract.Taxiname.CONTENT_TYPE;
            case INCOMING_SINGLE_TAXINAME_URI_INDICATOR:
                return SQLiteContract.Taxiname.CONTENT_ITEM_TYPE;
            case INCOMING_DETAILS_COLLECTION_URI_INDICATOR:
                return SQLiteContract.Details.CONTENT_TYPE;
            case INCOMING_SINGLE_DETAILS_URI_INDICATOR:
                return SQLiteContract.Details.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = getUriMatcher().match(uri);
        if (match == INCOMING_CITY_COLLECTION_URI_INDICATOR){
            long TextsId = db.insertWithOnConflict(SQLiteContract.City.TB_CITY,null,values,SQLiteDatabase.CONFLICT_IGNORE);
            getContext().getContentResolver().notifyChange(uri,null);
            return SQLiteContract.City.buildUri(String.valueOf(TextsId));
        }else
        if (match == INCOMING_TAXINAME_COLLECTION_URI_INDICATOR){
            long TextsId = db.insertWithOnConflict(SQLiteContract.Taxiname.TB_TAXINAME,null,values,SQLiteDatabase.CONFLICT_IGNORE);
            getContext().getContentResolver().notifyChange(uri,null);
            return SQLiteContract.City.buildUri(String.valueOf(TextsId));
        }else
        if (match == INCOMING_DETAILS_COLLECTION_URI_INDICATOR){
            long TextsId = db.insertWithOnConflict(SQLiteContract.Details.TB_DETAILS,null,values,SQLiteDatabase.CONFLICT_IGNORE);
            getContext().getContentResolver().notifyChange(uri,null);
            return SQLiteContract.City.buildUri(String.valueOf(TextsId));
        }
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SQLiteQueryBuilder builder = buildSelection(uri);
        int delete = db.delete(builder.getTables(), selection, selectionArgs);
        return delete;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SQLiteQueryBuilder builder = buildSelection(uri);
        int result = db.update(builder.getTables(),values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri,null);
        return result;
    }
}
