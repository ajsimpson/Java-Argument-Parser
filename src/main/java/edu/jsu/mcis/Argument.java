package edu.jsu.mcis;

import java.util.ArrayList;

public class Argument {

	public String name = "";
 	public String value = "";
	public String info = "";
	public Datatype type = Datatype.STRING;
	public enum Datatype {STRING, FLOAT, INTEGER, BOOLEAN};
	public ArrayList<String> choices = new ArrayList<>();
	
	String getName(){
		return name;
	}
	
	void setValue(String v){
		value = v;
	}
	
	String getValue(){
		return value;
	}
	
	void setInfo(String i){
		info = i;
	}
	
	String getInfo(){
		return info;
	}
	
	void setDatatype(Datatype d) {
		type = d;
	}
	
	Datatype getDatatype() {
		return type;
	}
	
	void setChoice(String c) {
		choices.add(c);
	}
	
	void getChoices() {
		for(int i=0; i<choices.size(); i++) {
			System.out.println(choices.get(i));
		}
	}
}