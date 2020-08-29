package ru.job4j.concurrent.downloaders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * TextWriter
 *
 * @author Vladislav Nechaev
 * @since 29.08.2020
 */

public class TextWriter {

    private static final Logger LOG = LogManager.getLogger(TextWriter.class);
    private String destination;

    public TextWriter(String destination) {
        this.destination = destination;
    }

    /**
     * Write the given text to the target directory {@see TextWriter#destination}
     *
     * @param text - the text to write.
     */
    public void write(String text) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(destination))) {
            out.write(text);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
