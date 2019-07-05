import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GetInactiveFeedsTestCase extends TestCase {
	
	public GetInactiveFeedsTestCase(String name, Map<String, List<String>> companyFeedMap, int days, List<String> output) {
		super(name);
		input = new LinkedList<Object>();
		((List<Object>)input).add(companyFeedMap);
		((List<Object>)input).add(days);
		if(output == null) {
			this.output = null;
		} else {
			this.output = output;
		}
	}
	
	public String toString() {
		return "[" + name + "], [" + input + "], [" + output + "]";
	}

}
