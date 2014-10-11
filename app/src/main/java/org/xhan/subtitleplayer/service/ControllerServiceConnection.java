package org.xhan.subtitleplayer.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
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
        Message message = Message.obtain(null, SubPlayerService.Messages.CONTROLLER_OPEN_FILE);
        Bundle data = new Bundle();
        data.putString(SubPlayerService.Constants.MESSAGE_CONTROLLER_OPEN_FILENAME, filename);
        message.setData(data);
        try {
            serviceMessenger.send(message);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
