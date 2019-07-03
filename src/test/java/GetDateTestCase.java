import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

public class GetDateTestCase extends TestCase {

	
	public GetDateTestCase(String name, String feedURL, String output) throws FactoryConfigurationError, IOException, XMLStreamException {
		super(name);
		if(feedURL == null) {
			input = null;
		} else {
			InputStream stream = getClass().getClassLoader().getResourceAsStream(feedURL);
			input = XMLInputFactory.newInstance().createXMLEventReader(stream);
		}
		if(output == null) {
			this.output = null;
		} else {
			this.output = LocalDate.parse(output);
		}
	}
	
}
