package ru.job4j.concurrent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * FileDownload
 *
 * @author Vladislav Nechaev
 * @since 28.08.2020
 */

public class FileDownloader {

    private static final long ONE_SECOND_IN_MILLIS = 1000;
    private static final Logger LOG = LogManager.getLogger(FileDownloader.class);

    /**
     * downloads an xml file and then writes him.
     *
     * @param url - the URL of source.
     * @param vol - the downloads limit (byte per second).
     */
    private void download(URL url, int vol) {
        String dest = System.getProperty("java.io.tmpdir") + "temp_pom.xml";
        long startProgram = System.currentTimeMillis();
        try (BufferedInputStream in = new BufferedInputStream(url.openStream(), vol);
             FileOutputStream out = new FileOutputStream(dest)) {
            byte[] buff = new byte[vol];
            int bytes;
            while (true) {
                long start = System.currentTimeMillis();
                bytes = in.read(buff, 0, vol);
                if (bytes == -1) {
                    break;
                }
                out.write(buff);
                long end = System.currentTimeMillis();
                long duration = end - start;
                if (duration < ONE_SECOND_IN_MILLIS) {
                    Thread.sleep(ONE_SECOND_IN_MILLIS - duration);
                }
            }
        } catch (IOException | InterruptedException e) {
            LOG.error(e.getMessage(), e);
        }
        long endProgram = System.currentTimeMillis();
        System.out.println("The Program duration: " + (endProgram - startProgram) / 1000 + "s.");
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("This method requires two parameters.");
        }
        String source = args[0];
        int vol = Integer.parseInt(args[1]);
        FileDownloader downloader = new FileDownloader();
        try {
            downloader.download(new URL(source), vol);
        } catch (MalformedURLException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
