package ua.com.android_helper.taxiinfo.utils;

/**
 * Created by andreyholovko on 3/28/14.
 */
public interface IServerInfo {
    int getId();
    void setId(int id);
    void setVersion(int version);
    int getVersion();
    void setValue(String key, String value);
    String getValueByKey(String key);
}
