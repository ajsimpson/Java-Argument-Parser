import edu.jsu.mcis.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ArgumentParserKeywords {

	ArgumentParser p = new ArgumentParser();
	
	public void startProgramWithArguments(String[] args) {
		ArrayList<String> userInput = new ArrayList<>();
		userInput.addAll(Arrays.asList(args));
		p.parse(userInput);
	}
	
	public void startFirstTest(String[] args) {
		ArrayList<String> userInput = new ArrayList<>();
		userInput.addAll(Arrays.asList(args));
		p.addPositionalArgument("length");
		p.addPositionalArgument("width");
		p.addPositionalArgument("height");
		p.parse(userInput);
	}
	
	public void startSecondTest(String[] args) {
		ArrayList<String> userInput = new ArrayList<>();
		userInput.addAll(Arrays.asList(args));
		p.addPositionalArgument("pet");
		p.addPositionalArgument("number");
		p.addPositionalArgument("rainy");
		p.addPositionalArgument("bathrooms");
		p.parse(userInput);
	}
	
	public String getLength() {
		return p.getPositionalArgumentValue("length");
	}
	
	public String getWidth() {
		return p.getPositionalArgumentValue("width");
	}
	
	public String getHeight() {
		return p.getPositionalArgumentValue("height");
	}
	
	public String getPet() {
		return p.getPositionalArgumentValue("pet");
	}
	
	public String getNumber() {
		return p.getPositionalArgumentValue("number");
	}
	
	public String getRainy() {
		return p.getPositionalArgumentValue("rainy");
	}
	
	public String getBathrooms() {
		return p.getPositionalArgumentValue("bathrooms");
	}
}