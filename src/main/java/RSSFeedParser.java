import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

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

	@SuppressWarnings("restriction")
	public LocalDate getUpdateDate(String feedURL) throws XMLStreamException, FactoryConfigurationError {
		InputStream feedStream = connectToFeedServer(feedURL);
		if (feedStream == null) {
			return null;
		}
		XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(feedStream);
		LocalDate date = getDate(eventReader);
		if (date == null) {
			System.out.println(
					"ERROR : The Date Format in the feed is not supported by this program. Please refer to the Readme.");
		}
		return date;
	}

	private InputStream connectToFeedServer(String feedURL) {
		InputStream stream = null;
		try {
			URL url = new URL(feedURL);
			stream = url.openStream();
		} catch (IOException e) {
			System.out.println("ERROR : Couldn't connect to the feed server.");
		}
		return stream;
	}

	private LocalDate getDate(XMLEventReader eventReader) throws XMLStreamException {
		LocalDate date = null;
		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();
			if (event.isStartElement()) {
				String feedItem = event.asStartElement().getName().getLocalPart();
				if (feedItem.toLowerCase().indexOf("date") > -1) {
					try {
						date = parseDate(getDateString(event, eventReader));
					} catch (Exception e) {
						continue;
					}
					return date;
				}
			}
		}
		return date;
	}

	@SuppressWarnings("restriction")
	private String getDateString(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
		String dateString = "";
		event = eventReader.nextEvent();
		if (event instanceof Characters) {
			dateString = event.asCharacters().getData();
		}
		return dateString;
	}

	/**
	 * @param dateString
	 * @return LocalDate object. (YYYY-MM-DD)
	 * @description Parses 2 date formats: (1) Day, DD MMM YYYY (2) YYYY-MM-DD and
	 *              generates an object.
	 */
	private LocalDate parseDate(String dateString) {
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
		LocalDate date = LocalDate.parse(dateString);
		return date;
	}

	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

}
