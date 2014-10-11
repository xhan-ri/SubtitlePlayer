package org.xhan.subtitleplayer.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

/**
 * Created by xhan on 10/11/14.
 */
public class PlayerServiceConnection extends SubPlayerServiceConnection {

    public interface IPlayerUpdateHandler {
        void update(String text);
    }

    IPlayerUpdateHandler playerUpdateHandler;
    public PlayerServiceConnection(IServiceConnectionHandler handler, IPlayerUpdateHandler playerUpdateHandler) {
        super(handler);
        this.playerUpdateHandler = playerUpdateHandler;
    }

    public void play() {
        sendMessage(SubPlayerService.Messages.PLAYER_PLAY);
    }

    @Override
    public int getRegisterMessageId() {
        return SubPlayerService.Messages.PLAYER_REGISTER;
    }

    @Override
    public Handler getInputHandler() {
        return new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SubPlayerService.Messages.PLAYER_UPDATE:
                        Bundle data = msg.getData();
                        String newText = data.getString(SubPlayerService.Constants.MESSAGE_PLAYER_UPDATE);
                        if (playerUpdateHandler != null) {
                            playerUpdateHandler.update(newText);
                        }
                        break;
                }
            }
        };
    }
}
