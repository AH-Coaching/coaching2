package ua.com.android_helper.taxiinfo.utils;

/**
 * Created by andreyholovko on 3/28/14.
 */
public interface IServerInfo {
    int getId();
    void setId(int id);
    void setVersion(int version);

    int getVersion();
    String getName();
    void setName(String name);
    int getCityId();
    void setCityID(int cityID);
    int getTaxiRate();
    void setTaxiRate(int taxiRate);
    void setValue(String key, String value);
    String getValueByKey(String key);
}
