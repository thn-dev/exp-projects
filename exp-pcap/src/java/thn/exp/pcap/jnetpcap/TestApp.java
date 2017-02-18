package thn.exp.pcap.jnetpcap;

import org.apache.log4j.Logger;
import org.jnetpcap.Pcap;
import org.jnetpcap.nio.JMemory;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.protocol.network.Arp;
import org.jnetpcap.protocol.network.Icmp;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.network.Ip6;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

public class TestApp {
    private static Logger log = Logger.getLogger(TestApp.class);
    
    private Pcap pcap;
    
    public TestApp() {
        this.pcap = null;
    }
    
    public void run(final String pcapPath) {
        final StringBuilder errbuf = new StringBuilder();
        
        pcap = Pcap.openOffline(pcapPath, errbuf);
        displayInfo();
    }
    
    public void displayInfo() {
        final PcapPacket packet = new PcapPacket(JMemory.POINTER);
        
        final Ethernet eth = new Ethernet();
        final Ip4 ip4 = new Ip4();
        final Ip6 ip6 = new Ip6();
        final Icmp icmp = new Icmp();
        final Arp arp = new Arp();
        
	final Tcp tcp = new Tcp();
        final Http http = new Http();
        
        final Udp udp = new Udp();
        
        boolean running = true;
        while (running) {
            final int retCode = pcap.nextEx(packet);
            
            if (retCode == -2) {
                running = false;
                break;
            }
            
            final StringBuilder protocolStack = new StringBuilder();
            
            if (packet.hasHeader(eth)) {
                protocolStack.append(eth.getName());
                
                displayInfo(eth.getName(), eth.typeDescription(), false);
            }

            if (packet.hasHeader(ip4)) {
                protocolStack.append("->").append(ip4.getName());
                
                final String value = String.format("%s -> %s", 
                        FormatUtils.ip(ip4.source()),
                        FormatUtils.ip(ip4.destination()));
                
                displayInfo(ip4.getName(), value, true);
            }
            
            if (packet.hasHeader(ip6)) {
                protocolStack.append("->").append(ip6.getName());
                
                displayInfo(ip6.getName(), ip6.getDescription(), false);
            }
            
            if (packet.hasHeader(icmp)) {
                protocolStack.append("->").append(icmp.getName());
                
                displayInfo(ip6.getName(), icmp.getDescription(), false);
            }
            
            if (packet.hasHeader(arp)) {
                protocolStack.append("->").append(arp.getName());
                
                displayInfo(ip6.getName(), arp.getDescription(), false);
            }
            
            if (packet.hasHeader(udp)) {
                protocolStack.append("->").append(udp.getName());
                
                displayInfo(tcp.getName(), udp.toString(), false);
            }
            
            if (packet.hasHeader(tcp)) {
                protocolStack.append("->").append(tcp.getName());
                
                final String value = String.format("%s -> %s", 
                        tcp.source(), 
                        tcp.destination());
                
                displayInfo(tcp.getName(), value, true);
            }
            
            if (packet.hasHeader(http)) {
                protocolStack.append("->").append(http.getName());
                
                displayInfo(http.getName(), http.toString(), false);
            }
            log.info(protocolStack.toString());
        }
    }

    private void displayInfo(final String label, final String value, final boolean displayed) {
        if (displayed) {
            log.info(String.format("%s: %s", label, value));
        }
    }
    
    public static void main(String[] args) {
        final TestApp app = new TestApp();
        app.run(args[0]);
    }
}
