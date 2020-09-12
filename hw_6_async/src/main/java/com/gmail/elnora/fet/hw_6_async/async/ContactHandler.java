package com.gmail.elnora.fet.hw_6_async.async;

import android.os.Handler;
import android.os.HandlerThread;

public class ContactHandler extends HandlerThread {

    private Handler handler;

    public ContactHandler() {
        super("contactHandlerThread");
    }

    public void post(Runnable runnable) {
        handler.post(runnable);
    }

    @Override
    protected void onLooperPrepared() {
        handler = new Handler(getLooper());
    }
}
