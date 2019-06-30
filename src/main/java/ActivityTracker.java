import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

public class ActivityTracker {

	@SuppressWarnings("restriction")
	public static void main(String[] args) throws FactoryConfigurationError, XMLStreamException, javax.xml.stream.FactoryConfigurationError {
		// TODO Auto-generated method stub

		String feedURL = 
				//"https://www.bbc.com/news/10628494";
				//"https://www.craigslist.org/about/rss";
				"https://www.craigslist.org/about/best/all/index.rss";
				//"https://www.espn.com/espn/rss/news";
		//String feedURL = "https://www.vogella.com/article.rss";
		System.out.println(RSSFeedParser.getInstance().getUpdateDate(feedURL));
		
	}

}
