package org.xhan.subtitleplayer.subtitle;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by xhan on 10/9/14.
 */
public class SubtitleLine {
    private int index;
    private long start;
    private long span;
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

    public void appendContent(String content) {
        this.content += System.getProperty("line.separator") + content;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getSpan() {
        return span;
    }

    public void setSpan(long span) {
        this.span = span;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("index", index)
                .add("start", start)
                .add("span", span)
                .add("content", content)
                .toString();
    }
}
