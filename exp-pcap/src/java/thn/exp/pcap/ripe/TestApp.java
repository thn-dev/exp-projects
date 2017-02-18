package thn.exp.pcap.ripe;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import net.ripe.hadoop.pcap.PcapReader;
import net.ripe.hadoop.pcap.packet.Packet;
import org.apache.log4j.Logger;

public class TestApp {
    private static Logger log = Logger.getLogger(TestApp.class);
    
    private PcapReader reader;
    
    public TestApp() {
        this.reader = null;
    }
 
    public void run(final String pcapPath) {
        try (final FileInputStream fis = new FileInputStream(pcapPath);) {
        this.reader = new PcapReader(new DataInputStream(fis));
        displayInfo();
        }
        catch (final IOException ioe) {
            log.error("Unable to process: " + pcapPath, ioe);
        }
    }
    
    public void displayInfo() {
        int count = 0;
        for (final Packet packet : reader) {
            if (packet.get(Packet.ID) != null) {                
                log.info(String.format("[%s, %s] %s:%s [%s:%s - %s:%s]", 
                    packet.get(Packet.ID), ++count,
                    packet.get(Packet.TIMESTAMP), packet.get(Packet.PROTOCOL), 
                    packet.get(Packet.SRC), packet.get(Packet.SRC_PORT),
                    packet.get(Packet.DST), packet.get(Packet.DST_PORT)));
            }
            //log.info("packet: " + ++count);
        }
    }
    
    public static void main(String[] args) {
        final TestApp app = new TestApp();
        app.run(args[0]);
    }
}
