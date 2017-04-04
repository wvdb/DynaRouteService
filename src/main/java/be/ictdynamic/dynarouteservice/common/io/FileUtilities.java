package be.ictdynamic.dynarouteservice.common.io;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Class FileUtilities.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 13-sep-2007 - 7:49:20
 */
public final class FileUtilities {

    /**
     * String constant defining the special characters that needs to be replaced in the file name.
     */
    public static final String FILENAME_PATTERN = "[\\W&&[^\\-\\.]]";

    /**
     * String constant defining the replacement the special characters must be replaced with in the file name.
     */
    public static final String FILENAME_PATTERN_REPLACE = "_";

    private FileUtilities() {
    }

    /**
     * Writes the given string to the given file. Converting all IOExceptions to RuntimeExceptions
     *
     * @param file    The file to write to
     * @param content The content to write
     */
    public static void writeStringToFile(File file, String content) {
        try {
            FileUtils.writeStringToFile(file, content);
        } catch (IOException e) {
            throw new RuntimeException(e);  //NOSONAR
        }
    }

    /**
     * To file.
     *
     * @param path the path
     * @return the file
     */
    public static File toFile(String... path) {
        File file = null;
        for (String name : path) {
            if (file == null) {
                file = new File(name);
            } else {
                file = new File(file, name);
            }
        }
        return file;
    }

    /**
     * Escape file name.
     *
     * @param fileName the file name
     * @return the string
     */
    public static String escapeFileName(String fileName) {
        return fileName.replaceAll(FILENAME_PATTERN, FILENAME_PATTERN_REPLACE);
    }

    /**
     * Reads the given file into a string converting IOExceptions to RuntimeExceptions
     *
     * @param file The file to read
     * @return The contents of the file as a string
     */
    public static String read(File file) {
        String content = null;
        try {
            content = FileUtils.readFileToString(file);
        } catch (IOException e) {
            throw new RuntimeException(e); //NOSONAR
        }
        return content;
    }

    /**
     * Reads the given url into a string converting IOExceptions to RuntimeExceptions
     *
     * @param url The url to read
     * @return The contents of the file as a string
     */
    public static String read(URL url) {
        String content = null;
        InputStream inputStream = null;
        try {
            inputStream = url.openStream();
            content = IOUtils.toString(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);   //NOSONAR
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return content;
    }
}
