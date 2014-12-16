package org.xhan.subtitleplayer;

import android.app.Application;

import org.xhan.subtitleplayer.dagger.Injector;

import javax.inject.Inject;

import dagger.ObjectGraph;

/**
 * Created by xhan on 12/15/14.
 */
public class SubtitlePlayerApplication extends Application {

	private static SubtitlePlayerApplication thisApp;
	private static ObjectGraph objectGraph;


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

	public static ObjectGraph getObjectGraph() {
		if (objectGraph == null) {
			objectGraph = ObjectGraph.create(new Injector());
		}

		return objectGraph;
	}
}
