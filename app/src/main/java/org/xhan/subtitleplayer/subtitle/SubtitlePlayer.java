package org.xhan.subtitleplayer.subtitle;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.widget.Toast;

import org.xhan.subtitleplayer.SubtitlePlayerApplication;
import org.xhan.subtitleplayer.service.ControllerServiceConnection;
import org.xhan.subtitleplayer.service.IServiceConnectionHandler;
import org.xhan.subtitleplayer.service.SubPlayerService;

import java.nio.charset.Charset;

import javax.inject.Inject;

import dagger.ObjectGraph;

/**
 * Created by xhan on 12/15/14.
 */
public class SubtitlePlayer {

	private Subtitle subtitle;

	@Inject
	SubtitlePlayerApplication context;

	Activity activity;

	public SubtitlePlayer(Subtitle subtitle, Activity activity) {
		SubtitlePlayerApplication.getObjectGraph().inject(this);
		this.activity = activity;
		this.subtitle = subtitle;
		init();

	}

	private void init() {
		activity.startService(new Intent(activity, SubPlayerService.class));
		final ControllerServiceConnection finalConnection = new ControllerServiceConnection(new IServiceConnectionHandler<ControllerServiceConnection>() {
			@Override
			public void onConnected(ControllerServiceConnection connection) {
				connection.register();
				Toast.makeText(activity, "service connected", Toast.LENGTH_LONG).show();
			}
		});
		activity.bindService(SubPlayerService.getControllerBindIntent(activity), finalConnection, Context.BIND_AUTO_CREATE);
		finalConnection.initPlayer();
	}
}
