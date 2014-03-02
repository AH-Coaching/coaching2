package ua.com.androidhelper.ui;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

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

import ua.com.androidhelper.ui.db.SQLiteContract;
import ua.com.androidhelper.ui.utils.Constants;
import ua.com.androidhelper.ui.utils.ServiceInfo;
import ua.com.androidhelper.ui.utils.TaxiService;

/**
 * Created by andreyholovko on 3/1/14.
 */
public class AHService extends IntentService {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    public AHService(String name) {
//        super(name);
//    }

    public AHService() {
        super("Start service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String url = Constants.GET_CITY_URL;
        Map<String,String> params = new HashMap<String, String>();
        String[] paramkeys  = Constants.GET_SERVICE_PARAMS;
        String jsonResult = getInfoFromServer(url,params);
        TaxiService taxiService = parseJson(jsonResult);

        // Fill data in DB
          populateCity(taxiService.getServiceInfoMap());

          Intent intBroadcast = new Intent ("ua.com.androidhelper.ui.dataAdded");
          sendBroadcast(intBroadcast);
        }

    private void populateCity(List<ServiceInfo> map) {
        ContentResolver contentResolver = getContentResolver();
        ContentValues cv = new ContentValues();

        for (int i=0;i<map.size();i++){
         cv.put(SQLiteContract.Items.COLUMN_ITEM_TEXT,String.valueOf(map.get(i).getValueByKey(""+(i+1))));
         contentResolver.insert(SQLiteContract.Items.CONTENT_URI, cv);
         //Log.d("TAG", String.valueOf(map.get(i).getValueByKey(""+(i+1))));
        }


        // Fill data in DB
    }

    private String getInfoFromServer(String url, Map<String,String> params) {
        String url_select = url;
        HttpParams param = new BasicHttpParams();

        for (Map.Entry<String, String> e: params.entrySet()){
            param.setParameter(e.getKey(),e.getValue());

        }
        InputStream inputStream = null;

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpPost = new HttpGet(url_select);
            httpPost.setParams(param);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            //Read content
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

        //Convert response to String

        String result = null;
        try{
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"),8);
            StringBuilder sBuilder = new StringBuilder();

            String line = null;
            while ((line = bReader.readLine())!=null){
                sBuilder.append(line+"/n");
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
                    taxiService.addServiceInfoMap(serviceInfo);

                } // End Loop


            } catch (JSONException e) {

                Log.e("JSONException", "Error: " + e.toString());

            } // catch (JSONException e)

            return taxiService;
        }
    }
}

