package org.xhan.subtitleplayer;

import android.app.Application;

import javax.inject.Inject;

/**
 * Created by xhan on 12/15/14.
 */
public class SubtitlePlayerApplication extends Application {

	private static SubtitlePlayerApplication thisApp;


	@Inject
	public SubtitlePlayerApplication() {
		thisApp = this;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public SubtitlePlayerApplication getInstance() {
		return thisApp;
	}
}
