import java.time.LocalDate;

public class GetLastUpdateDateTestCase extends TestCase {

	public GetLastUpdateDateTestCase(String name, String feedURL, String output) {
		super(name);
		this.input = feedURL;
		if(output == null) {
			this.output = null;
		} else {
			this.output = LocalDate.parse(output);
		}
	}
	
}
