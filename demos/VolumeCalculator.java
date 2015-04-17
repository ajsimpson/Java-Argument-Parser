import edu.jsu.mcis.*;
import java.util.ArrayList;
import java.util.Arrays;

public class VolumeCalculator {

    public static void main(String[] args) {
		ArrayList<String> userInput = new ArrayList<>();
        ArgumentParser p = new ArgumentParser();
		userInput.addAll(Arrays.asList(args));
		
		p.addPositionalArgument("length");
        p.addPositionalArgument("width");
        p.addPositionalArgument("height");
		p.addPositionalArgumentDescription("length", "How long an object is.");
		p.addPositionalArgumentDescription("width", "How wide an object is.");
		p.addPositionalArgumentDescription("height", "How tall an object is.");
		
        p.addNamedArgument("color");
        p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
		p.addNamedArgumentDescription("color", "color's value is a string.");
		p.addNamedRestrictedValue("color", "blue");
		p.setRequired("color");
		p.createGroup("group1");
		p.createGroup("group2");
		p.addToGroup("group1", "color");
        p.addNamedArgument("age");
        p.addNamedArgumentValue("age", "22", Argument.Datatype.INTEGER);
		p.addNamedArgumentDescription("age", "age's value is an integer.");
        p.addNamedArgument("weight");
        p.addNamedArgumentValue("weight", "160.5", Argument.Datatype.FLOAT);
		p.addNamedArgumentDescription("weight", "weight's value is a float.");
		
		//DEMO
		System.out.println("\nArgument values before command line input:\n");
		
		System.out.println("color:");
		System.out.println("Value of color: " + p.getValue("color", 0));
		System.out.println("Is present: " + p.getFlag("color"));
		System.out.println("\nage:");
		System.out.println("Value of age: " + p.getValue("age", 0));
		System.out.println("Is present: " + p.getFlag("age"));
		System.out.println("\nweight:");
		System.out.println("Value of weight: " + p.getValue("weight", 0));
		System.out.println("Is present: " + p.getFlag("weight"));

		System.out.println("\nValue of length: " + p.getValue("length", 0));
		System.out.println("Value of width: " + p.getValue("width", 0));
		System.out.println("Value of height: " + p.getValue("height", 0));

		p.parse(userInput);

		System.out.println("\nArgument values after command line input:\n");
		
		System.out.println("color:");
		System.out.println("Value of color: " + p.getValue("color", 1));
		System.out.println("Is present: " + p.getFlag("color"));
		System.out.println("\nage:");
		System.out.println("Value of age: " + p.getValue("age", 1));
		System.out.println("Is present: " + p.getFlag("age"));
		System.out.println("\nweight:");
		System.out.println("Value of weight: " + p.getValue("weight", 1));
		System.out.println("Is present: " + p.getFlag("weight"));

		System.out.println("\nValue of length: " + p.getValue("length", 0));
		System.out.println("Value of width: " + p.getValue("width", 0));
		System.out.println("Value of height: " + p.getValue("height", 0));
		
		System.out.println("\nSaving to XML...");
		XMLEditor.saveToXML("ArgumentInformation.xml", p);
		System.out.println("\nLoading from XML...\n");
		XMLEditor.loadFromXML("ArgumentInformation.xml", p);
		//DEMO
		
        float length = Float.parseFloat(p.getPositionalArgumentValue("length", 0));
        float width =  Float.parseFloat(p.getPositionalArgumentValue("width", 0));
        float height = Float.parseFloat(p.getPositionalArgumentValue("height", 0));

        float volume = length * width * height;
        System.out.println("The volume is " + volume);
    }
}