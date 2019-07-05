import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

public class ActivityTracker {

	public List<String> getInactiveFeeds(Map<String, List<String>> companyFeedMap, int days) throws XMLStreamException, javax.xml.stream.FactoryConfigurationError {
		List<String> inactiveFeeds = new LinkedList<String>();
		if(days < 0) {
			System.out.println("ERROR: Number of Days cannot be less than 0.");
			return inactiveFeeds;
		}
		
		if(companyFeedMap == null) {
			return inactiveFeeds;
		}
		
		for(Map.Entry<String, List<String>> entry: companyFeedMap.entrySet()) {
			boolean inactiveCompany = true;
			for(String feedURL : entry.getValue()) {
				LocalDate feedDate = getUpdateDate(feedURL);
				if(feedDate == null) {
					continue;
				}
				LocalDate currentDate = LocalDate.now();
				if( currentDate.minusDays(days).compareTo(feedDate) <= 0) {
					inactiveCompany = false;
				}
			}
			if(inactiveCompany) {
				inactiveFeeds.add(entry.getKey());
			}
		}
		
		return inactiveFeeds;
	}
	
	public LocalDate getUpdateDate(String feedURL) throws XMLStreamException, javax.xml.stream.FactoryConfigurationError {
		return RSSFeedParser.getInstance().getLastUpdateDate(feedURL);
	}
	
	@SuppressWarnings("restriction")
	public static void main(String[] args) throws FactoryConfigurationError, XMLStreamException, javax.xml.stream.FactoryConfigurationError {
		ActivityTracker tracker = new ActivityTracker();
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> bbcFeed = Arrays.asList("http://feeds.bbci.co.uk/news/rss.xml?edition=us");
		map.put("BBC", bbcFeed);
		List<String> craigslistFeed = Arrays.asList("https://www.craigslist.org/about/best/all/index.rss");
		map.put("Craigslist", craigslistFeed);
		List<String> inactiveFeeds = tracker.getInactiveFeeds(map, 10);
		System.out.println("Inactive feeds: " + inactiveFeeds);
		
	}

}
