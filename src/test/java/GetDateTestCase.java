import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

public class GetDateTestCase extends TestCase {

	//private XMLEventReader input;
	//private LocalDate output;
	
	public GetDateTestCase(String name, String feedURL, String output) throws XMLStreamException, FactoryConfigurationError, IOException {
		super(name);
		//System.out.println(getClass().getClassLoader().getResource(""));
		if(feedURL == null) {
			input = null;
		} else {
			InputStream stream = getClass().getClassLoader().getResourceAsStream(feedURL);
			//System.out.println(stream.readAllBytes());
			//System.out.println("Read");
			input = XMLInputFactory.newInstance().createXMLEventReader(stream);
		}
		if(output == null) {
			this.output = null;
		} else {
			this.output = LocalDate.parse(output);
		}
	}
	
	public static void main(String[] args) throws XMLStreamException, FactoryConfigurationError, IOException {
		GetDateTestCase obj = new GetDateTestCase("one", "validInvalidfeed.xml", null);
		//System.out.println(obj.input.nextEvent());
		System.out.println(RSSFeedParser.getInstance().getDate((XMLEventReader)obj.input));
		URL url = new URL("validInvalidfeed.xml");
		System.out.println(url);
	}
	
}
