package org.xhan.subtitleplayer.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

/**
 * Created by xhan on 10/11/14.
 */
public abstract class SubPlayerServiceConnection implements ServiceConnection {
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

    public void register() {
        sendMessage(getRegisterMessageId(), null, getInputHandler());
    }
    public abstract int getRegisterMessageId();

    public abstract Handler getInputHandler();

    protected void sendMessage(int msg) {
        sendMessage(msg, null, null);
    }
    protected void sendMessage(int msg, Bundle data, Handler inputHandler) {
        Message message = Message.obtain(null, msg);
        if (data != null) {
            message.setData(data);
        }

        if (inputHandler != null) {
            message.replyTo = new Messenger(inputHandler);
        }
        try {
            serviceMessenger.send(message);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
