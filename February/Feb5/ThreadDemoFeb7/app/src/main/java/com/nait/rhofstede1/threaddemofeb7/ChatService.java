package com.nait.rhofstede1.threaddemofeb7;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ChatService extends Service
{
    public static final String TAG = "ChatService";
    //tells thread how long it can run - 10 seconds here
    static final int DELAY = 10000;
    public static boolean bRun = false;
    public ChatThread theThread;

    public ChatService()
    {
        Log.d(TAG, "chat constructor");

    }

    @Override
    public void onCreate()
    {
        theThread = new ChatThread("");
        Log.d(TAG,"on create");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        bRun = true;
        theThread.start();
        Log.d(TAG, "on start command");
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        bRun = false;
        theThread.interrupt();
        theThread = null;
        Log.d(TAG, "on Destroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.d(TAG, "on bind method");
        return null;
    }
    private class ChatThread extends Thread
    {
        public ChatThread(String name)
        {
            super(name);
            Log.d(TAG, "chat thread constructor");
        }

        public void run()
        {
            while(bRun == true)
            {
                try
                {
                    //perform task
                    Log.d(TAG,"thread executed once");
                    Thread.sleep(DELAY);
                }
                catch (InterruptedException e)
                {
                    bRun = false;
                    Log.d(TAG,"interrupted exception");
                }
            }
        }
    }
}
