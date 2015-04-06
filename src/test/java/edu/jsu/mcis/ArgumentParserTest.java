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
	public void testGetDescriptionOfOptionalArgument() {
		p.addOptionalArgument("Type");
		p.addOptionalArgumentDescription("Type", "This is Type's description.");
		assertEquals("This is Type's description.", p.getOptionalArgumentDescription("Type"));
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
	public void testGetOptionalArgument() {
		p.addOptionalArgument("age");
		p.addOptionalArgumentValue("age", "7", Argument.Datatype.INTEGER);
		assertEquals("7", p.getOptionalArgument("age"));
	}
	
	@Test
	public void testGetArgumentValue() {
		p.addPositionalArgument("length");
		p.addPositionalArgumentValue("length", "7", Argument.Datatype.INTEGER);
		assertEquals("7", p.getPositionalArgumentValue("length"));
	}
	
	@Test
	public void testGetOptionalValue() {
		p.addOptionalArgument("type");
		p.addOptionalArgumentValue("type", "box", Argument.Datatype.STRING);
		assertEquals("box", p.getOptionalArgumentValue("type"));
	}
	
	@Test
	public void testGetFlag() {
		p.addOptionalArgument("type");
		p.setFlag("type", true);
		assertEquals(true, p.getFlag("type"));
	}
	
	@Test
	public void testIfOptionalArgumentIsPresent() {
		p.addOptionalArgument("color");
		p.addOptionalArgumentValue("color", "red", Argument.Datatype.STRING);
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("--color");
		userInput.add("purple");
		p.parse(userInput);
		assertEquals(true, p.getFlag("color"));
	}
	
	@Test
	public void testUseShortNameInput() {
		p.addOptionalArgument("color");
		p.addOptionalArgumentValue("color", "red", Argument.Datatype.STRING);
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("-c");
		userInput.add("purple");
		p.parse(userInput);
		assertEquals("purple", p.getOptionalArgumentValue("color"));
	}
	
	@Test
	public void testIfOptionalArgumentShortNameIsPresent() {
		p.addOptionalArgument("color");
		p.addOptionalArgumentValue("color", "red", Argument.Datatype.STRING);
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("-c");
		userInput.add("purple");
		p.parse(userInput);
		assertEquals(true, p.getFlag("color"));
	}
	
	@Test (expected = UnknownArgumentException.class)
	public void testGetUnknownShortName() {
		p.addOptionalArgument("color");
		p.addOptionalArgumentValue("color", "red", Argument.Datatype.STRING);
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
	public void testGetDifferentOptionalArgumentValues() {
		p.addOptionalArgumentValue("type", "5.2", Argument.Datatype.FLOAT);
		p.addOptionalArgumentValue("color", "red", Argument.Datatype.STRING);
		p.addOptionalArgumentValue("art", "3", Argument.Datatype.INTEGER);
		p.addOptionalArgumentValue("shape", "true", Argument.Datatype.BOOLEAN);
		assertEquals("5.2", p.getOptionalArgument("type"));
		assertEquals("red", p.getOptionalArgument("color"));
		assertEquals("3", p.getOptionalArgument("art"));
		assertEquals("true", p.getOptionalArgument("shape"));
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
		p.addOptionalArgument("type");
		p.addOptionalArgumentValue("type", "sphere", Argument.Datatype.STRING);
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("type");
		userInput.add("sphere");
		userInput.add("--color");
		userInput.add("purple");
		p.parse(userInput);
	}
	
	
	@Test
	public void testAddChoice() {
		p.addOptionalArgument("color");
		p.addOptionalArgumentValue("color", "red", Argument.Datatype.STRING);
		p.addChoice("color", "blue");
		assert(p.optionalArguments.get("color").choices.contains("blue"));
	}
	
	@Test
	public void testIfArgumentHasRestrictedValues() {
		p.addOptionalArgument("color");
		p.addOptionalArgumentValue("color", "red", Argument.Datatype.STRING);
		p.addChoice("color", "blue");
		assertEquals(true, p.optionalArguments.get("color").hasRestrictedValues());
	}
	
	@Test (expected = RestrictedValueException.class)
	public void testRestrictedValueException() {
		p.addOptionalArgument("color");
		p.addOptionalArgumentValue("color", "red", Argument.Datatype.STRING);
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
		p.addOptionalArgument("age");
		p.addOptionalArgumentValue("age", "7", Argument.Datatype.INTEGER);
		p.setOptionalArgumentType("age", Argument.Datatype.FLOAT);
		assertEquals(Argument.Datatype.FLOAT, p.getOptionalArgumentType("age"));
	}
	
	@Test
	public void testGetChoices() {
		p.addOptionalArgument("color");
		p.addOptionalArgumentValue("color", "red", Argument.Datatype.STRING);
		p.addChoice("color", "blue");
		assert(p.getChoices("color").contains("red"));
		assert(p.getChoices("color").contains("blue"));
	}
	
	@Test
	public void testTypeToString() {
		p.addOptionalArgument("color");
        p.addOptionalArgumentValue("color", "red", Argument.Datatype.STRING);
        p.addOptionalArgument("age");
        p.addOptionalArgumentValue("age", "22", Argument.Datatype.INTEGER);
        p.addOptionalArgument("weight");
        p.addOptionalArgumentValue("weight", "160.5", Argument.Datatype.FLOAT);
		p.addOptionalArgument("married");
        p.addOptionalArgumentValue("married", "false", Argument.Datatype.BOOLEAN);
		assertEquals("String", p.typeToString(p.optionalArguments.get("color").getDatatype()));
		assertEquals("integer", p.typeToString(p.optionalArguments.get("age").getDatatype()));
		assertEquals("float", p.typeToString(p.optionalArguments.get("weight").getDatatype()));
		assertEquals("boolean", p.typeToString(p.optionalArguments.get("married").getDatatype()));
	}
	
	@Test
	public void testWriteAndReadArgumentsToFile() {
        p.addOptionalArgument("color");
        p.addOptionalArgumentValue("color", "red", Argument.Datatype.STRING);
		p.addOptionalArgumentDescription("color", "color's value is a string.");
		p.addPositionalArgument("length");
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("7");
		userInput.add("--color");
		userInput.add("red");
		p.parse(userInput);
		XMLEditor.saveToXML("src/test/java/edu/jsu/mcis/XMLTest.xml", p);
		XMLEditor.loadFromXML("src/test/java/edu/jsu/mcis/XMLTest.xml");
		assertEquals("length", p.positionalArguments.get("length").getName());
		assertEquals("color", p.optionalArguments.get("color").getName());
		assertEquals("red", p.optionalArguments.get("color").getValue());
	}
}