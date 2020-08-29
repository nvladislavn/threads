package ru.job4j.concurrent.downloaders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Starter
 *
 * @author Vladislav Nechaev
 * @since 29.08.2020
 */

public class Starter {

    private static final Logger LOG = LogManager.getLogger(Starter.class);

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("This method requires two parameters.");
        }
        String source = args[0];
        int limit = Integer.parseInt(args[1]);
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit(new Downloader(source, limit));
        executorService.shutdown();
        String text = "";
        try {
            text = future.get();
            String dest = System.getProperty("java.io.tmpdir") + "temp_pom.xml";
            TextWriter writer = new TextWriter(dest);
            writer.write(text);
            System.out.println("Downloading is complete.");
        } catch (InterruptedException | ExecutionException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
