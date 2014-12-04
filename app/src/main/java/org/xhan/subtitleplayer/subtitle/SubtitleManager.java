package org.xhan.subtitleplayer.subtitle;

import android.content.Context;

import com.google.common.base.Strings;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;

import org.xhan.subtitleplayer.SubTitlePlayerMain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.nio.charset.Charset;

/**
 * Created by xhan on 10/13/14.
 */
public class SubtitleManager {

	Context context;

	public SubtitleManager(Context context) {
		this.context = context;
	}

	public Subtitle readSubFile(String filePath, Charset charset) {
        final File subFile = new File(filePath);
        if (!(subFile.exists() && subFile.canRead())) {
            return null;
        }
		final File normalizedFile = removePOM(subFile);
        final Subtitle subtitle = new Subtitle();
        SubtitleLineProcessor lineProcessor = new SubtitleLineProcessor();
        try {

            Files.readLines(normalizedFile, charset, lineProcessor);
        } catch (IOException e) {
           throw new RuntimeException(e);
        }
        subtitle.setLines(lineProcessor.getResult());
        return subtitle;
    }


	private File removePOM(File rawFile) {
		try {
			byte[] bytes = Files.toByteArray(rawFile);
			if (bytes[0] == (byte) 0xEF && bytes[1] == (byte) 0xBB && bytes[2] == (byte) 0xBF) {
				File tmpFile = new File(context.getCacheDir(), "subtitle.tmp");
				FileOutputStream outputStream = new FileOutputStream(tmpFile, false);
				outputStream.write(bytes, 3, bytes.length - 3);
				Closeables.close(outputStream, true);
				return tmpFile;
			}
		} catch (Exception e) {

		}
		return rawFile;
	}
}
