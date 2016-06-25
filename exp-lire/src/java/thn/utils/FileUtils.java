package thn.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.log4j.Logger;

public class FileUtils {
	private static Logger log = Logger.getLogger(FileUtils.class);

	public static List<String> loadFile(final String fileName) {
		return loadFile(fileName, Charset.defaultCharset());
	}

	public static List<String> loadFile(final String fileName, final Charset charset) {
		return loadFile(Paths.get(fileName), Charset.defaultCharset());
	}

	public static List<String> loadFile(final Path inputFile) {
		return loadFile(inputFile, Charset.defaultCharset());
	}

	public static List<String> loadFile(final Path inputFile, final Charset charset) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(inputFile, charset);
        }
        catch (final IOException | SecurityException e) {
            log.error("Invalid file: " + inputFile.toString(), e);
        }
        return lines;
    }

    public static void displayItems(final List<String> items) {
        int lineNumber = 0;
        for (final String item : items) {
            log.info(String.format("[%s] %s", ++lineNumber, item));
        }
    }
}
