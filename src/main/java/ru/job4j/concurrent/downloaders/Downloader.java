package ru.job4j.concurrent.downloaders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 * CollableDownload
 *
 * @author Vladislav Nechaev
 * @since 29.08.2020
 */

public class Downloader implements Callable<String> {

    private static final long ONE_SECOND_IN_MILLIS = 1000;
    private static final Logger LOG = LogManager.getLogger(Downloader.class);
    private final String source;
    private final int limit;

    public Downloader(String source, int limit) {
        this.source = source;
        this.limit = limit;
    }

    /**
     * downloads an xml file.
     *
     * @return the downloaded text.
     * @throws Exception if unable to compute a result
     */
    @Override
    public String call() throws Exception {
        URL url = new URL(source);
        StringBuilder result = new StringBuilder();
        try (BufferedInputStream in = new BufferedInputStream(url.openStream(), limit)) {
            byte[] buff = new byte[limit];
            int bytes;
            while (true) {
                long start = System.currentTimeMillis();
                bytes = in.read(buff, 0, limit);
                if (bytes == -1) {
                    break;
                }
                result.append(new String(buff));
                long end = System.currentTimeMillis();
                long duration = end - start;
                if (duration < ONE_SECOND_IN_MILLIS) {
                    Thread.sleep(ONE_SECOND_IN_MILLIS - duration);
                }
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return result.toString();
    }
}
