package ua.com.android_helper.taxiinfo.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreyholovko on 3/28/14.
 */
public class TaxiService extends ServiceInfo {
    private List<ServiceInfo> serviceInfoMap;

    public List <ServiceInfo> getServiceInfoMap(){
        return serviceInfoMap;
    }

    public void addServiceInfoMap(ServiceInfo serviceInfo){
        if(serviceInfoMap==null){
            serviceInfoMap = new ArrayList<ServiceInfo>();
        }
        serviceInfoMap.add(serviceInfo);
    }
}
