package com.example.user.sangwa_test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private static final String Tag = "서비스";
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent == null){
            return Service.START_STICKY;
        }else{
            processinsert(intent);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void processinsert(Intent insert){


        String editid = insert.getStringExtra("editid");
        String editpw = insert.getStringExtra("editpw");
        String editTitle = insert.getStringExtra("editTitle");
        String editContent = insert.getStringExtra("editContent");
        //DB 연결

        Log.d(Tag,"id"+editid+"editpw"+editpw+"editTitle"+editTitle+"editContent"+editContent);

    }
}
