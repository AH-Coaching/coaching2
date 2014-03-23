package com.android_helper;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.android_helper.db.SQLiteContract;
import com.android_helper.utils.Constants;
import com.android_helper.utils.ServiceInfo;
import com.android_helper.utils.TaxiService;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: andriistakhov
 * Date: 13.07.13
 * Time: 21:47
 * To change this template use File | Settings | File Templates.
 */
public class AHService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     * <p/>
     * Used to name the worker thread, important only for debugging.
     */
    public AHService() {
        super("AHService");
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String url = Constants.GET_CITY_URL;

        Map<String, String> params = new HashMap<String, String>();
        String[] paramsKeys = Constants.GET_SERVICE_PARAMS;
        String jsonResult = getInfoFromServer(url, params);
        TaxiService taxiService = parseJson(jsonResult);

        // Fill data in DB
        populateCity(taxiService.getServiceInfoMap());

        Intent intBrodcast = new Intent("com.ua.android_helper.androidhelper.dataAdded");
        sendBroadcast(intBrodcast);
    }

    private void populateCity(List<ServiceInfo> list) {

        for (ServiceInfo item : list) {
            ContentValues values = new ContentValues();
            values.put(SQLiteContract.Items.COLUMN_ITEM_TEXT, item.getName());

            getContentResolver().insert(SQLiteContract.Items.CONTENT_URI, values);
        }

    }

    private String getInfoFromServer(String url, Map<String, String> params) {
        String url_select = url;

        HttpParams param = new BasicHttpParams();

        for (Map.Entry<String, String> e : params.entrySet()) {
            param.setParameter(e.getKey(), e.getValue());
        }

        InputStream inputStream = null;
        try {
            // Set up HTTP post

            // HttpClient is more then less deprecated. Need to change to URLConnection
            HttpClient httpClient = new DefaultHttpClient();


            HttpGet httpPost = new HttpGet(url_select);
            httpPost.setParams(param);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            // Read content & Log
            inputStream = httpEntity.getContent();
        } catch (UnsupportedEncodingException e1) {
            Log.e("UnsupportedEncodingException", e1.toString());
            e1.printStackTrace();
        } catch (ClientProtocolException e2) {
            Log.e("ClientProtocolException", e2.toString());
            e2.printStackTrace();
        } catch (IllegalStateException e3) {
            Log.e("IllegalStateException", e3.toString());
            e3.printStackTrace();
        } catch (IOException e4) {
            Log.e("IOException", e4.toString());
            e4.printStackTrace();
        }
        // Convert response to string using String Builder
        String result = null;
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder sBuilder = new StringBuilder();

            String line = null;
            while ((line = bReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }

            inputStream.close();
            result = sBuilder.toString();

        } catch (Exception e) {
            Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
        }

        return result;
    }

    private TaxiService parseJson(String jsonString) {
        {
            TaxiService taxiService = new TaxiService();
            try {
                JSONArray jArray = new JSONArray(jsonString);


                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);


                    String name = jObject.getString("name");
                    int id = jObject.getInt("id");
                    int version = jObject.getInt("version");

                    ServiceInfo serviceInfo = new ServiceInfo();
                    serviceInfo.setId(id);
                    serviceInfo.setValue(String.valueOf(id), name);
                    serviceInfo.setName(name);
                    taxiService.addServiceInfoMap(serviceInfo);

                } // End Loop


            } catch (JSONException e) {

                Log.e("JSONException", "Error: " + e.toString());

            } // catch (JSONException e)

            return taxiService;
        }
    }
}
