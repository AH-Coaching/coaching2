package ua.com.androidhelper.ui.utils;

/**
 * Created by andreyholovko on 3/1/14.
 */
public interface IServerInfo {

    int getId();

    void setId(int id);

    void setValue(String key, String value);

    String getValueByKey(String key);

}