package org.xhan.subtitleplayer.subtitle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.xhan.subtitleplayer.SubtitlePlayerApplication;
import org.xhan.subtitleplayer.dagger.Dagger_MainComponent;
import org.xhan.subtitleplayer.service.ControllerServiceConnection;
import org.xhan.subtitleplayer.service.IServiceConnectionHandler;
import org.xhan.subtitleplayer.service.SubPlayerService;

import javax.inject.Inject;

/**
 * Created by xhan on 12/15/14.
 */
public class SubtitlePlayer {

	private Subtitle subtitle;

	@Inject
	SubtitlePlayerApplication context;

	Activity activity;

	public SubtitlePlayer(Subtitle subtitle, Activity activity) {
		Dagger_MainComponent.create();
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
