package org.xhan.subtitleplayer.subtitle;

import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;

import org.xhan.subtitleplayer.SubTitlePlayerMain;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by xhan on 10/13/14.
 */
public class SubtitleManager {
    public enum LineType {
        INDEX,
        TIME,
        CONTENT,
        END,
        UNKNOWN
    }
    public Subtitle readSubFile(String filePath, Charset charset) {
        final File subFile = new File(filePath);
        if (!(subFile.exists() && subFile.canRead())) {
            return null;
        }
        final Subtitle subtitle = new Subtitle();
        final SubtitleLine subtitleLine = new SubtitleLine();
        LineType lineType = LineType.UNKNOWN;
        try {
            Files.readLines(subFile, charset, new LineProcessor<Object>() {
                @Override
                public boolean processLine(String line) throws IOException {
                    if (isEndOfItem(line)) {
                        final SubtitleLine finalLine = subtitleLine;
                        subtitle.addLine(finalLine);
                    } else {

                    }
                    return true;
                }

                @Override
                public Object getResult() {
                    return null;
                }
            });
        } catch (IOException e) {
           throw new RuntimeException(e);
        }
        return null;
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
}
