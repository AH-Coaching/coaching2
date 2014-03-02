package ua.com.androidhelper.ui.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Androidsmith on 21.02.14.
 */
public class SQLiteContract {
    public static final String CONTENT_AUTHORITHY = "com.ua.androidhelper.ui.db.contentprovider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITHY);

    interface ItemsColumns {
        String COLUMN_ITEM_TEXT = "item_text";
        String COLUMN_ITEM_PARENT_ID = "item_parent_id";
    }

    ;

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
