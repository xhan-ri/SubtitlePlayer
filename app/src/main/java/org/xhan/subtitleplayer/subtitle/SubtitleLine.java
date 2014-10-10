package org.xhan.subtitleplayer.subtitle;

import java.sql.Timestamp;

/**
 * Created by xhan on 10/9/14.
 */
public class SubtitleLine {
    private int index;
    private Timestamp timestamp;
    private String content;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SubtitleLine{");
        sb.append("index=").append(index);
        sb.append(", timestamp=").append(timestamp);
        sb.append(", content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
