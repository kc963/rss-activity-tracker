import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
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
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

@TestInstance(Lifecycle.PER_CLASS)
public class TestRSSFeedParser {

	final String testCaseFile = "RSSFeedParserTestCaseFile.txt";
	RSSFeedParser feedParser;
	Map<String, TestCase> testCases;

	@PrepareForTest(RSSFeedParser.class)
	@BeforeAll
	public void beforeAll() throws XMLStreamException, FactoryConfigurationError, IOException {
		testCases = new HashMap<String, TestCase>();
		loadTestCases();
		feedParser = Mockito.spy(RSSFeedParser.getInstance());
	}
	
	// GetLastUpdateDate Tests
	
	@Test
	void testGetLastUpdateDate_NullInput() throws XMLStreamException, FactoryConfigurationError, IOException {
		String testCaseIdentificationKey = "GetLastUpdateDate_NullOrder";
		String input = null;
		LocalDate expectedOutput = null;
		LocalDate actualOutput = feedParser.getLastUpdateDate(input);
		assertEquals(expectedOutput, actualOutput);
	}
	
	@Test
	void testGetLastUpdateDate_MixedValidInvalidInput() throws Exception {
		String testCaseIdentificationKey = "GetLastUpdateDate_MixedValidInvalidInput";
		String input = (String) testCases.get(testCaseIdentificationKey).getInput();
		LocalDate expectedOutput = (LocalDate)testCases.get(testCaseIdentificationKey).getOutput();
		InputStream testFileStream = this.getClass().getClassLoader().getResourceAsStream((String)input);
		Mockito.when(feedParser.connectToFeedServer(input)).thenReturn(testFileStream);
		LocalDate actualOutput = feedParser.getLastUpdateDate(input);
		assertEquals(expectedOutput, actualOutput);
	}
	
	@Test
	void testGetLastUpdateDate_InvalidInput() throws Exception {
		String testCaseIdentificationKey = "GetLastUpdateDate_InvalidInput";
		String input = (String) testCases.get(testCaseIdentificationKey).getInput();
		LocalDate expectedOutput = (LocalDate)testCases.get(testCaseIdentificationKey).getOutput();
		InputStream testFileStream = this.getClass().getClassLoader().getResourceAsStream((String)input);
        Mockito.when(feedParser.connectToFeedServer(input)).thenReturn(testFileStream);
		LocalDate actualOutput = feedParser.getLastUpdateDate(input);
		assertEquals(expectedOutput, actualOutput);
	}
	
	@Test
	void testGetLastUpdateDate_ValidInput() throws Exception {
		String testCaseIdentificationKey = "GetLastUpdateDate_ValidInput";
		String input = (String) testCases.get(testCaseIdentificationKey).getInput();
		LocalDate expectedOutput = (LocalDate)testCases.get(testCaseIdentificationKey).getOutput();
		InputStream testFileStream = this.getClass().getClassLoader().getResourceAsStream((String)input);
        Mockito.when(feedParser.connectToFeedServer(input)).thenReturn(testFileStream);
		LocalDate actualOutput = feedParser.getLastUpdateDate(input);
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
				testCases.put("GetLastUpdateDate_" + testCaseName, new GetLastUpdateDateTestCase(testCaseName, feedURL, output));
			}
		}
	}
	
	
}
