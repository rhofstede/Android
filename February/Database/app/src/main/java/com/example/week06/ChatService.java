package com.example.week06;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class ChatService extends Service
{
    static final String TAG = "ChatService";
    static final int DELAY = 10000;
    private boolean bRun = false;
    private ChatThread theThread = null;


    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        theThread = new ChatThread("ChatServiceThread");
        Log.d(TAG,"onCreate() instantiate a new Thread");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        bRun = true;
        theThread.start();
        Log.d(TAG,"service started");
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        bRun = false;
        theThread.interrupt();
        theThread = null;
        Log.d(TAG,"onDestroy() - stopping the Thread");

        super.onDestroy();
    }

    class ChatThread extends Thread
    {
        public ChatThread(String name)
        {
            super(name);
        }

        @Override
        public void run()
        {
            while(bRun == true)
            {
                try
                {
                    Log.d(TAG,"reader executed one cycle");
                    Thread.sleep(DELAY);
                }
                catch(InterruptedException e)
                {
                    bRun = false;
                }
            }
        }
    }
}
