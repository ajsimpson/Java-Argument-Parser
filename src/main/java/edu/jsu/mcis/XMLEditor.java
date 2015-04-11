package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import javax.xml.stream.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

public class XMLEditor {

	 public static void saveToXML(String fileName, ArgumentParser p) {
		try {
			PrintWriter writer;
			writer = new PrintWriter(fileName);
			writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			writer.write("<arguments>\n");
			for(Map.Entry<String, PositionalArgument> entry : p.positionalArguments.entrySet()) {
				String name = entry.getKey();
				PositionalArgument temp = new PositionalArgument(name);
				temp = p.positionalArguments.get(name);
				writer.write("\t<argument type = \"positional\"" + ">\n");
				writer.write("\t\t<name>" + p.positionalArguments.get(name).getName() + "</name>\n");
				writer.write("\t\t<values>\n");
				for(int i=0; i<temp.getValues().size(); i++) {
					writer.write("\t\t\t<value>" + temp.values.get(i) + "</value>\n");
				}
				writer.write("\t\t</values>\n");
				if(p.hasPositionalRestrictedValues(name)) {
					writer.write("\t\t<restrictedValues>\n");
					for(int i=0; i<p.getNumberOfPositionalRestrictedValues(name); i++) {
						writer.write("\t\t\t<restrictedValue>" + temp.restrictedValues.get(i) + "</restrictedValue>\n");
					}
					writer.write("\t\t</restrictedValues>\n");
				}
				writer.write("\t\t<type>" + p.typeToString(p.getPositionalArgumentType(name)) + "</type>\n");
				writer.write("\t\t<description>" + p.getPositionalArgumentDescription(name) + "</description>\n");
				writer.write("\t</argument>\n");
			}
			for(Map.Entry<String, NamedArgument> entry : p.namedArguments.entrySet()) {
				String name = entry.getKey();
				NamedArgument temp = new NamedArgument(name);
				temp = p.namedArguments.get(name);
				writer.write("\t<argument type = \"named\"" + ">\n");
				writer.write("\t\t<name>" + p.namedArguments.get(name).getName() + "</name>\n");
				writer.write("\t\t<values>\n");
				for(int i=0; i<temp.getValues().size(); i++) {
					writer.write("\t\t\t<value>" + temp.values.get(i) + "</value>\n");
				}
				writer.write("\t\t</values>\n");
				if(p.hasNamedRestrictedValues(name)) {
					writer.write("\t\t<restrictedValues>\n");
					for(int i=0; i<p.getNumberOfNamedRestrictedValues(name); i++) {
						writer.write("\t\t\t<restrictedValue>" + temp.restrictedValues.get(i) + "</restrictedValue>\n");
					}
					writer.write("\t\t</restrictedValues>\n");
				}
				writer.write("\t\t<type>" + p.typeToString(p.getNamedArgumentType(name)) + "</type>\n");
				writer.write("\t\t<description>" + p.getNamedArgumentDescription(name) + "</description>\n");
				if(p.isGrouped(name)) {
					writer.write("\t\t<group>" + p.getGroupName(name) + "</group>\n");
				}
				writer.write("\t\t<required>" + String.valueOf(p.isRequired(name)) + "</required>\n");
				writer.write("\t</argument>\n");
			}
			writer.write("</arguments>");
			writer.close();
		} catch(FileNotFoundException e) {
			throw new FileErrorException("File could not be saved!");
		}
	}
	
	public static ArgumentParser loadFromXML(String file, ArgumentParser p) {
		try {
			File XMLFile = new File(file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
			Document XMLDoc = docBuilder.parse(XMLFile);
			XMLDoc.getDocumentElement().normalize();
			NodeList nodeList = XMLDoc.getElementsByTagName("argument");
			for(int i=0; i<nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node;
					if(e.getAttribute("type").equals("positional")) {
						String eName = e.getElementsByTagName("name").item(0).getTextContent();
						System.out.println("Positional argument name: " + eName); //DEMO
						ArrayList<String> eValues = new ArrayList<>();
						NodeList argumentValues = e.getElementsByTagName("value");
						for (int k=0; k<argumentValues.getLength(); k++) {
							System.out.println("Positional argument value: " + argumentValues.item(k).getTextContent()); //DEMO
							eValues.add(argumentValues.item(k).getTextContent());
						}
						if(p.hasPositionalRestrictedValues(eName)) {
							ArrayList<String> eRestrictedValues = new ArrayList<>();
							NodeList restrictedValues = e.getElementsByTagName("restrictedValue");
							for (int k=0; k<restrictedValues.getLength(); k++) {
								System.out.println("Positional restricted value: " + restrictedValues.item(k).getTextContent()); //DEMO
								eRestrictedValues.add(restrictedValues.item(k).getTextContent());
							}
						}
						String eType = e.getElementsByTagName("type").item(0).getTextContent();
						System.out.println("Positional argument type: " + eType); //DEMO
						String eDescription = e.getElementsByTagName("description").item(0).getTextContent();
						System.out.println("Positional argument description: " + eDescription + "\n"); //DEMO
					}
					else if(e.getAttribute("type").equals("named")) {        
						String eName = e.getElementsByTagName("name").item(0).getTextContent();
						System.out.println("Named argument name: " + eName); //DEMO
						ArrayList<String> eValues = new ArrayList<>();
						NodeList argumentValues = e.getElementsByTagName("value");
						for (int k=0; k<argumentValues.getLength(); k++) {
							System.out.println("Named argument value: " + argumentValues.item(k).getTextContent()); //DEMO
							eValues.add(argumentValues.item(k).getTextContent());
						}
						if(p.hasNamedRestrictedValues(eName)) {
							ArrayList<String> eRestrictedValues = new ArrayList<>();
							NodeList restrictedValues = e.getElementsByTagName("restrictedValue");
							for (int k=0; k<restrictedValues.getLength(); k++) {
								System.out.println("Named restricted value: " + restrictedValues.item(k).getTextContent()); //DEMO
								eRestrictedValues.add(restrictedValues.item(k).getTextContent());
							}
						}
						String eType = e.getElementsByTagName("type").item(0).getTextContent();
						System.out.println("Named argument type: " + eType); //DEMO
						String eDescription = e.getElementsByTagName("description").item(0).getTextContent();
						System.out.println("Named argument description: " + eDescription); //DEMO
						String eRequired = e.getElementsByTagName("required").item(0).getTextContent();
						System.out.println("Named argument required: " + eRequired); //DEMO
						if(p.isGrouped(eName)) {
							String eGroup = e.getElementsByTagName("group").item(0).getTextContent();
							System.out.println("Named argument group: " + eGroup); //DEMO
						}
						System.out.println("\n");
					}
				}
			}
		} catch(SAXException | ParserConfigurationException | IOException e) {
			throw new FileErrorException("File not found!"+e);
		}
		return p;
	}
}