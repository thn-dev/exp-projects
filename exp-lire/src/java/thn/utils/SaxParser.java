package thn.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxParser
{
    private final SAXParser parser;

    public SaxParser() throws ParserConfigurationException, SAXException {
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        parser = parserFactor.newSAXParser();        
    }
    
    public void parse(final File xmlFile, final DefaultHandler handler) 
        throws IllegalArgumentException, SAXException, IOException {
        parser.parse(xmlFile, handler);
    }

    public void parse(final String xmlData, final DefaultHandler handler) 
        throws IllegalArgumentException, SAXException, IOException{
        final InputSource inputSource = new InputSource( new StringReader( xmlData ) );
        parser.parse(inputSource, handler);
    }
}
