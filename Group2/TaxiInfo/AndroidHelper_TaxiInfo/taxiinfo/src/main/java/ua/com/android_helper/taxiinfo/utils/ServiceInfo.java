package ua.com.android_helper.taxiinfo.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andreyholovko on 3/28/14.
 */
public class ServiceInfo implements IServerInfo {
    //city
    private int mId;
    private int cId; //city_id
    private int rate;
    private int mVersion;
    private ArrayList<HashMap<String, String>> details;
    private String _name;
    //taxiservice
    private Map<String, String> map;
    //details

    @Override
    public int getId() {
        return mId;
    }

    @Override
    public void setId(int id) {
        mId=id;
    }

    @Override
    public void setVersion(int version) {
        mVersion=version;
    }

    @Override
    public int getVersion() {
        return mVersion;
    }

    @Override
    public void setValue(String key, String value) {
        if(map==null){
            map = new HashMap<String, String>();
        }
        map.put(key,value);
    }

    @Override
    public String getValueByKey(String key) {
        return map.get(key);
    }
    @Override
    public String getName() {
        return _name;
    }

    @Override
    public void setName(String name) {
        this._name = name;
    }

    @Override
    public void setCityID(int cityID) {
     this.cId=cityID;
    }

    @Override
    public int getCityId() {
        return cId;
    }

    @Override
    public int getTaxiRate() {
        return rate;
    }

    @Override
    public void setTaxiRate(int taxiRate) {
    this.rate=taxiRate;
    }


    public void setDetails (HashMap<String,String> det) {
        if(details==null){
            details = new ArrayList<HashMap<String, String>>();
        }
        this.details.add(det);
    }

    public ArrayList<HashMap<String, String>> getDetails(){
        return details;
    }
}
