package org.xhan.subtitleplayer.subtitle;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

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
        if (lines == null) {
            lines = new LinkedList<SubtitleLine>();
        }
        lines.add(line);
    }

    public List<SubtitleLine> getLines() {
        return lines;
    }

    public void setLines(List<SubtitleLine> lines) {
        this.lines = lines;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("lines", lines)
                .toString();
    }
}
