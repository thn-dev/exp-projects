package exp.hadoop.client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

public class HadoopClientTest {
    private final static Logger log = Logger.getLogger(HadoopClientTest.class);

    private static final String sequenceFileName = "/tmp/test.seq";
    private static final String hadoopLocalFS = "file:///";

    @Test
    public void testWriteSequenceFile() {
        writeSequenceFile(hadoopLocalFS);
    }

    private void writeSequenceFile(final String localtion) {
        final String dataFileName = "/tmp/test.txt";

        try {
            final int numOfLines = 20;
            final String baseStr = "....Test.Remote..";

            final List<String> lines = new ArrayList<String>();
            for (int i = 0; i < numOfLines; i++)
                lines.add(i + baseStr + UUID.randomUUID());

            final File dataFile = new File(dataFileName);
            FileUtils.writeLines(dataFile, lines, true);
            Thread.sleep(2000);

            HadoopClient.writeToSequenceFile(dataFile, sequenceFileName, localtion);
        }
        catch (final IOException | InterruptedException e) {
            log.error("Unable to write to a sequence file", e);
        }
    }

    @Test
    public void testReadSequenceFile() {
        try {
            HadoopClient.readSequenceFile(sequenceFileName, hadoopLocalFS);
        }
        catch (final IOException e) {
            log.error("Unable to read to a sequence file", e);
        }
    }
}
