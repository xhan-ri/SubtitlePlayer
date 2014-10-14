package org.xhan.subtitleplayer.subtitle;

import java.sql.Timestamp;

/**
 * Created by xhan on 10/9/14.
 */
public class SubtitleLine {
    private int index;
    private Timestamp from;
    private Timestamp to;
    private String content;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getFrom() {
        return from;
    }

    public void setFrom(Timestamp from) {
        this.from = from;
    }

    public Timestamp getTo() {
        return to;
    }

    public void setTo(Timestamp to) {
        this.to = to;
    }
}
