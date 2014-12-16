package org.xhan.subtitleplayer.subtitle;

import android.util.Log;

import com.google.common.base.Strings;
import com.google.common.io.LineProcessor;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by xhan on 10/14/14.
 */
public class SubtitleLineProcessor implements LineProcessor<List<SubtitleLine>> {

    private enum LineType {
        INDEX,
        TIME,
        CONTENT,
        END,
        UNKNOWN
    }

    SubtitleLine subLine;
    List<SubtitleLine> subLineList;
    LineType lineType;

    public SubtitleLineProcessor() {
        subLineList = new LinkedList<SubtitleLine>();
        lineType = LineType.UNKNOWN;
    }
    @Override
    public boolean processLine(String line) throws IOException {
        if (subLine == null) {
            subLine = new SubtitleLine();
        }
        if (isEndOfItem(line)) {
            subLineList.add(subLine);
            subLine = new SubtitleLine();
	        lineType = LineType.END;
        } else {
            lineType = getNextLineType(lineType);
            switch (lineType) {
                case INDEX:
                    parseIndexLine(line);
                    break;
                case TIME:
                    parseTimeRange(line);
                    break;
                case CONTENT:
                    parseContent(line);
                    break;

            }
        }
        return true;
    }

    @Override
    public List<SubtitleLine> getResult() {
	    if (subLine != null) {
		    subLineList.add(subLine);
		    subLine = null;
		    lineType = LineType.UNKNOWN;
	    }
        return subLineList;
    }

    private LineType getNextLineType(LineType lineType) {
        switch (lineType) {
            case INDEX:
                return LineType.TIME;
            case TIME:
                return LineType.CONTENT;
            case CONTENT:
                return LineType.CONTENT;
            case END:
                return LineType.INDEX;
            default:
                return LineType.INDEX;
        }
    }

    private boolean isEndOfItem(String line) {
        return Strings.isNullOrEmpty(line);
    }

    private void parseIndexLine(String line) {
	    Log.i("SubtitleLineProcessor", line);
	    subLine.setIndex(Integer.parseInt(line.trim()));
    }

    private void parseContent(String line) {
        subLine.appendContent(line);
    }

    public void parseTimeRange(String line) {
        String[] groups = line.split(" --> ");
        long start = parseTimePart(groups[0]);
        long end = parseTimePart(groups[1]);
        subLine.setStart(start);
        subLine.setSpan(end - start);
    }

    private long parseTimePart(String timePart) {
        String[] groups = timePart.split(",");
        long result = 0;
        String[] hms = groups[0].split(":");
        result += Integer.parseInt(hms[0]) * 60 * 60 * 1000; // hour
        result += Integer.parseInt(hms[1]) * 60 * 1000; // minute
        result += Integer.parseInt(hms[2]) * 1000; // second
        result += Integer.parseInt(groups[1]);
        return result;
    }
}
