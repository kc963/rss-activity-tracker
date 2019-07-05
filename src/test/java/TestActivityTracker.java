import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;

@TestInstance(Lifecycle.PER_CLASS)
public class TestActivityTracker {

	final String testCaseFile = "ActivityTrackerTestCaseFile.txt";
	ActivityTracker tracker;
	Map<String, TestCase> testCases;
	Map<String, LocalDate> feedToDateMap;
	RSSFeedParser feedParser;
	
	@BeforeAll
	public void beforeAll() throws ParserConfigurationException, TransformerException, XMLStreamException, FactoryConfigurationError, FileNotFoundException {
		testCases = new HashMap<String, TestCase>();
		feedToDateMap = new HashMap<String, LocalDate>();
		GenerateFeeds.generate();
		feedParser = Mockito.spy(RSSFeedParser.getInstance());
		tracker = Mockito.spy(new ActivityTracker());
		loadTestCases();
		
	}
	
	@Test
	public void testGetInactiveFeeds_DaysInFuture() throws XMLStreamException, FactoryConfigurationError {
		String testCaseIdentificationKey = "GetInactiveFeeds_DaysInFuture";
		Map<String, List<String>> inputMap = (Map<String, List<String>>) ((List<Object>)testCases.get(testCaseIdentificationKey).getInput()).get(0);
		int inputDays = (Integer)((List<Object>)testCases.get(testCaseIdentificationKey).getInput()).get(1);
		List<String> expectedOutput = (List<String>)testCases.get(testCaseIdentificationKey).getOutput();
		List<String> actualOutput = tracker.getInactiveFeeds(inputMap, inputDays);
		assertTrue(equalLists(expectedOutput, actualOutput));
	}
	
	@Test
	public void testGetInactiveFeeds_SameDay() throws XMLStreamException {
		String testCaseIdentificationKey = "GetInactiveFeeds_SameDay";
		Map<String, List<String>> inputMap = (Map<String, List<String>>) ((List<Object>)testCases.get(testCaseIdentificationKey).getInput()).get(0);
		int inputDays = (Integer)((List<Object>)testCases.get(testCaseIdentificationKey).getInput()).get(1);
		List<String> expectedOutput = (List<String>)testCases.get(testCaseIdentificationKey).getOutput();
		List<String> actualOutput = tracker.getInactiveFeeds(inputMap, inputDays);
		assertTrue(equalLists(expectedOutput, actualOutput));
	}
	
	@Test
	public void testGetInactiveFeeds_EarlierDay() throws XMLStreamException {
		String testCaseIdentificationKey = "GetInactiveFeeds_EarlierDay";
		Map<String, List<String>> inputMap = (Map<String, List<String>>) ((List<Object>)testCases.get(testCaseIdentificationKey).getInput()).get(0);
		int inputDays = (Integer)((List<Object>)testCases.get(testCaseIdentificationKey).getInput()).get(1);
		List<String> expectedOutput = (List<String>)testCases.get(testCaseIdentificationKey).getOutput();
		List<String> actualOutput = tracker.getInactiveFeeds(inputMap, inputDays);
		assertTrue(equalLists(expectedOutput, actualOutput));
	}
	
	@Test
	public void testGetInactiveFeeds_NullMap() throws XMLStreamException, FactoryConfigurationError {
		String testCaseIdentificationKey = "GetInactiveFeeds_NullMap";
		Map<String, List<String>> inputMap = (Map<String, List<String>>) ((List<Object>)testCases.get(testCaseIdentificationKey).getInput()).get(0);
		int inputDays = (Integer)((List<Object>)testCases.get(testCaseIdentificationKey).getInput()).get(1);
		List<String> expectedOutput = (List<String>)testCases.get(testCaseIdentificationKey).getOutput();
		List<String> actualOutput = tracker.getInactiveFeeds(inputMap, inputDays);
		assertTrue(equalLists(expectedOutput, actualOutput));
	}
	
	@Test
	public void testGetInactiveFeeds_EmptyMap() throws XMLStreamException, FactoryConfigurationError {
		String testCaseIdentificationKey = "GetInactiveFeeds_EmptyMap";
		Map<String, List<String>> inputMap = (Map<String, List<String>>) ((List<Object>)testCases.get(testCaseIdentificationKey).getInput()).get(0);
		int inputDays = (Integer)((List<Object>)testCases.get(testCaseIdentificationKey).getInput()).get(1);
		List<String> expectedOutput = (List<String>)testCases.get(testCaseIdentificationKey).getOutput();
		List<String> actualOutput = tracker.getInactiveFeeds(inputMap, inputDays);
		assertTrue(equalLists(expectedOutput, actualOutput));
	}
	
	@Test
	public void testGetInactiveFeeds_SingleCompany() throws XMLStreamException, FactoryConfigurationError {
		String testCaseIdentificationKey = "GetInactiveFeeds_SingleCompany";
		Map<String, List<String>> inputMap = (Map<String, List<String>>) ((List<Object>)testCases.get(testCaseIdentificationKey).getInput()).get(0);
		int inputDays = (Integer)((List<Object>)testCases.get(testCaseIdentificationKey).getInput()).get(1);
		List<String> expectedOutput = (List<String>)testCases.get(testCaseIdentificationKey).getOutput();
		List<String> actualOutput = tracker.getInactiveFeeds(inputMap, inputDays);
		assertTrue(equalLists(expectedOutput, actualOutput));
	}
	
	@Test
	public void testGetInactiveFeeds_SingleCompanyAndNoFeedURL() throws XMLStreamException, FactoryConfigurationError {
		String testCaseIdentificationKey = "GetInactiveFeeds_SingleCompanyAndNoFeedURL";
		Map<String, List<String>> inputMap = (Map<String, List<String>>) ((List<Object>)testCases.get(testCaseIdentificationKey).getInput()).get(0);
		int inputDays = (Integer)((List<Object>)testCases.get(testCaseIdentificationKey).getInput()).get(1);
		List<String> expectedOutput = (List<String>)testCases.get(testCaseIdentificationKey).getOutput();
		List<String> actualOutput = tracker.getInactiveFeeds(inputMap, inputDays);
		assertTrue(equalLists(expectedOutput, actualOutput));
	}
	
	@Test
	public void testGetInactiveFeeds_MultipleCompanies() throws XMLStreamException, FactoryConfigurationError {
		String testCaseIdentificationKey = "GetInactiveFeeds_MultipleCompanies";
		Map<String, List<String>> inputMap = (Map<String, List<String>>) ((List<Object>)testCases.get(testCaseIdentificationKey).getInput()).get(0);
		int inputDays = (Integer)((List<Object>)testCases.get(testCaseIdentificationKey).getInput()).get(1);
		List<String> expectedOutput = (List<String>)testCases.get(testCaseIdentificationKey).getOutput();
		List<String> actualOutput = tracker.getInactiveFeeds(inputMap, inputDays);
		assertTrue(equalLists(expectedOutput, actualOutput));
	}
	
	@Test
	public void testGetInactiveFeeds_MultipleCompaniesAndOneWithNoFeedURL() throws XMLStreamException, FactoryConfigurationError {
		String testCaseIdentificationKey = "GetInactiveFeeds_MultipleCompaniesAndOneWithNoFeedURL";
		Map<String, List<String>> inputMap = (Map<String, List<String>>) ((List<Object>)testCases.get(testCaseIdentificationKey).getInput()).get(0);
		int inputDays = (Integer)((List<Object>)testCases.get(testCaseIdentificationKey).getInput()).get(1);
		List<String> expectedOutput = (List<String>)testCases.get(testCaseIdentificationKey).getOutput();
		List<String> actualOutput = tracker.getInactiveFeeds(inputMap, inputDays);
		assertTrue(equalLists(expectedOutput, actualOutput));
	}
	
	@Test
	public void testGetInactiveFeeds_MultipleCompaniesAndMultipleFeedURL() throws XMLStreamException, FactoryConfigurationError {
		String testCaseIdentificationKey = "GetInactiveFeeds_MultipleCompaniesAndMultipleFeedURL";
		Map<String, List<String>> inputMap = (Map<String, List<String>>) ((List<Object>)testCases.get(testCaseIdentificationKey).getInput()).get(0);
		int inputDays = (Integer)((List<Object>)testCases.get(testCaseIdentificationKey).getInput()).get(1);
		List<String> expectedOutput = (List<String>)testCases.get(testCaseIdentificationKey).getOutput();
		List<String> actualOutput = tracker.getInactiveFeeds(inputMap, inputDays);
		assertTrue(equalLists(expectedOutput, actualOutput));
	}

	private void loadTestCases() throws XMLStreamException, FactoryConfigurationError, FileNotFoundException {
		Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream(testCaseFile));
		String rootPath = System.getProperty("user.dir") + "\\target\\test-classes\\" ;
		while(scanner.hasNext()) {
			String line = scanner.nextLine();
			char firstCharacter = line.charAt(0);
			if(firstCharacter == '#') {
				String testCaseName = line.substring(1);
				Map<String, List<String>> inputMap = null;
				int inputDays = -100;
				List<String> output = new LinkedList<String>();
				do {
					line = scanner.nextLine();
					firstCharacter = line.charAt(0);
					switch(firstCharacter) {
						case 'M': if(inputMap == null) {
								inputMap = new HashMap<String, List<String>>();
							}
							String inputs[] = line.split(":");
							if(inputs.length == 1) {
								break;
							}
							List<String> feeds = new LinkedList<String>();
							for(int i=2; i<inputs.length; i++) {
								feeds.add(inputs[i]);
								String filePath = rootPath + inputs[i] + ".xml";
								File feedFile = new File(filePath);
								InputStream testFileStream = new FileInputStream(feedFile);
								Mockito.when(feedParser.connectToFeedServer(inputs[i])).thenReturn(testFileStream);
								LocalDate date = feedParser.getLastUpdateDate(inputs[i]);
								//Mockito.doReturn(testFileStream).when(feedParser.connectToFeedServer(inputs[i]));
								//Mockito.doReturn(feedParser.getLastUpdateDate(inputs[i])).when(date);
								Mockito.when(tracker.getUpdateDate(inputs[i])).thenReturn(date);
							}
							inputMap.put(inputs[1], feeds);
							break;
						case 'D': inputDays = Integer.parseInt(line.substring(5));
							break;
						case 'O': output.add(line.substring(7));
							break;
					}
				} while(scanner.hasNext() && firstCharacter != '$');
				testCases.put("GetInactiveFeeds_" + testCaseName, new GetInactiveFeedsTestCase(testCaseName, inputMap, inputDays, output));
			}
		}
	}
	
	private boolean equalLists(List<String> a, List<String> b) {
		if(a==null && b==null) {
			return true;
		}
		Collections.sort(a);
		Collections.sort(b);
		return a.equals(b);
	}
	
}
