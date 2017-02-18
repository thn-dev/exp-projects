package thn.exp.pcap.hadoop.ripe;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class PcapReducer extends MapReduceBase implements
        Reducer<IntWritable, LongWritable, IntWritable, LongWritable>
{
    @Override
    public void reduce(final IntWritable key,
            final Iterator<LongWritable> values,
            final OutputCollector<IntWritable, LongWritable> output,
            final Reporter reporter) throws IOException
    {
        long sum = 0;
        while (values.hasNext())
        {
            sum += values.next().get();
        }

        output.collect(key, new LongWritable(sum));
    }
}
