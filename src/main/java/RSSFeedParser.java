import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

public class RSSFeedParser implements Cloneable{

	private static RSSFeedParser parser;
	
	private RSSFeedParser() {};
	
	public static RSSFeedParser getInstance() {
		if(parser == null) {
			parser = new RSSFeedParser();
		}
		return parser;
	}
	
	@SuppressWarnings("restriction")
	public String getUpdateDate(String feedURL) throws XMLStreamException, FactoryConfigurationError {
		String updateDate = "";
		
		InputStream inputStream = connectToFeedServer(feedURL);
		
		XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(inputStream);
		
		while(eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();
			
			if(event.isStartElement()) {
				 String feedItem = event.asStartElement().getName().getLocalPart();
                 if(feedItem.toLowerCase().indexOf("date") > -1) {
                 	updateDate = getCharacterData(event, eventReader);
                 	if(updateDate.length() > 7) {
                 		return updateDate;
                 	}
                 }
			}
		}
		
		return updateDate;
		
	}
	
	private InputStream connectToFeedServer(String feedURL) {
		URL url;
		InputStream stream;
		try {
			url = new URL(feedURL);
            stream = url.openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
		return stream;
    }
	
	@SuppressWarnings("restriction")
	private String getCharacterData(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
        String result = "";
        event = eventReader.nextEvent();
        if (event instanceof Characters) {
            result = event.asCharacters().getData();
        }
        return result;
    }
	
	
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
}
