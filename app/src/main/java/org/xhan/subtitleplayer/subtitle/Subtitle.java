package org.xhan.subtitleplayer.subtitle;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by xhan on 10/9/14.
 */
public class Subtitle {
    List<SubtitleLine> lines;
    public Subtitle() {
        lines = new LinkedList<SubtitleLine>();
    }
    public void addLine(SubtitleLine line) {
        lines.add(line);
    }
}
