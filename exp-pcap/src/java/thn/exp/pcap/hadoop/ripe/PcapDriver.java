package thn.exp.pcap.hadoop.ripe;

import net.ripe.hadoop.pcap.io.CombinePcapInputFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/*
 * based on example given from RIPE-NCC's github
 * https://github.com/RIPE-NCC/hadoop-pcap/tree/master/hadoop-pcap-lib
 *
 */
public class PcapDriver extends Configured implements Tool
{
    @Override
    public int run(final String[] args) throws Exception
    {
        final JobConf conf = new JobConf(getConf(), PcapDriver.class);

        conf.setJobName("Pcap");

        conf.setOutputKeyClass(IntWritable.class);
        conf.setOutputValueClass(LongWritable.class);

        conf.setInputFormat(CombinePcapInputFormat.class);

        conf.setMapperClass(PcapMapper.class);
        conf.setReducerClass(PcapReducer.class);

        // Combine input files into splits of 100MB in size
        conf.setLong("mapred.max.split.size", 104857600);

        FileInputFormat.addInputPath(conf, new Path("input"));
        FileOutputFormat.setOutputPath(conf, new Path("output"));

        return JobClient.runJob(conf).isSuccessful() ? 0 : 1;
    }

    public PcapDriver()
    {
        super(new Configuration());
    }

    public static void main(final String[] args) throws Exception
    {
        final int res = ToolRunner.run(new PcapDriver(), args);
        System.exit(res);
    }
}
