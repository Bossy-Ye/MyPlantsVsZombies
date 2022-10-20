package com.example.myplantsvszombies;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class BindService extends Service {
    private int count;
    private boolean quit;
    private MyBinder binder;
    public class MyBinder extends Binder{
        public int getCount()
        {
            return count;
        }
    }
    public BindService() {
        binder = new MyBinder();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        System.out.println("Service is Binded");
        return binder;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        System.out.println("Service is Created");

        new Thread() {
            @Override
            public void run() {
                while (!quit) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                }
            }
        }.start();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        this.quit=true;
        System.out.println("Service is Destroyed");
    }
}