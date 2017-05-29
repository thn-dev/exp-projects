package exp.hadoop.minicluster;

import org.junit.Assert;
import org.junit.Test;

public class MiniClusterTest {

    @Test
    public void testStartCluster() {
        final MiniCluster cluster = new MiniCluster();
        Assert.assertFalse(cluster.isClusterUp());

        cluster.startCluster();
        Assert.assertTrue(cluster.isClusterUp());
        cluster.shutdownCluster();
    }

    @Test
    public void testShutdownCluster() {
        final MiniCluster cluster = new MiniCluster();
        cluster.startCluster();
        Assert.assertTrue(cluster.isClusterUp());

        cluster.shutdownCluster();
        Assert.assertFalse(cluster.isClusterUp());
    }
}
