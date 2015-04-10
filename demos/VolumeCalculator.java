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
		p.addChoice("color", "blue");
        p.addNamedArgument("age");
        p.addNamedArgumentValue("age", "22", Argument.Datatype.INTEGER);
		p.addNamedArgumentDescription("age", "age's value is an integer.");
        p.addNamedArgument("weight");
        p.addNamedArgumentValue("weight", "160.5", Argument.Datatype.FLOAT);
		p.addNamedArgumentDescription("weight", "weight's value is a float.");
		
		/////////////////////////////////////////////////////////////////////////////////////////
		
		System.out.println("\nArgument values before command line input:\n");
		
		System.out.println("color:");
		System.out.println("Value of color: " + p.getNamedArgument("color"));
		System.out.println("Is present: " + p.getFlag("color"));
		System.out.println("\nage:");
		System.out.println("Value of age: " + p.getNamedArgument("age"));
		System.out.println("Is present: " + p.getFlag("age"));
		System.out.println("\nweight:");
		System.out.println("Value of weight: " + p.getNamedArgument("weight")+"");
		System.out.println("Is present: " + p.getFlag("weight"));

		System.out.println("\nValue of length: " + p.getPositionalArgument("length"));
		System.out.println("Value of width: " + p.getPositionalArgument("width"));
		System.out.println("Value of height: " + p.getPositionalArgument("height"));

		p.parse(userInput);

		System.out.println("\nArgument values after command line input:\n");
		
		System.out.println("color:");
		System.out.println("Value of color: " + p.getNamedArgument("color"));
		System.out.println("Is present: " + p.getFlag("color"));
		System.out.println("\nage:");
		System.out.println("Value of age: " + p.getNamedArgument("age"));
		System.out.println("Is present: " + p.getFlag("age"));
		System.out.println("\nweight:");
		System.out.println("Value of weight: " + p.getNamedArgument("weight"));
		System.out.println("Is present: " + p.getFlag("weight"));

		System.out.println("\nValue of length: " + p.getPositionalArgumentValue("length"));
		System.out.println("Value of width: " + p.getPositionalArgumentValue("width"));
		System.out.println("Value of height: " + p.getPositionalArgumentValue("height"));
		
		System.out.println("\nSaving to XML...");
		XMLEditor.saveToXML("ArgumentInformation.xml", p);
		System.out.println("\nLoading from XML...\n");
		XMLEditor.loadFromXML("ArgumentInformation.xml");
		
		////////////////////////////////////////////////////////////////////////////////////////////////
		
		//p.parse(userInput);
		
        float length = Float.parseFloat(p.getPositionalArgumentValue("length"));
        float width =  Float.parseFloat(p.getPositionalArgumentValue("width"));
        float height = Float.parseFloat(p.getPositionalArgumentValue("height"));

        float volume = length * width * height;
        System.out.println("\nThe volume is " + volume);
    }
}