package org.xhan.subtitleplayer.protocol;

import java.io.File;

/**
 * Created by xhan on 10/9/14.
 */
public interface IWorkService {
    void load(File file);
    void play();
    void pause();
    void resume();
    void stop();
    void seek(int toTime);
}
