package org.xhan.subtitleplayer.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

/**
 * Created by xhan on 10/11/14.
 */
public class PlayerServiceConnection extends SubPlayerServiceConnection {

    public PlayerServiceConnection(IServiceConnectionHandler handler) {
        super(handler);
    }

    public void play() {
        Message message = Message.obtain(null, SubPlayerService.Messages.PLAYER_PLAY);
        try {
            serviceMessenger.send(message);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
