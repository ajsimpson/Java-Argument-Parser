import edu.jsu.mcis.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ArgumentParserKeywords {

	ArgumentParser p = new ArgumentParser();
	
	public void startFirstTest() {
		ArrayList<String> input = new ArrayList<>(Arrays.asList("7", "5", "2"));
		p.addPositionalArgument("length");
		p.addPositionalArgument("width");
		p.addPositionalArgument("height");
		p.parse(input);
	}
	
	public void startSecondTest() {
		ArrayList<String> input = new ArrayList<>(Arrays.asList("dog", "2", "true", "3.5"));
		p.addPositionalArgument("pet");
		p.addPositionalArgument("number");
		p.addPositionalArgument("rainy");
		p.addPositionalArgument("bathrooms");
		p.parse(input);
	}
	
    public String getLengths() {
        return Integer.toString((int) p.getValue("length", 0));
    }

    public String getWidths() {
        return Integer.toString((int) p.getValue("width", 0));
    }

    public String getHeights() {
        return Integer.toString((int) p.getValue("height", 0));
    }
    
    public String getPet() {
        return p.getValue("pet", 0);
    }
    
    public String getNumber() {
        return Integer.toString((int) p.getValue("number", 0));
    }
    
    public String getRainy() {
        return Boolean.toString((boolean) p.getValue("rainy", 0));
    }
    
    public String getBathrooms() {
        return Float.toString((float) p.getValue("bathrooms", 0));
    }
}