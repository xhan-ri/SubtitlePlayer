package org.xhan.subtitleplayer.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import org.xhan.subtitleplayer.view.SubtitleTextView;

public class SubPlayerService extends Service {
    public class Messages {
        public static final int CONTROLLER_REGISTER = 1;
        public static final int PLAYER_REGISTER = 2;
        public static final int CONTROLLER_OPEN_FILE = 3;
        public static final int PLAYER_PLAY = 4;
    }

    public class Constants {
        public static final String MESSENGER_NAME = "MESSENGER";
        public static final String MESSENGER_CONTROLLER = "CONTROLLER";
        public static final String MESSENGER_PLAYER = "PLAYER";

        public static final String MESSAGE_CONTROLLER_OPEN_FILENAME = "MSG_CTL_OPEN_FILENAME";
        public static final String MESSAGE_PLAYER_PLAY = "MSG_PLAYER_PLAY";

    }

    Messenger controllerMessenger, playerMessenger;
    public SubPlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (isControllerMessenger(intent)) {
            controllerMessenger = new Messenger(controllerMessageHandler);
            return controllerMessenger.getBinder();
        } else {
            playerMessenger = new Messenger(playerMessageHandler);
            return playerMessenger.getBinder();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Sub player service started", Toast.LENGTH_LONG).show();
        initPlayer();
        return super.onStartCommand(intent, flags, startId);
    }

    public void initPlayer() {
        final WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);;
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
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Sub player service destroyed", Toast.LENGTH_LONG).show();
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
            super.handleMessage(msg);
            switch (msg.what) {
                case Messages.CONTROLLER_OPEN_FILE:
                    String filename = msg.getData().getString(Constants.MESSAGE_CONTROLLER_OPEN_FILENAME);
                    Toast.makeText(SubPlayerService.this, "Service: file name to open: " + filename, Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    private Handler playerMessageHandler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Messages.PLAYER_PLAY:
                    Toast.makeText(SubPlayerService.this, "Service: start playing...", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }


        }
    };
}