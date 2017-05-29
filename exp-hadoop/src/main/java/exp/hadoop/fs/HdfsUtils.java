package exp.hadoop.fs;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.log4j.Logger;

public class HdfsUtils {
    private final static Logger logger = Logger.getLogger(HdfsUtils.class);

    public static Configuration init(final String hadoopFS) {
        final Configuration config = new Configuration();
        config.set("fs.defaultFS", hadoopFS);
        return config;
    }

    public static SequenceFile.Writer createWriter(final Configuration config, final String sequenceFileName, final Class<?> keyClass, final Class<?> valueClass) throws IOException {
        final Path path = new Path(sequenceFileName);

        return createWriter(config, path, keyClass, valueClass);
    }

    public static SequenceFile.Writer createWriter(final Configuration config, final Path path, final Class<?> keyClass, final Class<?> valueClass) throws IOException {
        return SequenceFile.createWriter(config, SequenceFile.Writer.file(path), SequenceFile.Writer.keyClass(keyClass), SequenceFile.Writer.valueClass(valueClass));
    }

    public static void closeWriter(final SequenceFile.Writer writer) {
        IOUtils.closeStream(writer);
    }

    public static SequenceFile.Reader createReader(final Configuration config, final String sequenceFileName) throws IOException {
        final Path path = new Path(sequenceFileName);

        return createReader(config, path);
    }

    public static SequenceFile.Reader createReader(final Configuration config, final Path path) throws IOException {
        return new SequenceFile.Reader(config, SequenceFile.Reader.file(path));
    }

    public static void closeReader(final SequenceFile.Reader reader) {
        IOUtils.closeStream(reader);
    }

    public static void displayConfiguration(final Configuration config) {
        logger.info("# HDFS configuration");

        int i = 0;
        final Iterator<Entry<String, String>> iterator = config.iterator();
        while (iterator.hasNext()) {
            logger.info(String.format("%03d: %s", i++, iterator.next()));
        }
    }
}
