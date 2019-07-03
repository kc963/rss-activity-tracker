import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.stream.Stream;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

/**
 * @author Kapil
 */
public class RSSFeedParser implements Cloneable {

	private static RSSFeedParser parser;
	private String[] monthNames;

	private RSSFeedParser() {
		monthNames = new String[] { "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov",
				"dec" };
	}

	public static RSSFeedParser getInstance() {
		if (parser == null) {
			parser = new RSSFeedParser();
		}
		return parser;
	}

	/**
	 * @param feedURL
	 * @return LocalDate object containing the publication date present in the feed. Returns null if valid date not found.
	 * @throws XMLStreamException
	 * @throws FactoryConfigurationError
	 * @description Processing the feed URL and connects to the feed server. After that, processing the feed to find a valid publication date. Shows appropriate error message if no valid date found.
	 */
	@SuppressWarnings("restriction")
	public LocalDate getLastUpdateDate(String feedURL) throws XMLStreamException, FactoryConfigurationError {
		InputStream feedStream = connectToFeedServer(feedURL);
		if (feedStream == null) {
			return null;
		}
		XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(feedStream);
		LocalDate date = getDate(eventReader);
		disconnectFromFeedServer(feedStream);
		if (date == null) {
			System.out.println(
					"ERROR : The Date Format in the feed is not supported by this program. Please refer to the Readme.");
		}
		return date;
	}

	/**
	 * @param feedURL
	 * @return InputStream from the feedServer
	 * @description Establishes a connection to the feed server and returns the stream for accessing feed data.
	 */
	public InputStream connectToFeedServer(String feedURL) {
		InputStream stream = null;
		try {
			URL url = new URL(feedURL);
			stream = url.openStream();
		} catch (IOException e) {
			System.out.println("ERROR : Couldn't connect to the feed server.");
		}
		return stream;
	}

	/**
	 * @param eventReader
	 * @return LocalDate object
	 * @throws XMLStreamException
	 * @description Iterates over the entire feed until it finds a valid publication date. Returns null if valid date is not found.
	 */
	public LocalDate getDate(XMLEventReader eventReader) throws XMLStreamException {
		LocalDate date = null;
		if(eventReader == null) {
			return date;
		}
		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();
			//System.out.println(event);
			if (event.isStartElement()) {
				String feedItem = event.asStartElement().getName().getLocalPart();
				if (feedItem.toLowerCase().indexOf("date") > -1) {
					try {
						date = parseDate(getDateString(eventReader.nextEvent()));
					} catch (Exception e) {
						continue;
					}
					return date;
				}
			}
		}
		return date;
	}

	/**
	 * @param event
	 * @param eventReader
	 * @return String containing publication date.
	 * @throws XMLStreamException
	 * @description Extracts the character string between the xml opening and closing tag and returns it.
	 */
	@SuppressWarnings("restriction")
	private String getDateString(XMLEvent event) throws XMLStreamException {
		String dateString = "";
		if (event instanceof Characters) {
			dateString = event.asCharacters().getData();
		}
		//System.out.println(dateString);
		return dateString;
	}

	/**
	 * @param dateString
	 * @return LocalDate object. (YYYY-MM-DD)
	 * @throws Exception 
	 * @description Parses 2 date formats: (1) Day, DD MMM YYYY (2) YYYY-MM-DD and
	 *              generates an object.
	 */
	private LocalDate parseDate(String dateString) throws Exception {
		String DD = "", MM = "", YYYY = "";
		if (dateString.indexOf(',') < 5 && dateString.indexOf(',') > -1) {
			// process format "Day, DD MON YYYY" (Example : Sat, 10 Jun 2019)
			DD = dateString.substring(5, 7);
			MM = dateString.substring(8, 11).toLowerCase();
			for (int i = 0; i < monthNames.length; i++) {
				MM = monthNames[i].equals(MM) ? "" + (i + 1) : MM;
			}
			if (MM.length() < 2) {
				MM = "0" + MM;
			}
			YYYY = dateString.substring(12, 16);
		} else if (dateString.indexOf('-') > -1) {
			// process format "YYYY-MM-DD" (Example : 2019-05-31)
			DD = dateString.substring(8, 10);
			MM = dateString.substring(5, 7);
			YYYY = dateString.substring(0, 4);
		}
		dateString = YYYY + "-" + MM + "-" + DD;
		LocalDate date = null;
		try {
			date = LocalDate.parse(dateString);
		} catch(Exception e) {
			throw e;
		}
		return date;
	}
	
	/**
	 * @param feedStream
	 * @description Closes the connection with the feed server by closing the stream.
	 */
	private void disconnectFromFeedServer(InputStream feedStream) {
		try {
			feedStream.close();
		} catch (IOException e) {}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 * @purpose Prevent cloning of objects.
	 */
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

}
