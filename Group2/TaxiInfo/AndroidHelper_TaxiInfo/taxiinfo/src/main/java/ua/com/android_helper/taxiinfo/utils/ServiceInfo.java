package ua.com.android_helper.taxiinfo.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andreyholovko on 3/28/14.
 */
public class ServiceInfo implements IServerInfo {
    //city
    private int mId;
    private int mVersion;
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

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }
}
