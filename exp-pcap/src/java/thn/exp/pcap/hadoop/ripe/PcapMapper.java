package thn.exp.pcap.hadoop.ripe;

import java.io.IOException;

import net.ripe.hadoop.pcap.packet.Packet;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.ObjectWritable;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class PcapMapper extends MapReduceBase implements
        Mapper<LongWritable, ObjectWritable, IntWritable, LongWritable>
{
    private final static LongWritable ONE = new LongWritable(1);
    private final IntWritable srcPort = new IntWritable();

    @Override
    public void map(final LongWritable key, final ObjectWritable value,
            final OutputCollector<IntWritable, LongWritable> output,
            final Reporter reporter) throws IOException
    {
        final Packet packet = (Packet) value.get();
        if (packet != null)
        {
            final Object srcPortVal = packet.get(Packet.SRC_PORT);
            if (srcPortVal != null)
            {
                srcPort.set((Integer) srcPortVal);
                output.collect(srcPort, ONE);
            }
        }
    }
}
