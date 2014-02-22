package com.android_helper.utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by andriistakhov on 24.11.13.
 */
public class ServiceTest extends Service {
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        return super.onStartCommand(intent, flags, startId);
    }
}
