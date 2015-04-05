package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import org.w3c.dom.Document;
import javax.xml.parsers.*;
//import org.custommonkey.xmlunit.*;

public class XMLTest {

	private ArgumentParser p;
	
	@Before
	public void JUnitXML() {
		p = new ArgumentParser();
	}
	
	/*@Test
	public void testLoadFromXMLArgumentNames() {
		p = XML.loadFromXML("./src/java/edu/jsu/mcis/ArgumentInformationTests.xml");
		assertEquals("7", p.getValue("length"));
		assertEquals("5", p.getValue("width"));
		assertEquals("2", p.getValue("height"));
	}
	
	@Test
	public void testLoadFromXMLArgumentValues() {
		p = XMLEditor.loadFromXML("./src/java/edu/jsu/mcis/ArgumentInformationTests.xml");
		ArrayList<String> input = new ArrayList<>();
		input.add("7");
		input.add("5");
		input.add("2");
		p.parse(input);
		assertEquals("7", p.getPositionalArgumentValue("length"));
		assertEquals("5", p.getPositionalArgumentValue("width"));
		assertEquals("2", p.getPositionalArgumentValue("height"));
	}
	
	@Test
	public void testLoadFromXMLArgumentDescription() {
		p = XML.loadFromXML("./src/java/edu/jsu/mcis/ArgumentInformationTests.xml");
		assertEquals("color's value is a string.", p.getValue("length"));
		assertEquals("age's value is an integer.", p.getValue("width"));
		assertEquals("weight's value is a float.", p.getValue("height"));
	}
	
	@Test
	public void testLoadFromXMLArgumentDatatype() {
		p = XML.loadFromXML("./src/java/edu/jsu/mcis/ArgumentInformationTests.xml");
		assertEquals("7", p.getValue("length"));
		assertEquals("5", p.getValue("width"));
		assertEquals("2", p.getValue("height"));
	}*/
}