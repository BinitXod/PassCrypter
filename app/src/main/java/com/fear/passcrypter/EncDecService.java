package com.fear.passcrypter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class EncDecService extends Service {

    private static final String TAG = "com.fear.passcrypter";

    public EncDecService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStartCommand called");

        Runnable r = new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<5; i++){
                    synchronized (this){
                        try {
                            wait(5000);
                            Log.i(TAG,"Service in play...");
                        } catch (Exception e) {}
                    }
                }
            }
        };

        Thread fearsThread = new Thread(r);
        fearsThread.start();
        return Service.START_STICKY;

    }

    @Override
    public void onDestroy() {
        Log.i(TAG,"onDestroy called");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
