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

private static PrintWriter writer;

	 public static void saveToXML(String fileName, ArgumentParser p) {
		try{
			writer = new PrintWriter(fileName);
			writer.write("<?xml version=\"1.0\"?>\n");
			writer.write("<arguments>\n");
			for (Map.Entry<String, PositionalArgument> entry : p.positionalArguments.entrySet()) {
				String name = entry.getKey();
				writer.write("\t<argument type = \"positional\"" + ">\n");
				writer.write("\t\t<name>" + p.positionalArguments.get(name).getName() + "</name>\n");
				writer.write("\t\t<value>" + p.positionalArguments.get(name).getValue() + "</value>\n");
				writer.write("\t\t<type>" + p.typeToString(p.positionalArguments.get(name).getDatatype()) + "</type>\n");
				writer.write("\t\t<description>" + p.positionalArguments.get(name).getInfo() + "</description>\n");
				if(p.positionalArguments.get(name).hasRestrictedValues()) {
					for(int i=0; i<p.positionalArguments.get(name).choices.size(); i++) {
						writer.write("\t\t<restricted>" + p.positionalArguments.get(name).choices.get(i) + "</restricted>\n");
					}
				}
			}
			for (Map.Entry<String, OptionalArgument> entry : p.optionalArguments.entrySet()) {
				String name = entry.getKey();
				writer.write("\t<argument type = \"optional\"" + ">\n");
				writer.write("\t\t<name>" + p.optionalArguments.get(name).getName() + "</name>\n");
				writer.write("\t\t<value>" + p.optionalArguments.get(name).getValue() + "</value>\n");
				writer.write("\t\t<type>" + p.typeToString(p.optionalArguments.get(name).getDatatype()) + "</type>\n");
				writer.write("\t\t<description>" + p.optionalArguments.get(name).getInfo() + "</description>\n");
				if(p.optionalArguments.get(name).hasRestrictedValues()) {
					for(int i=0; i<p.optionalArguments.get(name).choices.size(); i++) {
						writer.write("\t\t<restricted>" + p.optionalArguments.get(name).choices.get(i) + "</restricted>\n");
					}
				}
			}
			writer.write("</arguments>");
			writer.close();
		} catch(FileNotFoundException e) {
				throw new FileErrorException("File could not be saved!");
		}
	}
	
	public static ArgumentParser loadFromXML(String fileName) {
		ArgumentParser p = new ArgumentParser();
		try {
			File xmlFile = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
			Document xmlDoc = docBuilder.parse(xmlFile);
			xmlDoc.getDocumentElement().normalize();
			NodeList nodeList = xmlDoc.getElementsByTagName("argument");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node;
					if (e.getAttribute("type").equals("positional")) {
						String eName = e.getElementsByTagName("name").item(0).getTextContent();
						String eValue = e.getElementsByTagName("value").item(0).getTextContent();
						String eType = e.getElementsByTagName("type").item(0).getTextContent();
						String eDescription = e.getElementsByTagName("description").item(0).getTextContent();
						if(p.positionalArguments.get(eName).hasRestrictedValues()) {
							ArrayList<String> eRestrictedValues = new ArrayList<>();
							NodeList restrictedValues = e.getElementsByTagName("restricted");
							for (int k = 0; k < restrictedValues.getLength(); k++) {
								eRestrictedValues.add(restrictedValues.item(k).getTextContent());
							}
						}
					}
					else if (e.getAttribute("type").equals("optional")) {	
						String eName = e.getElementsByTagName("name").item(0).getTextContent();
						String eValue = e.getElementsByTagName("value").item(0).getTextContent();
						String eType = e.getElementsByTagName("type").item(0).getTextContent();
						String eDescription = e.getElementsByTagName("description").item(0).getTextContent();
						if(p.optionalArguments.get(eName).hasRestrictedValues()) {
							ArrayList<String> eRestrictedValues = new ArrayList<>();
							NodeList restrictedValues = e.getElementsByTagName("restricted");
							for (int k = 0; k < restrictedValues.getLength(); k++) {
								eRestrictedValues.add(restrictedValues.item(k).getTextContent());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new FileErrorException("File not found!");
		}
		return p;
	}
}