package com.android_helper.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created with IntelliJ IDEA. User: andriistakhov Date: 18.05.13 Time: 07:51 To change this template use File |
 * Settings | File Templates.
 */
public class SQLiteContract {
    public static final String CONTENT_AUTHORITY = "com.android_helper.db.contentprovider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    interface ItemsColumns {
        String COLUMN_ITEM_TEXT = "item_text";
        String COLUMN_ITEM_PARENT_ID = "item_parent_id";
    }

    public static class Items implements ItemsColumns, BaseColumns {
        public static final String TB_ITEMS = "tb_items";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TB_ITEMS).build();
        public static final String CONTENT_TYPE = "vnd.ah.cursor.dir/vnd.ah.items";
        public static final String CONTENT_ITEM_TYPE = "vnd.ah.cursor.item/vnd.ah.items";

        public static Uri buildUri(String projectId) {
            return CONTENT_URI.buildUpon().appendPath(projectId).build();
        }

        public static Uri buildUri(Integer projectId) {
            return buildUri(projectId.toString());
        }

        public static String getId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }
}
