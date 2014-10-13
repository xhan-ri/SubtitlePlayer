package org.xhan.subtitleplayer.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import org.xhan.subtitleplayer.service.IServiceConnectionHandler;
import org.xhan.subtitleplayer.service.SubPlayerService;

/**
* Created by xhan on 10/10/14.
*/
public class ControllerServiceConnection extends SubPlayerServiceConnection {

    public ControllerServiceConnection(IServiceConnectionHandler handler) {
        super(handler);
    }

    public void openFile(String filename) {
        Bundle data = new Bundle();
        data.putString(SubPlayerService.Constants.MESSAGE_CONTROLLER_OPEN_FILENAME, filename);
        sendMessage(SubPlayerService.Messages.CONTROLLER_OPEN_FILE, data, null);
    }

    public void initPlayer() {
        sendMessage(SubPlayerService.Messages.CONTROLLER_INIT_PLAYER);
    }

    public void removePlayer() {
        sendMessage(SubPlayerService.Messages.CONTROLLER_REMOVE_PLAYER);
    }

    @Override
    public int getRegisterMessageId() {
        return SubPlayerService.Messages.CONTROLLER_REGISTER;
    }

    @Override
    public Handler getInputHandler() {
        return new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
    }
}
