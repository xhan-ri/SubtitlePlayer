package org.xhan.subtitleplayer.service;

/**
* Created by xhan on 10/11/14.
*/
public interface IServiceConnectionHandler <T extends SubPlayerServiceConnection> {
    void onConnected(T connection);
}
