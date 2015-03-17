package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.util.ArrayList;

public class ArgumentParserTest {

	private ArgumentParser p;
	
	@Before
	public void startUp() {
		p = new ArgumentParser();
	}
	
	@Test
	public void testGetDescriptionOfOptionalArgument() {
		p.addOptionalArgument("Type");
		p.addOptionalArgumentDescription("Type","This is Type's description.");
		assertEquals("This is Type's description.", p.getOptionalArgumentDescription("Type"));
	}
	
	@Test
	public void testGetPositionalArgument() {
		p.addPositionalArgument("length");
		p.addPositionalArgumentValue("length", "7", "INTEGER");
		assertEquals("7", p.getPositionalArgument("length"));
	}
	
	@Test
	public void testGetOptionalArgument() {
		p.addOptionalArgument("age");
		p.addOptionalArgumentValue("age", "7", "INTEGER");
		assertEquals("7", p.getOptionalArgument("age"));
	}
	
	@Test
	public void testGetArgumentValue() {
		p.addPositionalArgument("length");
		p.addPositionalArgumentValue("length", "7", "INTEGER");
		assertEquals("7", p.getPositionalArgumentValue("length"));
	}
	
	@Test
	public void testGetOptionalValue() {
		p.addOptionalArgument("type");
		p.addOptionalArgumentValue("type", "box", "STRING");
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
		p.addOptionalArgumentValue("color", "red", "STRING");
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("--color");
		userInput.add("purple");
		p.parse(userInput);
		assertEquals(true, p.getFlag("color"));
	}
	
	@Test
	public void testUseShortNameInput() {
		p.addOptionalArgument("color");
		p.addOptionalArgumentValue("color", "red", "STRING");
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("-c");
		userInput.add("purple");
		p.parse(userInput);
		assertEquals("purple", p.getOptionalArgumentValue("color"));
	}
	
	@Test
	public void testIfOptionalArgumentShortNameIsPresent() {
		p.addOptionalArgument("color");
		p.addOptionalArgumentValue("color", "red", "STRING");
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("-c");
		userInput.add("purple");
		p.parse(userInput);
		assertEquals(true, p.getFlag("color"));
	}
	
	@Test
	public void testGetUnknownShortName() {
		p.addOptionalArgument("color");
		p.addOptionalArgumentValue("color", "red", "STRING");
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("-f");
		userInput.add("purple");
		try{
		p.parse(userInput);
		} catch
		(UnknownArgumentException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testGetDifferentPositionalArgumentValues() {
		p.addPositionalArgument("length");
		p.addPositionalArgument("width");
		p.addPositionalArgument("cat");
		p.addPositionalArgument("rain");
		p.addPositionalArgumentValue("length", "7", "INTEGER");
		p.addPositionalArgumentValue("width", "5.2", "FLOAT");
		p.addPositionalArgumentValue("cat", "white cat", "STRING");
		p.addPositionalArgumentValue("rain", "true", "BOOLEAN");
		assertEquals("7", p.getPositionalArgument("length"));
		assertEquals("5.2", p.getPositionalArgument("width"));
		assertEquals("white cat", p.getPositionalArgument("cat"));
		assertEquals("true", p.getPositionalArgument("rain"));
	}
	
	@Test
	public void testGetDifferentOptionalArgumentValues() {
		p.addOptionalArgumentValue("type", "5.2", "FLOAT");
		p.addOptionalArgumentValue("color", "red", "STRING");
		p.addOptionalArgumentValue("art", "3", "INTEGER");
		p.addOptionalArgumentValue("shape", "true", "BOOLEAN");
		assertEquals("5.2", p.getOptionalArgument("type"));
		assertEquals("red", p.getOptionalArgument("color"));
		assertEquals("3", p.getOptionalArgument("art"));
		assertEquals("true", p.getOptionalArgument("shape"));
	}
	
	@Test
	public void testMissingArgumentException() {
		p.addPositionalArgument("length");
		p.addPositionalArgument("width");
		p.addPositionalArgument("height");
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("7");
		userInput.add("5");
		try{
		p.parse(userInput);
		} catch
		(MissingArgumentException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testInvalidArgumentException() {
		p.addPositionalArgument("length");
		p.addPositionalArgument("width");
		p.addPositionalArgument("height");
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("7");
		userInput.add("something");
		userInput.add("2");
		try{
		p.parse(userInput);
		} catch
		(InvalidArgumentException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testUnrecognisedArgumentException() {
		p.addPositionalArgument("length");
		p.addPositionalArgument("width");
		p.addPositionalArgument("height");
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("7");
		userInput.add("5");
		userInput.add("2");
		userInput.add("4");
		try{
			p.parse(userInput);
		} catch(UnrecognisedArgumentException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testUnknownArgumentException() {
		p.addOptionalArgument("type");
		p.addOptionalArgumentValue("type","sphere","STRING");
		ArrayList<String> userInput = new ArrayList<>();
		userInput.add("type");
		userInput.add("sphere");
		userInput.add("--color");
		userInput.add("purple");
		try{
			p.parse(userInput);
		} catch(UnknownArgumentException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testCheckForInvalidArguments() {
		try{
		p.addOptionalArgumentValue("symbol", "/-*", "INTEGER");
		} catch(InvalidArgumentException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testShowHelp() {
		assertEquals("\nUsage: Java VolumeCalculator length width height\nCalculate the volume of a box.\n\nPositional arguments:\nlength: the length of the box\nwidth: the width of the box\nheight: the height of the box", p.showHelp());
	}
	
	@Test
	public void testGetArgumentDataType() {
		p.addOptionalArgument("age");
		p.addOptionalArgumentValue("age", "7", "INTEGER");
		p.setOptionalArgumentType("age", "FLOAT");
		assertEquals("FLOAT", p.getOptionalArgumentType("age"));
	}
}