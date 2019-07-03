import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

public class ActivityTracker {

	private List<String> getInactiveFeeds(HashMap<String, List<String>> companyFeedMap, int days) throws XMLStreamException, javax.xml.stream.FactoryConfigurationError {
		List<String> inactiveFeeds = new LinkedList<String>();
		
		for(Map.Entry<String, List<String>> entry: companyFeedMap.entrySet()) {
			boolean inactiveCompany = true;
			for(String feedURL : entry.getValue()) {
				LocalDate feedDate = RSSFeedParser.getInstance().getLastUpdateDate(feedURL);
				if(feedDate == null) {
					continue;
				}
				LocalDate currentDate = LocalDate.now();
				System.out.println("[ Feed Date : " + feedDate + " ]  [ Today's Date : " + currentDate + " ]  [ Days Limit : " + days + " ]");
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
	
	@SuppressWarnings("restriction")
	public static void main(String[] args) throws FactoryConfigurationError, XMLStreamException, javax.xml.stream.FactoryConfigurationError {
		// TODO Auto-generated method stub

		ActivityTracker tracker = new ActivityTracker();
		
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		//map.put("BBC", new LinkedList<String>(Arrays.asList( "http://feeds.bbci.co.uk/news/rss.xml?edition=us", "http://feeds.bbci.co.uk/news/rss.xml?edition=uk", "http://feeds.bbci.co.uk/news/rss.xml?edition=int" )));
		//map.put("Real Time with Bill Maher", new LinkedList<String>(Arrays.asList( "http://billmaher.hbo.libsynpro.com/rss" )));
		//map.put("Bill Simmons Podcast", new LinkedList<String>(Arrays.asList( "https://rss.art19.com/the-bill-simmons-podcast" )));
		//map.put("Craigslist", new LinkedList<String>(Arrays.asList( "https://www.craigslist.org/about/best/all/index.rss" )));
		//map.put("ESPN", new LinkedList<String>(Arrays.asList( "https://www.espn.com/espn/rss/news", "https://www.espn.com/espn/rss/nfl/news", "https://www.espn.com/espn/rss/nba/news" )));
		map.put("KC", new LinkedList<String>(Arrays.asList("https://drive.google.com/open?id=1lFJKOGKh-rPivR352bj1taWOHQtF61tx")));
		
		List<String> inactiveFeeds = tracker.getInactiveFeeds(map, 1);
		
		System.out.println("Inactive feeds: " + inactiveFeeds);
		
	}

}
