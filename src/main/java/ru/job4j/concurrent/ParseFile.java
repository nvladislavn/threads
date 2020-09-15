package ru.job4j.concurrent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

/**
 * ParseFile
 *
 * @author Vladislav Nechaev
 * @since 15.09.2020
 */

public class ParseFile {

    private static final Logger LOGGER = LogManager.getLogger(ParseFile.class);
    private File source;
    private File target;

    public synchronized void setSource(File source) {
        this.source = source;
    }

    public synchronized File getSource() {
        return source;
    }

    public synchronized File getTarget() {
        return target;
    }

    public synchronized void setTarget(File target) {
        this.target = target;
    }

    public synchronized String getContent() {
        String output = "";
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(source))) {
            int data;
            while ((data = in.read()) > 0) {
                output += (char) data;
            }
        } catch (IOException ioe) {
            LOGGER.error(ioe.getMessage(), ioe);
        }
        return output;
    }

    public synchronized String getContentWithoutUnicode() {
        String output = "";
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(source))) {
            int data;
            while ((data = in.read()) > 0) {
                if (data < 0x80) {
                    output += (char) data;
                }
            }
        } catch (IOException ioe) {
            LOGGER.error(ioe.getMessage(), ioe);
        }
        return output;
    }

    public synchronized void saveContent(String content) {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(target))) {
            byte[] text = content.getBytes();
            out.write(text);
        } catch (IOException ioe) {
            LOGGER.error(ioe.getMessage(), ioe);
        }
    }
}
