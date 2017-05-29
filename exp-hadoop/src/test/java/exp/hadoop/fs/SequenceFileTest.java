package exp.hadoop.fs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.util.ReflectionUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import exp.hadoop.minicluster.MiniCluster;
import junit.framework.Assert;

public class SequenceFileTest {
    private static MiniCluster cluster;

    private static final String TEST_CLUSTER = "cluster";
    private static final String BASE_DIR = "data";

    private static Configuration config;

    @BeforeClass
    public static void setUpBeforeClass() {
        cluster = new MiniCluster(BASE_DIR, TEST_CLUSTER);
        cluster.startCluster();

        config = cluster.getConfiguration();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        cluster.shutdownCluster();
    }

    @Test
    public void testReadWriteFiles() throws IOException {
        writeFiles(config.get(MiniDFSCluster.HDFS_MINIDFS_BASEDIR), 5, 10);
        readFiles(config.get(MiniDFSCluster.HDFS_MINIDFS_BASEDIR), 5, 10);
    }

    public void writeFiles(final String baseDir, final int numberOfFiles, final int numberOfLines) throws IOException {
        for (int indexFile = 0; indexFile < numberOfFiles; indexFile++) {
            final String fileName = String.format("%s/%d.seq", baseDir, indexFile);
            final SequenceFile.Writer writer = HdfsUtils.createWriter(config, fileName, IntWritable.class, BytesWritable.class);

            for (int i = 0; i < 10; i++) {
                final String line = String.format("test%d", i);
                final IntWritable key = new IntWritable(i);
                final BytesWritable value = new BytesWritable(line.getBytes());
                writer.append(key, value);
            }

            IOUtils.closeStream(writer);
        }
    }

    public void readFiles(final String baseDir, final int numberOfFiles, final int numberOfLines) throws IOException {
        int count = 0;

        for (int indexFile = 0; indexFile < numberOfFiles; indexFile++) {
            final String fileName = String.format("%s/%d.seq", baseDir, indexFile);
            final SequenceFile.Reader reader = HdfsUtils.createReader(config, fileName);

            count = 0;

            IntWritable key = (IntWritable) ReflectionUtils.newInstance(reader.getKeyClass(), config);
            BytesWritable value = (BytesWritable) ReflectionUtils.newInstance(reader.getValueClass(), config);
            while (reader.next(key, value)) {
                System.out.println(String.format("%d: %s", key.get(), new String(value.getBytes())));
                count++;
            }

            Assert.assertEquals(numberOfLines, count);

            IOUtils.closeStream(reader);
        }
    }
}
