package org.xhan.subtitleplayer.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;

import org.xhan.subtitleplayer.view.SubtitleTextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class SubPlayerService extends Service {
    boolean started =false;
    public class Messages {
        public static final int CONTROLLER_REGISTER = 100;
        public static final int CONTROLLER_OPEN_FILE = 101;
        public static final int CONTROLLER_INIT_PLAYER = 102;
        public static final int CONTROLLER_REMOVE_PLAYER = 103;
        public static final int PLAYER_REGISTER = 200;
        public static final int PLAYER_PLAY = 201;
        public static final int PLAYER_UPDATE = 202;
    }

    public class Constants {
        public static final String MESSENGER_NAME = "MESSENGER";
        public static final String MESSENGER_CONTROLLER = "CONTROLLER";
        public static final String MESSENGER_PLAYER = "PLAYER";

        public static final String MESSAGE_CONTROLLER_OPEN_FILENAME = "MSG_CTL_OPEN_FILENAME";
        public static final String MESSAGE_PLAYER_PLAY = "MSG_PLAYER_PLAY";
        public static final String MESSAGE_PLAYER_UPDATE = "MSG_PLAYER_UPDATE";

    }

    Messenger controllerMessenger, playerMessenger;
    View playerView;
    public SubPlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (!started) {
            startService(new Intent(this, SubPlayerService.class));
        }
        Messenger inputMessenger = null;
        if (isControllerMessenger(intent)) {
            inputMessenger = new Messenger(controllerMessageHandler);
        } else {
            inputMessenger = new Messenger(playerMessageHandler);
        }
        return inputMessenger.getBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Sub player service started", Toast.LENGTH_LONG).show();
        Notification.Builder builder=  new Notification.Builder(this);
        builder.setOngoing(true);
        builder.setContentTitle("Player service is running");
        builder.setContentText("Player info goes here");

        startForeground(1, builder.build());
        started = true;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Sub player service destroyed", Toast.LENGTH_LONG).show();
        stopForeground(true);
        super.onDestroy();
    }

    public static Intent getControllerBindIntent(Context context) {
        return getBindIntent(context, Constants.MESSENGER_CONTROLLER);
    }

    public static Intent getPlayerBindIntent(Context context) {
        return getBindIntent(context, Constants.MESSENGER_PLAYER);
    }

    private static Intent getBindIntent(Context context, String messengerName) {
        Intent intent = new Intent(context, SubPlayerService.class);
        intent.putExtra(Constants.MESSENGER_NAME, messengerName);
        return intent;
    }

    private String getMessengerName(Intent intent) {
        return intent.getStringExtra(Constants.MESSENGER_NAME);
    }

    private boolean isControllerMessenger(Intent intent) {
        return Constants.MESSENGER_CONTROLLER.equals(getMessengerName(intent));
    }

    private Handler controllerMessageHandler = new Handler() {


        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case Messages.CONTROLLER_REGISTER:
                    controllerMessenger = msg.replyTo;
                    break;
                case Messages.CONTROLLER_OPEN_FILE:
                    String filename = msg.getData().getString(Constants.MESSAGE_CONTROLLER_OPEN_FILENAME);
                    Toast.makeText(SubPlayerService.this, "Service: file name to open: " + filename, Toast.LENGTH_LONG).show();
                    break;
                case Messages.CONTROLLER_INIT_PLAYER:
                    initPlayer();
                    break;
                case Messages.CONTROLLER_REMOVE_PLAYER:
                    removePlayer();
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private Handler playerMessageHandler = new Handler() {


        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case Messages.PLAYER_REGISTER:
                    playerMessenger = msg.replyTo;
                    Toast.makeText(SubPlayerService.this, "Service: player registered. player=" + playerMessenger.toString(), Toast.LENGTH_LONG).show();
                    break;
                case Messages.PLAYER_PLAY:
                    Toast.makeText(SubPlayerService.this, "Service: start playing...", Toast.LENGTH_LONG).show();
                    sendUpdateToPlayer("Movie is playing!");
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);

        }
    };

    private void sendUpdateToPlayer(String text) {
        Message msg = Message.obtain(null, Messages.PLAYER_UPDATE);
        Bundle data = new Bundle();
        data.putString(Constants.MESSAGE_PLAYER_UPDATE, text);
        msg.setData(data);

        try {
            playerMessenger.send(msg);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void initPlayer() {
        final WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;
        final SubtitleTextView player = new SubtitleTextView(this);

        player.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(player, params);
                        return true;
                }
                return false;
            }
        });
        windowManager.addView(player, params);
        playerView = player;
    }

    private void removePlayer() {
        if (playerView != null) {
            final WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            windowManager.removeView(playerView);
            playerView = null;
        }
    }

    private void openSubtitle(String filePath) {
    }
}
