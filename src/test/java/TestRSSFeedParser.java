import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLEventReader;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class TestRSSFeedParser {

	final String testCaseFile = "TestCaseFile.txt";
	RSSFeedParser feedParser;
	Map<String, TestCase> testCases;
	
	@BeforeAll
	public void beforeAll() throws XMLStreamException, FactoryConfigurationError, IOException {
		feedParser = RSSFeedParser.getInstance();
		testCases = new HashMap<String, TestCase>();
		loadTestCases();
	}
	
	@Test
	void testGetDate_NullInput() throws XMLStreamException {
		String testCaseIdentificationKey = "NullOrder";
		XMLEventReader input = null;
		LocalDate expectedOutput = null;
		LocalDate actualOutput = feedParser.getDate(input);
		assertEquals(expectedOutput, actualOutput);
	}
	
	@Test
	void testGetDate_MixedValidInvalidInput() throws XMLStreamException {
		String testCaseIdentificationKey = "MixedValidInvalidInput";
		XMLEventReader input = (XMLEventReader) testCases.get(testCaseIdentificationKey).getInput();
		LocalDate expectedOutput = (LocalDate)testCases.get(testCaseIdentificationKey).getOutput();
		LocalDate actualOutput = feedParser.getDate(input);
		assertEquals(expectedOutput, actualOutput);
	}
	
	@Test
	void testGetDate_InvalidInput() throws XMLStreamException {
		String testCaseIdentificationKey = "InvalidInput";
		XMLEventReader input = (XMLEventReader) testCases.get(testCaseIdentificationKey).getInput();
		LocalDate expectedOutput = (LocalDate)testCases.get(testCaseIdentificationKey).getOutput();
		LocalDate actualOutput = feedParser.getDate(input);
		assertEquals(expectedOutput, actualOutput);
	}
	
	@Test
	void testGetDate_ValidInput() throws XMLStreamException {
		String testCaseIdentificationKey = "ValidInput";
		XMLEventReader input = (XMLEventReader) testCases.get(testCaseIdentificationKey).getInput();
		LocalDate expectedOutput = (LocalDate)testCases.get(testCaseIdentificationKey).getOutput();
		LocalDate actualOutput = feedParser.getDate(input);
		assertEquals(expectedOutput, actualOutput);
	}
	
	private void loadTestCases() throws XMLStreamException, FactoryConfigurationError, IOException {
		Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream(testCaseFile));
		while(scanner.hasNext()) {
			String line = scanner.nextLine();
			char firstCharacter = line.charAt(0);
			if(firstCharacter == '#') {
				String testCaseName = line.substring(1);
				String feedURL = null;
				String output = null;
				do {
					line = scanner.nextLine();
					firstCharacter = line.charAt(0);
					switch(firstCharacter) {
						case 'I': feedURL = line.substring(6);
							break;
						case 'O': output = line.substring(7);
					}
				} while(scanner.hasNext() && firstCharacter != '$');
				testCases.put(testCaseName, new GetDateTestCase(testCaseName, feedURL, output));
			}
		}
	}
	
	
}
