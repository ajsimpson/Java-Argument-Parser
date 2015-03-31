package edu.jsu.mcis;

import java.util.ArrayList;
import java.util.Arrays;

public class VolCalc {

    public static void main(String[] args) {
		ArrayList<String> userInput = new ArrayList<>();
        ArgumentParser p = new ArgumentParser();
		userInput.addAll(Arrays.asList(args));
		
        p.addOptionalArgument("color");
        p.addOptionalArgumentValue("color", "red", Argument.Datatype.STRING);
		p.addOptionalArgumentDescription("color", "color's value is a string.");
        p.addOptionalArgument("age");
        p.addOptionalArgumentValue("age", "22", Argument.Datatype.INTEGER);
		p.addOptionalArgumentDescription("age", "age's value is an integer.");
        p.addOptionalArgument("weight");
        p.addOptionalArgumentValue("weight", "160.5", Argument.Datatype.FLOAT);
		p.addOptionalArgumentDescription("weight", "weight's value is a float.");
		
		//p.addOptionalArgumentValue("weight", "16076543", "INTEGER");
		
        p.addPositionalArgument("length");
        p.addPositionalArgument("width");
        p.addPositionalArgument("height");
		
		//DEMO
		///////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("\nDefaut argument values:\n");
		
		System.out.println("Value of color: " + p.getOptionalArgument("color"));
		System.out.println("Is present: " + p.getFlag("color"));
		System.out.println("Value of age: " + p.getOptionalArgument("age"));
		System.out.println("Is present: " + p.getFlag("age"));
		System.out.println("Value of weight: " + p.getOptionalArgument("weight")+"");
		System.out.println("Is present: " + p.getFlag("weight"));
		
		System.out.println("\nValue of length: " + p.getPositionalArgument("length"));
		System.out.println("Value of width: " + p.getPositionalArgument("width"));
		System.out.println("Value of height: " + p.getPositionalArgument("height"));
		
		p.parse(userInput);
		
		System.out.println("\nArgument values after command line input:\n");
		
		System.out.println("color:");
		System.out.println("Value of color: " + p.getOptionalArgument("color"));
		System.out.println("Is present: " + p.getFlag("color"));
		System.out.println("Description: " + p.getOptionalArgumentDescription("color"));
		System.out.println("\nage:");
		System.out.println("Value of age: " + p.getOptionalArgument("age"));
		System.out.println("Is present: " + p.getFlag("age"));
		System.out.println("Description: " + p.getOptionalArgumentDescription("age"));
		System.out.println("\nweight:");
        System.out.println("Value of weight: " + p.getOptionalArgument("weight"));
		System.out.println("Is present: " + p.getFlag("weight"));
		System.out.println("Description: " + p.getOptionalArgumentDescription("weight"));
		
		System.out.println("\nValue of length: " + p.getPositionalArgumentValue("length"));
		System.out.println("Value of width: " + p.getPositionalArgumentValue("width"));
		System.out.println("Value of height: " + p.getPositionalArgumentValue("height"));
		///////////////////////////////////////////////////////////////////////////////////////////////*/
		
		//p.parse(userInput);
		
        float length = Float.parseFloat(p.getPositionalArgumentValue("length"));
        float width =  Float.parseFloat(p.getPositionalArgumentValue("width"));
        float height = Float.parseFloat(p.getPositionalArgumentValue("height"));

        float volume = length * width * height;
        System.out.println("\nThe volume is " + volume);
    }
}