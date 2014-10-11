package org.xhan.subtitleplayer.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;

/**
 * Created by xhan on 10/11/14.
 */
public class SubPlayerServiceConnection implements ServiceConnection {
    Messenger serviceMessenger;
    IServiceConnectionHandler handler;

    public SubPlayerServiceConnection (IServiceConnectionHandler handler) {
        this.handler = handler;
    }
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        serviceMessenger = new Messenger(service);
        if (handler != null) {
            handler.onConnected(this);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    public Messenger getServiceMessenger() {
        return serviceMessenger;
    }
}
