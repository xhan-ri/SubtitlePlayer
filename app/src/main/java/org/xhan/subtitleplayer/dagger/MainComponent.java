package org.xhan.subtitleplayer.dagger;

import org.xhan.subtitleplayer.SubtitlePlayerApplication;

import dagger.Component;

/**
 * Created by xhan on 1/21/15.
 */
@Component
public interface MainComponent {
	SubtitlePlayerApplication subtitlePlayerApplication();
}
