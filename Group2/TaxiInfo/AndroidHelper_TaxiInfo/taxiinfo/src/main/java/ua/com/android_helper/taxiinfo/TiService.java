package ua.com.android_helper.taxiinfo;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.os.Parcel;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.com.android_helper.taxiinfo.db.SQLiteContract;
import ua.com.android_helper.taxiinfo.utils.Constants;
import ua.com.android_helper.taxiinfo.utils.ServiceInfo;
import ua.com.android_helper.taxiinfo.utils.TaxiService;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class TiService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "ua.com.android_helper.taxiinfo.action.FOO";
    private static final String ACTION_BAZ = "ua.com.android_helper.taxiinfo.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "ua.com.android_helper.taxiinfo.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "ua.com.android_helper.taxiinfo.extra.PARAM2";

    public TiService() {
        super("TiService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, TiService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, TiService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String url = Constants.GET_CITY_URL;
        Map<String, String> params = new HashMap<String, String>();
        // String[] paramKeys = Constants.GET_SERVICE_PARAMS;
        String jsonResult = getInfoFromServer(url, params);
        TaxiService taxiService = parseJson(jsonResult, url);

        populateCity(taxiService.getServiceInfoMap());
        url = Constants.GET_SERVICE_URL;
        jsonResult = getInfoFromServer(url, params);
        taxiService = parseJson(jsonResult, url);
        populateServices(taxiService.getServiceInfoMap());

        Intent intBroadcast = new Intent("ua.com.android_helper.taxiinfo.cityadd");
        sendBroadcast(intBroadcast);

    }

    private void populateCity(List<ServiceInfo> list) {

        for (ServiceInfo item : list) {
            ContentValues values = new ContentValues();
            Log.d("TAG", "id =" + item.getId());
            Log.d("TAG", "name =" + item.getName());
            Log.d("TAG", "version =" + item.getVersion());


            values.put(SQLiteContract.City.COLUMN_CITY_NAME, item.getName());
            values.put(SQLiteContract.City._ID, item.getId());
            values.put(SQLiteContract.City.COLUMN_CITY_ID, item.getId());
            values.put(SQLiteContract.City.COLUMN_CITY_VERSION, item.getVersion());
            getContentResolver().insert(SQLiteContract.City.CONTENT_URI, values);
        }

    }

    private void populateServices(List<ServiceInfo> list) {

        for (ServiceInfo item : list) {
            ContentValues values = new ContentValues();

            values.put(SQLiteContract.Taxiname.COLUMN_TAXI_NAME, item.getName());
            values.put(SQLiteContract.Taxiname._ID, item.getId());
            values.put(SQLiteContract.Taxiname.COLUMN_TAXI_RATE, item.getTaxiRate());
            values.put(SQLiteContract.Taxiname.COLUMN_CITY_ID, item.getCityId());

            getContentResolver().insert(SQLiteContract.Taxiname.CONTENT_URI, values);


            ArrayList<HashMap<String, String>> details;
            details = item.getDetails();
            if (details != null) {
                for (HashMap<String, String> det : details) {

                    Parcel parcel = Parcel.obtain();

                    parcel.writeMap(det);
                    parcel.setDataPosition(0);
                    values = ContentValues.CREATOR.createFromParcel(parcel);

                    getContentResolver().insert(SQLiteContract.Details.CONTENT_URI, values);
                }
            }


        }

    }

    private TaxiService parseJson(String jsonResult, String type_url) {

        TaxiService taxiService = new TaxiService();

        if (type_url == Constants.GET_CITY_URL) {


            try {
                JSONArray jArray = new JSONArray(jsonResult);


                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonObject = jArray.getJSONObject(i);

                    String name = jsonObject.getString("name");
                    int id = jsonObject.getInt("id");
                    int version = jsonObject.getInt("version");

                    ServiceInfo serviceInfo = new ServiceInfo();
                    serviceInfo.setId(id);
                    serviceInfo.setVersion(version);
                    serviceInfo.setName(name);
                    serviceInfo.setValue(String.valueOf(id), name);
                    taxiService.addServiceInfoMap(serviceInfo);
                }
            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            }
            return taxiService;
        } else if (type_url == Constants.GET_SERVICE_URL) {

            try {
                JSONArray jArray = new JSONArray(jsonResult);


                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonObject = jArray.getJSONObject(i);

                    int id = jsonObject.getInt("id");
                    String name = jsonObject.getString("name");
                    int rate = jsonObject.getInt("rate");
                    int city_id = jsonObject.getInt("city_id");

                    ServiceInfo serviceInfo = new ServiceInfo();
                    serviceInfo.setId(id);
                    serviceInfo.setName(name);
                    serviceInfo.setTaxiRate(rate);
                    serviceInfo.setCityID(city_id);


                    JSONObject jsonInfos = jsonObject.getJSONObject("infos");


                    for (int j = 0; j < jsonInfos.length(); j++) {
                        String infos_id = jsonInfos.getJSONObject(String.valueOf(j)).getString("id");
                        String infos_numbervalue = jsonInfos.getJSONObject(String.valueOf(j)).getString("number_value");
                        String infos_service_id = jsonInfos.getJSONObject(String.valueOf(j)).getString("service_id");
                        String infos_type = jsonInfos.getJSONObject(String.valueOf(j)).getString("type");

                        //tmp hashmap

                        HashMap<String, String> tmp_details = new HashMap<String, String>();
                        tmp_details.put(SQLiteContract.Details._ID, infos_id);
                        tmp_details.put(SQLiteContract.Details.COLUMN_DETAILS_NUMBER_VALUE, infos_numbervalue);
                        tmp_details.put(SQLiteContract.Details.COLUMN_DETAILS_SERVICE_ID, infos_service_id);
                        tmp_details.put(SQLiteContract.Details.COLUMN_DETAILS_TYPE, infos_type);
                        serviceInfo.setDetails(tmp_details);
                    }


                    taxiService.addServiceInfoMap(serviceInfo);
                }
            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            }

        }
        return taxiService;
    }


    private String getInfoFromServer(String url, Map<String, String> params) {
        String url_select = url;
        HttpParams param = new BasicHttpParams();

        for (Map.Entry<String, String> e : params.entrySet()) {
            param.setParameter(e.getKey(), e.getValue());
        }

        InputStream inputStream = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url_select);
            httpGet.setParams(param);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();
        } catch (ClientProtocolException e) {

            Toast.makeText(this,"Проблемы со связью с сервисом "+e.toString(),Toast.LENGTH_SHORT).show();
          //  e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this,"Проблем "+e.toString(),Toast.LENGTH_SHORT).show();
          //  e.printStackTrace();
        }

        //Convert response

        String result = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            inputStream.close();
            result = stringBuilder.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
