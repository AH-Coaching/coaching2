package com.android_helper.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: andriistakhov
 * Date: 19.07.13
 * Time: 15:03
 * To change this template use File | Settings | File Templates.
 */
public class ServiceInfo implements IServerInfo {
    private int mId;
    private Map<String, String> map;
    private String _name;

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

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }
}
