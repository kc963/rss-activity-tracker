import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public final class GenerateFeeds {

	static void generate() throws ParserConfigurationException, TransformerException {

		LocalDate date = LocalDate.now();
		//Generating Feed11
		generateXMLFeed("11",date.minusDays(20));
		//Generating Feed12
		generateXMLFeed("12",date.minusDays(25));
		//Generating Feed13
		generateXMLFeed("13",date.minusDays(30));
		//Feed 21
		generateXMLFeed("21",date.minusDays(2));
		//Feed31
		generateXMLFeed("31",date);
		//Feed32
		generateXMLFeed("32",date.minusDays(10));
		//Feed41
		generateXMLFeed("41",date);
		//Feed42
		generateXMLFeed("42",date.minusDays(18));
		
	}

	private static void generateXMLFeed(String fileName, LocalDate date) throws ParserConfigurationException, TransformerException {
		String rootPath = System.getProperty("user.dir") + "\\target\\test-classes\\" ;
		
		fileName = rootPath + "Feed" + fileName + ".xml";

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// feed
		Document doc = docBuilder.newDocument();
		Element feed = doc.createElement("feed");
		doc.appendChild(feed);
		
		//item
		Element item = doc.createElement("item");
		feed.appendChild(item);
		
		//pub-date
		Element pubDate = doc.createElement("pub-date");
		pubDate.appendChild(doc.createTextNode(date.toString()));
		item.appendChild(pubDate);
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(fileName));
		
		transformer.transform(source, result);
		
		System.out.println(fileName + " generated.");		
	}

	public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException, URISyntaxException {
		String rootPath = System.getProperty("user.dir") + "\\target\\test-classes\\" ;
		
		generate();
		
		File f = new File(rootPath + "Feed11.xml");		
		InputStream stream = new FileInputStream(f);
		System.out.println(new String(stream.readAllBytes(), StandardCharsets.UTF_8));
		
		f = new File(rootPath + "Feed21.xml");
		stream = new FileInputStream(f);
		System.out.println(new String(stream.readAllBytes(), StandardCharsets.UTF_8));
		
		f = new File(rootPath + "Feed31.xml");
		stream = new FileInputStream(f);
		System.out.println(new String(stream.readAllBytes(), StandardCharsets.UTF_8));
		
		f = new File(rootPath + "Feed41.xml");
		stream = new FileInputStream(f);
		System.out.println(new String(stream.readAllBytes(), StandardCharsets.UTF_8));
		
	}
	
}
