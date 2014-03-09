package ua.com.android_helper.taxiinfo.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by andreyholovko on 3/8/14.
 */
public class SQLiteContract {
    public static final String CONTENT_AUTHORITHY = "ua.com.android_helper.taxiinfo.db.contentprovider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITHY);

    interface CityColumns {
        //For table "tb_city"
        String COLUMN_CITY_ID = "city_id";
        String COLUMN_CITY_NAME = "city_name";
        String COLUMN_CITY_IMAGE = "city_image";
        String COLUMN_CITY_VERSION = "city_version";
    }

    public static class City implements CityColumns, BaseColumns {
        public static final String TB_CITY = "tb_city";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TB_CITY).build();
        public static final String CONTENT_TYPE = "vnd.ah.cursor.dir/vnd.ah.items";
        public static final String CONTENT_ITEM_TYPE = "vnd.ah.cursor.item/vnd.ah.items";

        public static Uri buildUri(String projectid) {
            return CONTENT_URI.buildUpon().appendPath(projectid).build();
        }

        public static Uri buildUri(Integer projectid) {
            return buildUri(projectid.toString());
        }

        public static String getID(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    interface TaxiNameColumns {
        //For table "tb_taxiname"
        // have String COLUMN_CITY_ID = "city_id"; - connection with tb_city
        String COLUMN_CITY_ID = "city_id";
        String COLUMN_TAXI_NAME = "taxi_name";
        String COLUMN_TAXI_INFO = "taxi_info";
        String COLUMN_TAXI_RATE = "taxi_rate";

    }

    public static class Taxiname implements TaxiNameColumns, BaseColumns {
        public static final String TB_TAXINAME = "tb_taxiname";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TB_TAXINAME).build();
        public static final String CONTENT_TYPE = "vnd.ah.cursor.dir/vnd.ah.items";
        public static final String CONTENT_ITEM_TYPE = "vnd.ah.cursor.item/vnd.ah.items";

        public static Uri buildUri(String projectid) {
            return CONTENT_URI.buildUpon().appendPath(projectid).build();
        }

        public static Uri buildUri(Integer projectid) {
            return buildUri(projectid.toString());
        }

        public static String getID(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    interface DetailsColumns {
        //For table "tb_details"
        String COLUMN_DETAILS_SERVICE_ID = "service_id"; //- connection with tb_taxiname column "_id"
        String COLUMN_DETAILS_NUMBER_VALUE = "number_value";
        String COLUMN_DETAILS_TYPE = "type";

    }

    public static class Details implements DetailsColumns, BaseColumns {
        public static final String TB_DETAILS = "tb_details";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TB_DETAILS).build();
        public static final String CONTENT_TYPE = "vnd.ah.cursor.dir/vnd.ah.items";
        public static final String CONTENT_ITEM_TYPE = "vnd.ah.cursor.item/vnd.ah.items";

        public static Uri buildUri(String projectid) {
            return CONTENT_URI.buildUpon().appendPath(projectid).build();
        }

        public static Uri buildUri(Integer projectid) {
            return buildUri(projectid.toString());
        }

        public static String getID(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

}
