package thn.exp.pcap.pcap4j;

import java.io.EOFException;
import java.util.concurrent.TimeoutException;
import org.apache.log4j.Logger;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.Packet.Header;

public class TestApp {
    private static Logger log = Logger.getLogger(TestApp.class);
    
    private PcapHandle handle;
    
    public TestApp() {
        this.handle = null;
    }
 
    public void run(final String pcapPath) {
        try {
            handle = Pcaps.openOffline(pcapPath);
            displayInfo();
        }
        catch (final PcapNativeException e) {
            log.info("Unable to open " + pcapPath);
        }
    }
    
    public void displayInfo() {
        Packet packet = null;
        int count = 0;
        try {
            while ((packet = handle.getNextPacketEx()) != null) {  
                count++;
                
                final Header header = packet.getHeader();
                if (header != null) {
                    log.info(String.format("[%s] %s", count, header.toString()));
                }
                //log.info("packet: " + ++count);
            }
        }
        catch (final NotOpenException |PcapNativeException | EOFException | TimeoutException e) {
            log.info("Unable to process: " + handle.toString(), e);
        }
        finally {
            handle.close();
        }
    }
    
    public static void main(String[] args) {
        final TestApp app = new TestApp();
        app.run(args[0]);
    }
    
}
