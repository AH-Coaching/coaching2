package ua.com.androidhelper.ui.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by Androidsmith on 21.02.14.
 */
public class SQLiteDataProvider extends ContentProvider {

    // / Used for the UriMacher
    private static final int ITEMS = 10;
    private static final int ITEMS_ID = 20;
    private static final int ITEMS_D = 30;
    private static final int ITEMS_D_ID = 40;
    private static UriMatcher sUriMatcher;


    //database
    private SQLiteHelperNew mOpenHelper;

    public static synchronized UriMatcher getUriMatcher() {
        if (sUriMatcher == null) {
            sUriMatcher = buildUriMatcher();
        }
        return sUriMatcher;
    }

    private static UriMatcher buildUriMatcher() {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(SQLiteContract.CONTENT_AUTHORITHY, SQLiteContract.Items.TB_ITEMS, ITEMS);
        sUriMatcher.addURI(SQLiteContract.CONTENT_AUTHORITHY, SQLiteContract.Items.TB_ITEMS + "/*", ITEMS_ID);
        return sUriMatcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new SQLiteHelperNew(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        final SQLiteQueryBuilder builder = buildSelection(uri);
        return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

    }

    private SQLiteQueryBuilder buildSelection(Uri uri) {
        final SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        final int match = getUriMatcher().match(uri);
        switch (match) {
            case ITEMS:
                builder.setTables(SQLiteContract.Items.TB_ITEMS);
                break;
            case ITEMS_ID:
                String cartId = SQLiteContract.Items.getId(uri);
                builder.setTables(SQLiteContract.Items.TB_ITEMS);
                builder.appendWhere(String.format(SQLiteContract.Items._ID + "='%s'", cartId));
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return builder;
    }

    @Override
    public String getType(Uri uri) {
        final int match = getUriMatcher().match(uri);
        switch (match) {
            case ITEMS:
                return SQLiteContract.Items.CONTENT_TYPE;
            case ITEMS_ID:
                return SQLiteContract.Items.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = getUriMatcher().match(uri);
        if (match == ITEMS) {
            long TextsId = db.insertWithOnConflict(SQLiteContract.Items.TB_ITEMS, null, values, SQLiteDatabase.CONFLICT_IGNORE);
            getContext().getContentResolver().notifyChange(uri, null);
            return SQLiteContract.Items.buildUri(String.valueOf(TextsId));
        }

        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SQLiteQueryBuilder builder = buildSelection(uri);
        int result = db.update(builder.getTables(), values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        final SQLiteQueryBuilder builder = buildSelection(uri);
        int delete = db.delete(builder.getTables(), selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return delete;
    }
}
