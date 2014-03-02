package ua.com.androidhelper.ui.utils;

/**
 * Created by andreyholovko on 3/1/14.
 */
import java.util.HashMap;
import java.util.Map;


public class ServiceInfo implements IServerInfo {
    private int mId;
    private Map<String, String> map;

    @Override
    public int getId() {
        return mId;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setId(int id) {
        mId = id;
    }

    @Override
    public void setValue(String key, String value) {
        if (map == null) {
            map = new HashMap<String, String>();
        }
        map.put(key, value);
    }

    @Override
    public String getValueByKey(String key) {
        return map.get(key);
    }
}