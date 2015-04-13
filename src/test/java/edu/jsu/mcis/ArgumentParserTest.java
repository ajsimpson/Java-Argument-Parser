package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import edu.jsu.mcis.*;

public class ArgumentParserTest {

	private ArgumentParser p;
	
	@Before
	public void startUp() {
		p = new ArgumentParser();
	}
	
	@Test
	public void testGetPositionalArgumentNames() {
		p.addPositionalArgument("length");
		p.addPositionalArgument("width");
		p.addPositionalArgument("cat");
		p.addPositionalArgument("rain");
		assert(p.getPositionalArgumentNames().contains("length"));
		assert(p.getPositionalArgumentNames().contains("width"));
		assert(p.getPositionalArgumentNames().contains("cat"));
		assert(p.getPositionalArgumentNames().contains("rain"));
	}
	
	@Test
	public void testGetNamedArgumentNames() {
		p.addNamedArgument("length");
		p.addNamedArgument("width");
		p.addNamedArgument("cat");
		p.addNamedArgument("rain");
		assert(p.getNamedArgumentNames().contains("length"));
		assert(p.getNamedArgumentNames().contains("width"));
		assert(p.getNamedArgumentNames().contains("cat"));
		assert(p.getNamedArgumentNames().contains("rain"));
	}
	
	@Test
	public void testGetPositionalArgumentValues() {
		p.addPositionalArgument("length");
		p.addPositionalArgument("width");
		p.addPositionalArgument("cat");
		p.addPositionalArgument("rain");
		p.addPositionalArgumentValue("length", "7", Argument.Datatype.INTEGER);
		p.addPositionalArgumentValue("width", "5.2", Argument.Datatype.FLOAT);
		p.addPositionalArgumentValue("cat", "grey", Argument.Datatype.STRING);
		p.addPositionalArgumentValue("rain", "true", Argument.Datatype.BOOLEAN);
		assertEquals(7, p.getValue("length", 0));
		assertEquals(5.2f, p.getValue("width", 0));
		assertEquals("grey", p.getValue("cat", 0));
		assertEquals(true, p.getValue("rain", 0));
	}
	
	@Test
	public void testGetNamedArgumentValues() {
		p.addNamedArgument("length");
		p.addNamedArgument("width");
		p.addNamedArgument("cat");
		p.addNamedArgument("rain");
		p.addNamedArgumentValue("length", "7", Argument.Datatype.INTEGER);
		p.addNamedArgumentValue("width", "5.2", Argument.Datatype.FLOAT);
		p.addNamedArgumentValue("cat", "grey", Argument.Datatype.STRING);
		p.addNamedArgumentValue("rain", "true", Argument.Datatype.BOOLEAN);
		assertEquals(7, p.getValue("length", 0));
		assertEquals(5.2f, p.getValue("width", 0));
		assertEquals("grey", p.getValue("cat", 0));
		assertEquals(true, p.getValue("rain", 0));
	}
	
	@Test
	public void testReplaceValues() {
		p.addPositionalArgument("length");
		p.addNamedArgument("color");
		p.addPositionalArgumentValue("length", "7", Argument.Datatype.INTEGER);
		p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
		p.replacePositionalValue("length", 0, "5");
		p.replaceNamedValue("color", 0, "blue");
		assertEquals("5", p.getPositionalArgumentValue("length", 0));
		assertEquals("blue", p.getNamedArgumentValue("color", 0));
	}
	
	@Test (expected = UnknownArgumentException.class)
	public void testGetValueWithInvalidInput() {
		p.getValue("cheese", 0);
	}
	
	@Test
	public void testGetPositionalValueAsString() {
		p.addPositionalArgument("length");
		p.addPositionalArgumentValue("length", "7", Argument.Datatype.INTEGER);
		assertEquals("7", p.getPositionalArgumentValue("length", 0));
	}
	
	@Test
	public void testGetNamedValueAsString() {
		p.addNamedArgument("type");
		p.addNamedArgumentValue("type", "box", Argument.Datatype.STRING);
		assertEquals("box", p.getNamedArgumentValue("type", 0));
	}
	
	@Test
	public void testGetNamedArgumentDataType() {
		p.addNamedArgument("age");
		p.addNamedArgumentValue("age", "7", Argument.Datatype.INTEGER);
		p.setNamedArgumentType("age", Argument.Datatype.FLOAT);
		assertEquals(Argument.Datatype.FLOAT, p.getNamedArgumentType("age"));
	}
	
	@Test
	public void testGetPositionalArgumentDataType() {
		p.addPositionalArgument("age");
		p.addPositionalArgumentValue("age", "7", Argument.Datatype.INTEGER);
		p.setPositionalArgumentType("age", Argument.Datatype.FLOAT);
		assertEquals(Argument.Datatype.FLOAT, p.getPositionalArgumentType("age"));
	}
	
	@Test
	public void testGetNamedArgumentDescription() {
		p.addNamedArgument("Type");
		p.addNamedArgumentDescription("Type", "This is Type's description.");
		assertEquals("This is Type's description.", p.getNamedArgumentDescription("Type"));
	}
	
	@Test
	public void testGetPositionalArgumentDescription() {
		p.addPositionalArgument("Type");
		p.addPositionalArgumentDescription("Type", "This is Type's description.");
		assertEquals("This is Type's description.", p.getPositionalArgumentDescription("Type"));
	}
	
	@Test
	public void testSetFlag() {
		p.addNamedArgument("type");
		p.setFlag("type", true);
		assertEquals(true, p.getFlag("type"));
	}
	
	@Test
	public void testNamedArgumentIsPresent() {
		p.addNamedArgument("color");
		p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("--color");
		userInput.add("purple");
		p.parse(userInput);
		assertEquals(true, p.getFlag("color"));
	}
	
	@Test
	public void testShortNamedArgumentIsPresent() {
		p.addNamedArgument("color");
		p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("-c");
		userInput.add("purple");
		p.parse(userInput);
		assertEquals(true, p.getFlag("color"));
	}
	
	@Test
	public void testAddNamedRestrictedValue() {
		p.addNamedArgument("color");
		p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
		p.addNamedRestrictedValue("color", "blue");
		assertEquals(true, p.hasNamedRestrictedValues("color"));
	}
	
	@Test
	public void testNamedHasRestrictedValues() {
		p.addNamedArgument("color");
		p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
		p.addNamedRestrictedValue("color", "blue");
		assertEquals(true, p.hasNamedRestrictedValues("color"));
	}
	
	@Test
	public void testAddPositionalRestrictedValue() {
		p.addPositionalArgument("color");
		p.addPositionalArgumentValue("color", "red", Argument.Datatype.STRING);
		p.addPositionalRestrictedValue("color", "blue");
		assertEquals(true, p.hasPositionalRestrictedValues("color"));
	}
	
	@Test
	public void testPositionalHasRestrictedValues() {
		p.addPositionalArgument("color");
		p.addPositionalArgumentValue("color", "red", Argument.Datatype.STRING);
		p.addPositionalRestrictedValue("color", "blue");
		assertEquals(true, p.hasPositionalRestrictedValues("color"));
	}
	
	@Test
	public void testGetRestrictedValues() {
		p.addNamedArgument("color");
		p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
		p.addNamedRestrictedValue("color", "blue");
		p.addPositionalArgument("color");
		p.addPositionalArgumentValue("color", "red", Argument.Datatype.STRING);
		p.addPositionalRestrictedValue("color", "blue");
		assert(p.getNamedRestrictedValues("color").contains("blue"));
		assert(p.getPositionalRestrictedValues("color").contains("blue"));
	}
	
	@Test
	public void testGetNumberOfRestrictedValues() {
		p.addNamedArgument("color");
		p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
		p.addNamedRestrictedValue("color", "blue");
		p.addPositionalArgument("length");
		p.addPositionalArgumentValue("length", "7", Argument.Datatype.STRING);
		p.addPositionalRestrictedValue("length", "5");
		assertEquals(1, p.getNumberOfPositionalRestrictedValues("length"));
		assertEquals(2, p.getNumberOfNamedRestrictedValues("color"));
	}
	
	@Test
	public void testRequired() {
		p.addNamedArgument("color");
		p.setRequired("color");
		assertEquals(true, p.isRequired("color"));
	}
	
	@Test
	public void testGetRequiredArguments() {
		p.addNamedArgument("color");
		p.setRequired("color");
		assert(p.getRequiredArguments().contains("color"));
	}
	
	@Test
	public void testCheckForShortNameRequired() { //MISSED BY JACOCO?
		p.addPositionalArgument("length");
		p.addPositionalArgument("width");
		p.addPositionalArgument("height");
		p.addNamedArgument("color");
		p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("7");
		userInput.add("5");
		userInput.add("2");
		userInput.add("-c");
		userInput.add("blue");
		p.parse(userInput);
	}
	
	@Test (expected = RequiredArgumentException.class)
	public void testMissingRequired() {
		p.addPositionalArgument("length");
		p.addPositionalArgument("width");
		p.addPositionalArgument("height");
		p.addNamedArgument("color");
		p.setRequired("color");
		p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("7");
		userInput.add("5");
		userInput.add("2");
		p.parse(userInput);
	}
	
	@Test
	public void testCreateGroup() {
		p.createGroup("group1");
		assert(p.getGroups().contains("group1"));
	}
	
	@Test
	public void testAddToGroup() {
		p.addNamedArgument("color");
		p.createGroup("group1");
		p.addToGroup("group1", "color");
		assert(p.getGroupValues("group1").contains("color"));
	}
	
	@Test
	public void getGroupName() {
		p.addNamedArgument("color");
		p.createGroup("group1");
		p.addToGroup("group1", "color");
		assertEquals("group1", p.getGroupName("color"));
	}
	
	@Test
	public void testGetValues() {
		p.addPositionalArgument("length");
		p.addPositionalArgumentValue("length", "7", Argument.Datatype.INTEGER);
		p.addNamedArgument("color");
		p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
		assert(p.getPositionalValues("length").contains("7"));
		assert(p.getNamedValues("color").contains("red"));
	}
	
	@Test (expected = UnknownArgumentException.class)
	public void testAddUnknownArgumentToGroup() {
		p.createGroup("group1");
		p.addToGroup("group1", "color");
		assert(p.getGroupValues("group1").contains("color"));
	}
	
	@Test
	public void testIsGrouped() {
		p.addNamedArgument("color");
		p.createGroup("group1");
		p.addToGroup("group1", "color");
		assertEquals(true, p.isGrouped("color"));
	}
	
	@Test
	public void testShortNameInput() {
		p.addNamedArgument("color");
		p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("-c");
		userInput.add("purple");
		p.parse(userInput);
		assertEquals("purple", p.getNamedArgumentValue("color", 1));
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
	
	@Test (expected = InvalidArgumentException.class) //MISSED BY JACOCO?
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
	
	@Test (expected = MutualExclusionException.class)
	public void testMutualExclusionException() {
		p.addNamedArgument("color");
		p.createGroup("group1");
		p.createGroup("group2");
		p.addToGroup("group1", "color");
		p.addToGroup("group2", "color");
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("7");
		userInput.add("5");
		userInput.add("2");
		userInput.add("--color");
		userInput.add("red");
		p.parse(userInput);
	}
	
	@Test (expected = RestrictedValueException.class)
	public void testRestrictedValueException() {
		p.addNamedArgument("color");
		p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
		p.addNamedRestrictedValue("color", "blue");
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("7");
		userInput.add("5");
		userInput.add("2");
		userInput.add("--color");
		userInput.add("pink");
		p.parse(userInput);
	}
	
	@Test
	public void testTypeToString() {
		p.addPositionalArgument("color");
        p.addPositionalArgumentValue("color", "red", Argument.Datatype.STRING);
        p.addPositionalArgument("age");
        p.addPositionalArgumentValue("age", "22", Argument.Datatype.INTEGER);
        p.addPositionalArgument("weight");
        p.addPositionalArgumentValue("weight", "160.5", Argument.Datatype.FLOAT);
		p.addPositionalArgument("married");
        p.addPositionalArgumentValue("married", "false", Argument.Datatype.BOOLEAN);
		assertEquals("String", p.typeToString(p.getPositionalArgumentType("color")));
		assertEquals("integer", p.typeToString(p.getPositionalArgumentType("age")));
		assertEquals("float", p.typeToString(p.getPositionalArgumentType("weight")));
		assertEquals("boolean", p.typeToString(p.getPositionalArgumentType("married")));
	}
	
	@Test
	public void testShowHelp() {
		assertEquals("\nUsage: Java VolumeCalculator length width height\nCalculate the volume of a box.\n\nPositional arguments:\nlength: the length of the box\nwidth: the width of the box\nheight: the height of the box", p.showHelp());
	}
	
	@Test
	public void testWriteAndReadArgumentsToFile() {
		p.addPositionalArgument("length");
        p.addNamedArgument("color");
		p.addPositionalArgumentValue("length", "7", Argument.Datatype.INTEGER);
        p.addNamedArgumentValue("color", "red", Argument.Datatype.STRING);
		p.addNamedArgumentDescription("color", "color's value is a string.");
		p.addNamedRestrictedValue("color", "blue");
		p.addPositionalRestrictedValue("length", "5");
		p.setRequired("color");
		p.createGroup("group1");
		p.addToGroup("group1", "color");
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("7");
		userInput.add("--color");
		userInput.add("red");
		p.parse(userInput);
		XMLEditor.saveToXML("src/test/java/edu/jsu/mcis/XMLTest.xml", p);
		XMLEditor.loadFromXML("src/test/java/edu/jsu/mcis/XMLTest.xml", p);
		assertEquals("length", p.positionalArguments.get("length").getName());
		assertEquals("color", p.namedArguments.get("color").getName());
		assert(p.getNamedRestrictedValues("color").contains("blue"));
	}
	
	
	@Test (expected = FileErrorException.class)
	public void testLoadFromXMLError() {
		XMLEditor.loadFromXML("doesNotExist.xml", p);
	}
	
	//@Test (expected = FileErrorException.class)
	public void testSaveToXMLError() {
		
		XMLEditor.saveToXML("src/test/java/edu/jsu/mcis/XMLTest.xml", p);
	}
}