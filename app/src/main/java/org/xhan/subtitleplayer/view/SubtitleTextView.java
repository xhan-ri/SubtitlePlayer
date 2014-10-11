package org.xhan.subtitleplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import org.xhan.subtitleplayer.service.IServiceConnectionHandler;
import org.xhan.subtitleplayer.service.PlayerServiceConnection;
import org.xhan.subtitleplayer.service.SubPlayerService;
import org.xhan.subtitleplayer.service.SubPlayerServiceConnection;

/**
 * Created by xhan on 10/10/14.
 */
public class SubtitleTextView extends TextView {
    public SubtitleTextView(Context context) {
        super(context);
        setText("Test is a test");
        bindToService(context);
    }

    private void bindToService(Context context) {
        PlayerServiceConnection serviceConnection = new PlayerServiceConnection(new IServiceConnectionHandler<PlayerServiceConnection>() {
            @Override
            public void onConnected(PlayerServiceConnection connection) {
                connection.register();
                connection.play();
            }
        }, new PlayerServiceConnection.IPlayerUpdateHandler() {
            @Override
            public void update(String text) {
                setText(text);
            }
        });
        context.bindService(SubPlayerService.getPlayerBindIntent(context), serviceConnection, Context.BIND_AUTO_CREATE);
    }
}
