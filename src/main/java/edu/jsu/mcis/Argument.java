package edu.jsu.mcis;

import java.util.ArrayList;

public class Argument {

	public String name = "";
 	public String value = "";
	public String info = "";
	public Datatype type = Datatype.STRING;
	public enum Datatype {STRING, FLOAT, INTEGER, BOOLEAN};
	public boolean restrictedValues = false;
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
	
	void addChoice(String c) {
		choices.add(c);
	}
	
	ArrayList<String> getChoices() {
		return choices;
	}

	void setRestrictedValues(boolean r) {
		restrictedValues = r;
	}
	
	boolean hasRestrictedValues() {
		return restrictedValues;
	}
	
	void isRestrictedValue(String v) {
		if(restrictedValues) {
		boolean found = false;
		if(choices.contains(v))
			found = true;
		else
			throw new RestrictedValueException("\nUsage: Java VolumeCalculator length width height \nVolumeCalculator.Java: error: value not allowed: " + v);
		}
	}
}