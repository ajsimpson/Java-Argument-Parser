package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ArgumentParserTest {

	private ArgumentParser p;
	
	@Before
	public void startUp() {
		p = new ArgumentParser();
	}
	
	@Test
	public void testGetDescriptionOfNamedArgument() {
		p.addNamedArgument("Type");
		p.addNamedArgumentDescription("Type", "This is Type's description.");
		assertEquals("This is Type's description.", p.getNamedArgumentDescription("Type"));
	}
	
	@Test
	public void testGetDescriptionOfPositionalArgument() {
		p.addPositionalArgument("Type");
		p.addPositionalArgumentDescription("Type", "This is Type's description.");
		assertEquals("This is Type's description.", p.getPositionalArgumentDescription("Type"));
	}
	
	@Test
	public void testGetPositionalArgument() {
		p.addPositionalArgument("length");
		p.addPositionalArgumentValue("length", "7", Argument.Datatype.INTEGER);
		assertEquals("7", p.getPositionalArgument("length"));
	}
	
	@Test
	public void testGetNamedArgument() {
		p.addNamedArgument("age");
		p.addNamedArgumentValue("age", "7", Argument.Datatype.INTEGER);
		assertEquals("7", p.getNamedArgument("age"));
	}
	
	@Test
	public void testGetArgumentValue() {
		p.addPositionalArgument("length");
		p.addPositionalArgumentValue("length", "7", Argument.Datatype.INTEGER);
		assertEquals("7", p.getPositionalArgumentValue("length"));
	}
	
	@Test
	public void testGetNamedValue() {
		p.addNamedArgument("type");
		p.addNamedArgumentValue("type", "box", Argument.Datatype.STRING);
		assertEquals("box", p.getNamedArgumentValue("type"));
	}
	
	@Test
	public void testGetFlag() {
		p.addNamedArgument("type");
		p.setFlag("type", true);
		assertEquals(true, p.getFlag("type"));
	}
	
	@Test
	public void testIfNamedArgumentIsPresent() {
		p.addNamedArgument("color");
		p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("--color");
		userInput.add("purple");
		p.parse(userInput);
		assertEquals(true, p.getFlag("color"));
	}
	
	@Test
	public void testUseShortNameInput() {
		p.addNamedArgument("color");
		p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("-c");
		userInput.add("purple");
		p.parse(userInput);
		assertEquals("purple", p.getNamedArgumentValue("color"));
	}
	
	@Test
	public void testIfNamedArgumentShortNameIsPresent() {
		p.addNamedArgument("color");
		p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("-c");
		userInput.add("purple");
		p.parse(userInput);
		assertEquals(true, p.getFlag("color"));
	}
	
	@Test (expected = UnknownArgumentException.class)
	public void testGetUnknownShortName() {
		p.addNamedArgument("color");
		p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("-f");
		userInput.add("purple");
		p.parse(userInput);
	}
	
	@Test
	public void testGetDifferentPositionalArgumentValues() {
		p.addPositionalArgument("length");
		p.addPositionalArgument("width");
		p.addPositionalArgument("cat");
		p.addPositionalArgument("rain");
		p.addPositionalArgumentValue("length", "7", Argument.Datatype.INTEGER);
		p.addPositionalArgumentValue("width", "5.2", Argument.Datatype.FLOAT);
		p.addPositionalArgumentValue("cat", "white cat", Argument.Datatype.STRING);
		p.addPositionalArgumentValue("rain", "true", Argument.Datatype.BOOLEAN);
		assertEquals("7", p.getPositionalArgument("length"));
		assertEquals("5.2", p.getPositionalArgument("width"));
		assertEquals("white cat", p.getPositionalArgument("cat"));
		assertEquals("true", p.getPositionalArgument("rain"));
	}
	
	@Test
	public void testGetDifferentNamedArgumentValues() {
		p.addNamedArgumentValue("type", "5.2", Argument.Datatype.FLOAT);
		p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
		p.addNamedArgumentValue("art", "3", Argument.Datatype.INTEGER);
		p.addNamedArgumentValue("shape", "true", Argument.Datatype.BOOLEAN);
		assertEquals("5.2", p.getNamedArgument("type"));
		assertEquals("red", p.getNamedArgument("color"));
		assertEquals("3", p.getNamedArgument("art"));
		assertEquals("true", p.getNamedArgument("shape"));
	}
	
	@Test (expected = MissingArgumentException.class)
	public void testMissingArgumentException() {
		p.addPositionalArgument("length");
		p.addPositionalArgument("width");
		p.addPositionalArgument("height");
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("7");
		userInput.add("5");
		p.parse(userInput);
	}
	
	@Test (expected = InvalidArgumentException.class)
	public void testInvalidArgumentException() {
		p.addPositionalArgument("length");
		p.addPositionalArgument("width");
		p.addPositionalArgument("height");
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("7");
		userInput.add("5");
		userInput.add("something");
		userInput.add("2");
		p.parse(userInput);
	}
	
	@Test (expected = UnrecognisedArgumentException.class)
	public void testUnrecognisedArgumentException() {
		p.addPositionalArgument("length");
		p.addPositionalArgument("width");
		p.addPositionalArgument("height");
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("7");
		userInput.add("5");
		userInput.add("2");
		userInput.add("4");
		p.parse(userInput);
	}
	
	@Test (expected = UnknownArgumentException.class)
	public void testUnknownArgumentException() {
		p.addNamedArgument("type");
		p.addNamedArgumentValue("type", "sphere", Argument.Datatype.STRING);
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("type");
		userInput.add("sphere");
		userInput.add("--color");
		userInput.add("purple");
		p.parse(userInput);
	}
	
	
	@Test
	public void testAddChoice() {
		p.addNamedArgument("color");
		p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
		p.addChoice("color", "blue");
		assert(p.namedArguments.get("color").choices.contains("blue"));
	}
	
	@Test
	public void testIfArgumentHasRestrictedValues() {
		p.addNamedArgument("color");
		p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
		p.addChoice("color", "blue");
		assertEquals(true, p.namedArguments.get("color").hasRestrictedValues());
	}
	
	@Test (expected = RestrictedValueException.class)
	public void testRestrictedValueException() {
		p.addNamedArgument("color");
		p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
		p.addChoice("color", "blue");
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("7");
		userInput.add("5");
		userInput.add("2");
		userInput.add("--color");
		userInput.add("pink");
		p.parse(userInput);
	}
	
	@Test
	public void testShowHelp() {
		assertEquals("\nUsage: Java VolumeCalculator length width height\nCalculate the volume of a box.\n\nPositional arguments:\nlength: the length of the box\nwidth: the width of the box\nheight: the height of the box", p.showHelp());
	}
	
	@Test
	public void testGetArgumentDataType() {
		p.addNamedArgument("age");
		p.addNamedArgumentValue("age", "7", Argument.Datatype.INTEGER);
		p.setNamedArgumentType("age", Argument.Datatype.FLOAT);
		assertEquals(Argument.Datatype.FLOAT, p.getNamedArgumentType("age"));
	}
	
	@Test
	public void testGetChoices() {
		p.addNamedArgument("color");
		p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
		p.addChoice("color", "blue");
		assert(p.getChoices("color").contains("red"));
		assert(p.getChoices("color").contains("blue"));
	}
	
	@Test
	public void testTypeToString() {
		p.addNamedArgument("color");
        p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
        p.addNamedArgument("age");
        p.addNamedArgumentValue("age", "22", Argument.Datatype.INTEGER);
        p.addNamedArgument("weight");
        p.addNamedArgumentValue("weight", "160.5", Argument.Datatype.FLOAT);
		p.addNamedArgument("married");
        p.addNamedArgumentValue("married", "false", Argument.Datatype.BOOLEAN);
		assertEquals("String", p.typeToString(p.namedArguments.get("color").getDatatype()));
		assertEquals("integer", p.typeToString(p.namedArguments.get("age").getDatatype()));
		assertEquals("float", p.typeToString(p.namedArguments.get("weight").getDatatype()));
		assertEquals("boolean", p.typeToString(p.namedArguments.get("married").getDatatype()));
	}
	
	@Test
	public void testWriteAndReadArgumentsToFile() {
        p.addNamedArgument("color");
        p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
		p.addNamedArgumentDescription("color", "color's value is a string.");
		p.addPositionalArgument("length");
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("7");
		userInput.add("--color");
		userInput.add("red");
		p.parse(userInput);
		XMLEditor.saveToXML("src/test/java/edu/jsu/mcis/XMLTest.xml", p);
		XMLEditor.loadFromXML("src/test/java/edu/jsu/mcis/XMLTest.xml");
		assertEquals("length", p.positionalArguments.get("length").getName());
		assertEquals("color", p.namedArguments.get("color").getName());
		assertEquals("red", p.namedArguments.get("color").getValue());
	}
}