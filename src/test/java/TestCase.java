
public abstract class TestCase {

	protected String name;
	public Object input;
	public Object output;
	
	public TestCase(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Object getInput() {
		return input;
	}
	
	public Object getOutput() {
		return output;
	}
	
}
