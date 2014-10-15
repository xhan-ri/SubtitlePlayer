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

    public Subtitle readSubFile(String filePath, Charset charset) {
        final File subFile = new File(filePath);
        if (!(subFile.exists() && subFile.canRead())) {
            return null;
        }
        final Subtitle subtitle = new Subtitle();
        final SubtitleLine subtitleLine = new SubtitleLine();
        SubtitleLineProcessor lineProcessor = new SubtitleLineProcessor();
        try {
            Files.readLines(subFile, charset, lineProcessor);
        } catch (IOException e) {
           throw new RuntimeException(e);
        }
        subtitle.setLines(lineProcessor.getResult());
        return subtitle;
    }


}
