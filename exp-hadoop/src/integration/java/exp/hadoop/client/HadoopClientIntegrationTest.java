package exp.hadoop.client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.log4j.Logger;
import org.junit.Test;

public class HadoopClientIntegrationTest {
    private final static Logger log = Logger.getLogger(HadoopClientIntegrationTest.class);

    HadoopClient hadoopClient;

    private static final String sequenceFileName = "/tmp/test.seq";
    private static final String hadoopRemoteFS = "hdfs://localhost:9000";

    @Test
    public void testConfig() {
        Configuration conf = new Configuration();
        HadoopClient.listHadoopConfiguration(conf);
    }

    @Test
    public void testWriteSequenceFile_remoteHDFS() {
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

            HadoopClient.writeToSequenceFile(dataFile, sequenceFileName, hadoopRemoteFS);
        }
        catch (final IOException | InterruptedException e) {
            log.error("Unable to write to a sequence file", e);
        }
    }

    @Test
    public void testReadSequenceFile() {
        try {
            HadoopClient.readSequenceFile(sequenceFileName, hadoopRemoteFS);
        }
        catch (final IOException e) {
            log.error("Unable to read to a sequence file", e);
        }
    }

    @Test
    public void testCopySequenceFileToRemoteHDFS() {
        String tempFileName = "/tmp/test2.txt";
        String sequenceFileName = "/tmp/test2.seq";
        String hadoopLocalFS = "file:///";
        String hadoopRemoteFS = "hdfs://localhost:9000";

        try {
            int numOfLines = 5;
            String baseStr = "....Test...";
            List<String> lines = new ArrayList<String>();
            for (int i = 0; i < numOfLines; i++)
                lines.add(i + baseStr + UUID.randomUUID());

            File dataFile = new File(tempFileName);
            FileUtils.writeLines(dataFile, lines, true);
            Thread.sleep(2000);
            HadoopClient.writeToSequenceFile(dataFile, sequenceFileName, hadoopLocalFS);
            HadoopClient.readSequenceFile(sequenceFileName, hadoopLocalFS);
            HadoopClient.copySequenceFile(sequenceFileName, sequenceFileName, hadoopRemoteFS);
            HadoopClient.readSequenceFile(sequenceFileName, hadoopRemoteFS);
        }
        catch (final IOException | InterruptedException e) {
            log.error("Unable to copy a sequence file", e);
        }
    }
}
