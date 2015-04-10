package edu.jsu.mcis;

import java.util.ArrayList;

public class Argument {

	protected String name = "";
 	protected String value = "";
	protected String info = "";
	protected Datatype type = Datatype.STRING;
	protected enum Datatype {STRING, FLOAT, INTEGER, BOOLEAN};
	protected boolean containsRestrictedValues = false;
	protected ArrayList<String> restrictedValues = new ArrayList<>();
	protected boolean required = false;
	
	public String getName(){
		return name;
	}
	
	public void setValue(String v){
		value = v;
	}
	
	public String getValue(){
		return value;
	}
	
	public void setInfo(String i){
		info = i;
	}
	
	public String getInfo(){
		return info;
	}
	
	public void setDatatype(Datatype d) {
		type = d;
	}
	
	public Datatype getDatatype() {
		return type;
	}
	
	public void addRestrictedValue(String c) {
		restrictedValues.add(c);
	}
	
	public ArrayList<String> getRestrictedValues() {
		return restrictedValues;
	}

	public void setRestrictedValues(boolean r) {
		containsRestrictedValues = r;
	}
	
	public boolean hasRestrictedValues() {
		return containsRestrictedValues;
	}
	
	public void isRestrictedValue(String v) {
		if(containsRestrictedValues) {
		boolean found = false;
		if(restrictedValues.contains(v))
			found = true;
		else
			throw new RestrictedValueException("\nUsage: Java VolumeCalculator length width height \nVolumeCalculator.Java: error: Invalid restricted value: " + v);
		}
	}
	
	public void setRequired(boolean r) {
		required = r;
	}
	
	public boolean isRequired() {
		return required;
	}
}