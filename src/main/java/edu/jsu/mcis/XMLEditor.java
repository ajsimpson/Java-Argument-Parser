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
import java.io.*;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

public class XMLEditor {

private PrintWriter writer;

	 public void saveToXML(String fileName, ArgumentParser p) {
			try{
				writer = new PrintWriter(fileName);
				writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
				writer.write("<arguments>\n");
				for (Map.Entry<String, PositionalArgument> entry : p.positionalArguments.entrySet()) {
					String name = entry.getKey();
					writer.write("\t<argument type = \"positional\"" + ">\n");
					writer.write("\t\t<name>" + p.positionalArguments.get(name).getName() + "</name>\n");
					writer.write("\t\t<value>" + p.positionalArguments.get(name).getValue() + "</value>\n");
					writer.write("\t\t<type>" + p.typeToString(p.positionalArguments.get(name).getDatatype()) + "</type>\n");
					writer.write("\t\t<description>" + p.positionalArguments.get(name).getInfo() + "</description>\n");
				/*if(p.positionalArguments.get(name).hasRestrictedValues()) {
						for(int i=0; i<p.positionalArguments.get(name).choices.size(); i++) {
								writer.write("\t\t<restricted>" + p.positionalArguments.get(name).choices.get(i) + "</restricted>\n");
						}
					}*/
					writer.write("\t</argument>\n");
				}
				for (Map.Entry<String, NamedArgument> entry : p.namedArguments.entrySet()) {
					String name = entry.getKey();
					writer.write("\t<argument type = \"named\"" + ">\n");
					writer.write("\t\t<name>" + p.namedArguments.get(name).getName() + "</name>\n");
					writer.write("\t\t<value>" + p.namedArguments.get(name).getValue() + "</value>\n");
					writer.write("\t\t<type>" + p.typeToString(p.namedArguments.get(name).getDatatype()) + "</type>\n");
					writer.write("\t\t<description>" + p.namedArguments.get(name).getInfo() + "</description>\n");
					/*if(p.namedArguments.get(name).hasRestrictedValues()) {
						for(int i=0; i<p.namedArguments.get(name).choices.size(); i++) {
								writer.write("\t\t<restricted>" + p.namedArguments.get(name).choices.get(i) + "</restricted>\n");
						}
					}*/
					writer.write("\t</argument>\n");
				}
				writer.write("</arguments>");
				writer.close();
		} catch(FileNotFoundException e) {
						throw new FileErrorException("File could not be saved!");
		}
	}
	
	public ArgumentParser loadFromXML(String file) {
		ArgumentParser p = new ArgumentParser();
		try {
			File XMLFile = new File(file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
			Document XMLDoc = docBuilder.parse(XMLFile);
			XMLDoc.getDocumentElement().normalize();
			NodeList nodeList = XMLDoc.getElementsByTagName("argument");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node;
					if (e.getAttribute("type").equals("positional")) {
						String eName = e.getElementsByTagName("name").item(0).getTextContent();
						System.out.println("Positional argument name: " + eName); //DEMO
						String eValue = e.getElementsByTagName("value").item(0).getTextContent();
						System.out.println("Positional argument value: " + eValue); //DEMO
						String eType = e.getElementsByTagName("type").item(0).getTextContent();
						System.out.println("Positional argument type: " + eType); //DEMO
						String eDescription = e.getElementsByTagName("description").item(0).getTextContent();
						System.out.println("Positional argument description: " + eDescription + "\n"); //DEMO
						/*if(p.positionalArguments.get(eName).hasRestrictedValues()) {
							ArrayList<String> eRestrictedValues = new ArrayList<>();
							NodeList restrictedValues = e.getElementsByTagName("restricted");
							for (int k = 0; k < restrictedValues.getLength(); k++) {
								System.out.println(restrictedValues.item(k).getTextContent()); //DEMO
								eRestrictedValues.add(restrictedValues.item(k).getTextContent());
							}
						}*/
					}
					else if (e.getAttribute("type").equals("named")) {        
						String eName = e.getElementsByTagName("name").item(0).getTextContent();
						System.out.println("Named argument name: " + eName); //DEMO
						String eValue = e.getElementsByTagName("value").item(0).getTextContent();
						System.out.println("Named argument value: " + eValue); //DEMO
						String eType = e.getElementsByTagName("type").item(0).getTextContent();
						System.out.println("Named argument type: " + eType); //DEMO
						String eDescription = e.getElementsByTagName("description").item(0).getTextContent();
						System.out.println("Named argument description: " + eDescription +"\n"); //DEMO
						/*if(p.namedArguments.get(eName).hasRestrictedValues()) {
							ArrayList<String> eRestrictedValues = new ArrayList<>();
							NodeList restrictedValues = e.getElementsByTagName("restricted");
							for (int k = 0; k < restrictedValues.getLength(); k++) {
									System.out.println(restrictedValues.item(k).getTextContent()); //DEMO
									eRestrictedValues.add(restrictedValues.item(k).getTextContent());
							}
						}*/
					}
				}
			}
		} catch (SAXException | ParserConfigurationException | IOException e) {
				throw new FileErrorException("File not found!");
		}
		return p;
	}
}