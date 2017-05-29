package exp.hadoop.minicluster;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.test.PathUtils;
import org.apache.log4j.Logger;

/*
 * References:
 * - http://www.lopakalogic.com/articles/hadoop-articles/hadoop-testing-with-minicluster/
 *
 */
public class MiniCluster {
    private final static Logger log = Logger.getLogger(MiniCluster.class);

    private static final String BASEDIR_NAME = "data";
    private static final String CLUSTER_NAME = "cluster";

    private final File baseDir;
    private final File baseCluster;
    private final Configuration config;
    private MiniDFSCluster cluster;

    public MiniCluster() {
        this(BASEDIR_NAME, CLUSTER_NAME);
    }

    public MiniCluster(final String baseDirName, final String clusterName) {
        System.clearProperty(MiniDFSCluster.PROP_TEST_BUILD_DATA);

        this.baseDir = new File(PathUtils.getTestDir(MiniCluster.class), baseDirName);
        this.baseCluster = new File(baseDir, clusterName);

        this.config = new HdfsConfiguration();
        this.config.set(MiniDFSCluster.HDFS_MINIDFS_BASEDIR, baseCluster.getAbsolutePath());
    }

    public void startCluster() {
        shutdownCluster();

        try {
            cluster = new MiniDFSCluster.Builder(config).build();
        }
        catch (final IOException e) {
            log.error("Unable to start the mini cluster", e);
        }
    }

    public void shutdownCluster() {
        if (cluster != null && cluster.isClusterUp()) {
            cluster.shutdown();
        }
        cluster = null;
    }

    public boolean isClusterUp() {
        if (cluster != null && cluster.isClusterUp()) {
            return true;
        }

        return false;
    }

    public Configuration getConfiguration() {
        return this.config;
    }

    public FileSystem getFilesystem() {
        FileSystem fs = null;
        try {
            fs = FileSystem.get(config);
        }
        catch (final IOException e) {
            log.error("Unable to obtain the FileSystem instance", e);
        }

        return fs;
    }
}
