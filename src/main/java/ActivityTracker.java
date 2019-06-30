import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

public class ActivityTracker {

	private static List<String> getInactiveFeeds(HashMap<String, List<String>> companyFeedMap, int days) throws XMLStreamException, javax.xml.stream.FactoryConfigurationError {
		List<String> inactiveFeeds = new LinkedList<String>();
		
		for(Map.Entry<String, List<String>> entry: companyFeedMap.entrySet()) {
			for(String feedURL : entry.getValue()) {
				LocalDate feedDate = RSSFeedParser.getInstance().getUpdateDate(feedURL);
				if(feedDate == null) {
					continue;
				}
				LocalDate currentDate = LocalDate.now();
				if( currentDate.minusDays(days).compareTo(feedDate) > 0) {
					inactiveFeeds.add(entry.getKey());
				}
			}
		}
		
		return inactiveFeeds;
	}
	
	@SuppressWarnings("restriction")
	public static void main(String[] args) throws FactoryConfigurationError, XMLStreamException, javax.xml.stream.FactoryConfigurationError {
		// TODO Auto-generated method stub

		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("BBC", new LinkedList<String>(Arrays.asList( "http://feeds.bbci.co.uk/news/rss.xml?edition=us", "http://feeds.bbci.co.uk/news/rss.xml?edition=uk", "http://feeds.bbci.co.uk/news/rss.xml?edition=int" )));
		map.put("Real Time with Bill Maher", new LinkedList<String>(Arrays.asList( "http://billmaher.hbo.libsynpro.com/rss" )));
		map.put("Bill Simmons Podcast", new LinkedList<String>(Arrays.asList( "https://rss.art19.com/the-bill-simmons-podcast" )));
		map.put("Craigslist", new LinkedList<String>(Arrays.asList( "https://www.craigslist.org/about/best/all/index.rss" )));
		map.put("ESPN", new LinkedList<String>(Arrays.asList( "https://www.espn.com/espn/rss/news", "https://www.espn.com/espn/rss/nfl/news", "https://www.espn.com/espn/rss/nba/news" )));
		
		System.out.println(getInactiveFeeds(map, 1));
		
	}

}
